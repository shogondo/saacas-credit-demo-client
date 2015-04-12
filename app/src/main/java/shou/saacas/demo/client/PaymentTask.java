package shou.saacas.demo.client;

import android.os.AsyncTask;

public class PaymentTask extends AsyncTask<Integer, Integer, PaymentTask.Result> {
    private Callback callback;

    public PaymentTask(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected PaymentTask.Result doInBackground(Integer... params) {
        Result result = new Result();

        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(1000);
                publishProgress(20);
            }
        } catch (InterruptedException e) {
            result.error = e;
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
