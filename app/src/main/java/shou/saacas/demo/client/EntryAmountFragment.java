package shou.saacas.demo.client;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class EntryAmountFragment extends Fragment implements PaymentTask.Callback {
    private Button settleButton;

    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.entry_amount, container, false);

        dialog = new ProgressDialog(getActivity());

        settleButton = (Button) view.findViewById(R.id.settle_button);
        settleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage(getString(R.string.payment_in_progress));
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.setMax(100);
                dialog.show();

                PaymentTask task = new PaymentTask(EntryAmountFragment.this);
                task.execute();
            }
        });

        return view;
    }

    @Override
    public void onSuccess(String result) {
        dialog.dismiss();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new CompletePaymentFragment())
                .commit();
    }

    @Override
    public void onProgress(int progress) {
        dialog.incrementProgressBy(progress);
    }

    @Override
    public void onFailure(Exception error) {
        dialog.dismiss();
    }
}
