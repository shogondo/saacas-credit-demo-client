package shou.saacas.demo.client;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class EntryAmountFragment extends Fragment {
    private Button settleButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.entry_amount, container, false);

        settleButton = (Button) view.findViewById(R.id.settle_button);
        settleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new CompletePaymentFragment())
                        .commit();
            }
        });

        return view;
    }
}
