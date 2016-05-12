package net.sproutlab.kmufood.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import net.sproutlab.kmufood.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*
        Handler mSplashHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                finish();
            }
        };
        mSplashHandler.sendEmptyMessageDelayed(0, 2000);
        */

        
    }

    @Override
    public void onBackPressed(){
        return;
    }
}
