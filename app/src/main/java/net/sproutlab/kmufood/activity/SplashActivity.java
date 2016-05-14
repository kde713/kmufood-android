package net.sproutlab.kmufood.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import net.sproutlab.kmufood.R;
import net.sproutlab.kmufood.datamod.Timestampdata;
import net.sproutlab.kmufood.parsemod.IntegratedParser;

public class SplashActivity extends AppCompatActivity {

    IntegratedParser mParser;

    private final String PREF_NAME = "net.sproutlab.kmufood";
    private final int specialKey = 1095;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        Timestampdata mTSAdapter = new Timestampdata(getApplicationContext());
        mParser = new IntegratedParser(this, mHandler);
        SharedPreferences mPref = getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor mPrefEditor = mPref.edit();

        if(mPref.getInt("specialKey",0) != specialKey){
            mPrefEditor.putInt("specialKey", specialKey);
            Toast.makeText(SplashActivity.this, getString(R.string.msg_loading), Toast.LENGTH_SHORT).show();
            mParser.execute("");
        } else if(mTSAdapter.checkTS()){
            Toast.makeText(SplashActivity.this, getString(R.string.msg_loading), Toast.LENGTH_SHORT).show();
            mParser.execute("");
        } else{
            Toast.makeText(SplashActivity.this, getString(R.string.msg_itsok), Toast.LENGTH_SHORT).show();
            mSplashHandler.sendEmptyMessageDelayed(2000, 0);
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            mParser.cancel(true);
            switch(msg.what) {
                case -1:
                    startActivity((new Intent(SplashActivity.this, FailMessageActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                    break;
                case 2:
                    mSplashHandler.sendEmptyMessageDelayed(0, 0);
                    break;
            }
        }
    };

    private Handler mSplashHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            finish();
        }
    };

    @Override
    public void onBackPressed(){
        return;
    }
}
