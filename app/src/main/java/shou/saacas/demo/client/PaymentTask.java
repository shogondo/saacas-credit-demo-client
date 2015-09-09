package shou.saacas.demo.client;

import android.os.AsyncTask;

import jp.atrealize.saacas.ssc.Packet;
import jp.atrealize.saacas.ssc.SSCException;
import jp.atrealize.saacas.ssc.SaacasSSCClient;
import jp.atrealize.ssc.usb.Logger;

public class PaymentTask extends AsyncTask<Integer, Integer, PaymentTask.Result> {
    private Callback callback;

    private int amount;

    public PaymentTask(Callback callback, int amount) {
        this.callback = callback;
        this.amount = amount;
    }

    @Override
    protected PaymentTask.Result doInBackground(Integer... params) {
        Result result = new Result();

        try {
            SaacasSSCClient.getInstance().open();
            publishProgress(25);

            SaacasSSCClient.getInstance().send("0001", String.format("%d;", amount));
            publishProgress(25);

            while(true) {
                Packet responsePacket = SaacasSSCClient.getInstance().send("0002", "");
                if ("0003".equals(responsePacket.getRequestCode())) {
                    break;
                }
            }
            publishProgress(25);

            SaacasSSCClient.getInstance().send("0004", "", true);
            publishProgress(25);
        }
        catch (SSCException e) {
            Logger.error(e);
            result.error = e;
        }
        finally {
            SaacasSSCClient.getInstance().close();
        }

        return result;
    }

    @Override
    protected void onPostExecute(PaymentTask.Result result) {
        if (result.hasError()) {
            callback.onFailure(result.error);
        } else {
            callback.onSuccess(result.value);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        callback.onProgress(values[0]);
    }

    public class Result {
        public String value;

        public Exception error;

        public boolean hasError() {
            return error != null;
        }
    }

    public interface Callback {
        public void onSuccess(String result);

        public void onProgress(int progress);

        public void onFailure(Exception error);
    }
}
