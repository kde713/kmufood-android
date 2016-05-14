package net.sproutlab.kmufood.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import net.sproutlab.kmufood.R;
import net.sproutlab.kmufood.parsemod.IntegratedParser;

public class FailMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_fail_message);

        findViewById(R.id.btn_retry).setOnClickListener(mListner);
        findViewById(R.id.btn_exit).setOnClickListener(mListner);
    }

    ImageButton.OnClickListener mListner = new ImageButton.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_retry:
                    (new IntegratedParser(getApplicationContext(), mHandler)).execute("");
                    break;
                case R.id.btn_exit:
                    finishAffinity();
                    break;
            }
        }
    };

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Log.d("FailScreen-Handler","Message "+Integer.toString(msg.what)+" Received.");
            switch(msg.what) {
                case -1:
                    Snackbar.make(findViewById(R.id.failview_container), getString(R.string.msg_refail), Snackbar.LENGTH_LONG).show();
                    break;
                case 2:
                    Toast.makeText(FailMessageActivity.this, getString(R.string.msg_resuccess), Toast.LENGTH_LONG).show();
                    finish();
                    break;
            }
        }
    };
}
