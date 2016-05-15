package net.sproutlab.kmufood.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import net.sproutlab.kmufood.R;
import net.sproutlab.kmufood.datamod.Timestampdata;
import net.sproutlab.kmufood.parsemod.IntegratedParser;

public class FailMessageActivity extends AppCompatActivity {

    ProgressDialog loadingDiag = new ProgressDialog(this);
    Timestampdata mTSAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_fail_message);

        mTSAdapter = new Timestampdata(getApplicationContext());

        loadingDiag.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDiag.setMessage(getString(R.string.msg_retry));

        findViewById(R.id.btn_retry).setOnClickListener(mListner);
        findViewById(R.id.btn_exit).setOnClickListener(mListner);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        loadingDiag.dismiss();
    }


    ImageButton.OnClickListener mListner = new ImageButton.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_retry:
                    loadingDiag.show();
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
            switch(msg.what) {
                case -1:
                    loadingDiag.dismiss();
                    Snackbar.make(findViewById(R.id.failview_container), getString(R.string.msg_refail), Snackbar.LENGTH_LONG).show();
                    break;
                case 2:
                    loadingDiag.dismiss();
                    if(!mTSAdapter.checkKey()) mTSAdapter.patchKey();
                    Toast.makeText(FailMessageActivity.this, getString(R.string.msg_resuccess), Toast.LENGTH_LONG).show();
                    finish();
                    break;
            }
        }
    };
}
