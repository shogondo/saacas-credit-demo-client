package shou.saacas.demo.client;

import android.app.Activity;
import android.os.Bundle;

import jp.atrealize.saacas.ssc.SaacasSSCClient;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new EntryAmountFragment())
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SaacasSSCClient.getInstance().prepare(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SaacasSSCClient.getInstance().dispose();
    }
}
