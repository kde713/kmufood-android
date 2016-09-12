package net.sproutlab.kmufood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import net.sproutlab.kmufood.KMUFoodApplication;
import net.sproutlab.kmufood.R;
import net.sproutlab.kmufood.api.APIGlobal;
import net.sproutlab.kmufood.api.MenuJSONParse;
import net.sproutlab.kmufood.data.Prefdata;
import net.sproutlab.kmufood.data.chungFooddata;
import net.sproutlab.kmufood.data.dormFooddata;
import net.sproutlab.kmufood.data.lawFooddata;
import net.sproutlab.kmufood.data.staffFooddata;
import net.sproutlab.kmufood.data.stuFooddata;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    KMUFoodApplication kmuFoodApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        kmuFoodApplication = (KMUFoodApplication) getApplicationContext();

        Prefdata mTSAdapter = new Prefdata(getApplicationContext());

        if (!mTSAdapter.checkKey()) {
            mTSAdapter.patchKey();
            Toast.makeText(SplashActivity.this, getString(R.string.msg_loading), Toast.LENGTH_SHORT).show();
            Call<String> call = APIGlobal.callInterface.downloadMenu();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        MenuJSONParse jsonparse = new MenuJSONParse(response.body());
                        lawFooddata mLawfood = new lawFooddata(SplashActivity.this);
                        mLawfood.saveData(jsonparse.runParse("lawfood"));
                        stuFooddata mStufood = new stuFooddata(SplashActivity.this);
                        mStufood.saveData(jsonparse.runParse("stufood"));
                        staffFooddata mStafffood = new staffFooddata(SplashActivity.this);
                        mStafffood.saveData(jsonparse.runParse("stafffood"));
                        chungFooddata mChungfood = new chungFooddata(SplashActivity.this);
                        mChungfood.saveData(jsonparse.runParse("chungfood"));
                        dormFooddata mDormfood = new dormFooddata(SplashActivity.this);
                        mDormfood.saveData(jsonparse.runParse("dormfood"));
                        Prefdata mTSData = new Prefdata(SplashActivity.this);
                        mTSData.updateTS();
                        mHandler.sendEmptyMessage(1);
                        Message msg = mHandler.obtainMessage();
                        msg.what = 2;
                        mHandler.sendMessage(msg);
                    } else {
                        Log.d("MenuDownload", "HTTP " + response.code());
                        mHandler.sendEmptyMessage(1);
                        Message msg = mHandler.obtainMessage();
                        msg.what = -1;
                        mHandler.sendMessage(msg);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                    mHandler.sendEmptyMessage(1);
                    Message msg = mHandler.obtainMessage();
                    msg.what = -1;
                    mHandler.sendMessage(msg);
                }
            });
        } else if (mTSAdapter.checkTS()) {
            Toast.makeText(SplashActivity.this, getString(R.string.msg_loading), Toast.LENGTH_SHORT).show();
            Call<String> call = APIGlobal.callInterface.downloadMenu();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        MenuJSONParse jsonparse = new MenuJSONParse(response.body());
                        lawFooddata mLawfood = new lawFooddata(SplashActivity.this);
                        mLawfood.saveData(jsonparse.runParse("lawfood"));
                        stuFooddata mStufood = new stuFooddata(SplashActivity.this);
                        mStufood.saveData(jsonparse.runParse("stufood"));
                        staffFooddata mStafffood = new staffFooddata(SplashActivity.this);
                        mStafffood.saveData(jsonparse.runParse("stafffood"));
                        chungFooddata mChungfood = new chungFooddata(SplashActivity.this);
                        mChungfood.saveData(jsonparse.runParse("chungfood"));
                        dormFooddata mDormfood = new dormFooddata(SplashActivity.this);
                        mDormfood.saveData(jsonparse.runParse("dormfood"));
                        Prefdata mTSData = new Prefdata(SplashActivity.this);
                        mTSData.updateTS();
                        mHandler.sendEmptyMessage(1);
                        Message msg = mHandler.obtainMessage();
                        msg.what = 2;
                        mHandler.sendMessage(msg);
                    } else {
                        Log.d("MenuDownload", "HTTP " + response.code());
                        mHandler.sendEmptyMessage(1);
                        Message msg = mHandler.obtainMessage();
                        msg.what = -1;
                        mHandler.sendMessage(msg);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                    mHandler.sendEmptyMessage(1);
                    Message msg = mHandler.obtainMessage();
                    msg.what = -1;
                    mHandler.sendMessage(msg);
                }
            });
        } else {
            Toast.makeText(SplashActivity.this, getString(R.string.msg_itsok), Toast.LENGTH_SHORT).show();
            mSplashHandler.sendEmptyMessageDelayed(0, 0);
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -1:
                    startActivity((new Intent(SplashActivity.this, FailMessageActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                    break;
                case 2:
                    mSplashHandler.sendEmptyMessageDelayed(0, 0);
                    break;
            }
        }
    };

    private Handler mSplashHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            kmuFoodApplication.setUpdateChecked(true);
            finish();
        }
    };

    @Override
    public void onBackPressed() {
        return;
    }
}
