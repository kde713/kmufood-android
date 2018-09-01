package net.sproutlab.kmufood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import net.sproutlab.kmufood.KMUFoodApplication;
import net.sproutlab.kmufood.R;
import net.sproutlab.kmufood.api.APIGlobal;
import net.sproutlab.kmufood.api.models.ApiResponse;
import net.sproutlab.kmufood.utils.PrefHelper;

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

        PrefHelper prefHelper = new PrefHelper(getApplicationContext());

        if (!prefHelper.checkUniqueKey()) {
//            prefHelper.patchKey();
//            Toast.makeText(SplashActivity.this, getString(R.string.msg_loading), Toast.LENGTH_SHORT).show();
//            Call<ApiResponse> call = APIGlobal.callInterface.coopApi("2018-09-01", "2018-09-05");
//            call.enqueue(new Callback<ApiResponse>() {
//                @Override
//                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                    Log.d("APITEST", "onResponse");
//                    if (response.isSuccessful()) {
//                        ApiResponse body = response.body();
//                        Sikdan test = body.lawfood.get("2018-09-03").fire2;
//                        Log.d("APITEST", String.format("메뉴: %s\n가격: %s", test.menu, test.price));
//                    } else {
//                        Log.d("APITEST", "STATUS " + Integer.toString(response.code()));
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ApiResponse> call, Throwable t) {
//                    t.printStackTrace();
//                    Log.d("APITEST", "onFailure");
//                }
//            });
//            Call<String> call = APIGlobal.callInterface.downloadMenu();
//            call.enqueue(new Callback<String>() {
//                @Override
//                public void onResponse(Call<String> call, Response<String> response) {
//                    if (response.isSuccessful()) {
//                        MenuJSONParse jsonparse = new MenuJSONParse(response.body());
//                        lawFooddata mLawfood = new lawFooddata(SplashActivity.this);
//                        mLawfood.saveData(jsonparse.runParse("lawfood"));
//                        stuFooddata mStufood = new stuFooddata(SplashActivity.this);
//                        mStufood.saveData(jsonparse.runParse("stufood"));
//                        staffFooddata mStafffood = new staffFooddata(SplashActivity.this);
//                        mStafffood.saveData(jsonparse.runParse("stafffood"));
//                        chungFooddata mChungfood = new chungFooddata(SplashActivity.this);
//                        mChungfood.saveData(jsonparse.runParse("chungfood"));
//                        dormFooddata mDormfood = new dormFooddata(SplashActivity.this);
//                        mDormfood.saveData(jsonparse.runParse("dormfood"));
//                        Prefdata mTSData = new Prefdata(SplashActivity.this);
//                        mTSData.updateTS();
//                        mHandler.sendEmptyMessage(1);
//                        Message msg = mHandler.obtainMessage();
//                        msg.what = 2;
//                        mHandler.sendMessage(msg);
//                    } else {
//                        Log.d("MenuDownload", "HTTP " + response.code());
//                        mHandler.sendEmptyMessage(1);
//                        Message msg = mHandler.obtainMessage();
//                        msg.what = -1;
//                        mHandler.sendMessage(msg);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<String> call, Throwable t) {
//                    t.printStackTrace();
//                    mHandler.sendEmptyMessage(1);
//                    Message msg = mHandler.obtainMessage();
//                    msg.what = -1;
//                    mHandler.sendMessage(msg);
//                }
//            });
        } else if (prefHelper.needUpdate()) {
//            Toast.makeText(SplashActivity.this, getString(R.string.msg_loading), Toast.LENGTH_SHORT).show();
//            Call<String> call = APIGlobal.callInterface.downloadMenu();
//            call.enqueue(new Callback<String>() {
//                @Override
//                public void onResponse(Call<String> call, Response<String> response) {
//                    if (response.isSuccessful()) {
//                        MenuJSONParse jsonparse = new MenuJSONParse(response.body());
//                        lawFooddata mLawfood = new lawFooddata(SplashActivity.this);
//                        mLawfood.saveData(jsonparse.runParse("lawfood"));
//                        stuFooddata mStufood = new stuFooddata(SplashActivity.this);
//                        mStufood.saveData(jsonparse.runParse("stufood"));
//                        staffFooddata mStafffood = new staffFooddata(SplashActivity.this);
//                        mStafffood.saveData(jsonparse.runParse("stafffood"));
//                        chungFooddata mChungfood = new chungFooddata(SplashActivity.this);
//                        mChungfood.saveData(jsonparse.runParse("chungfood"));
//                        dormFooddata mDormfood = new dormFooddata(SplashActivity.this);
//                        mDormfood.saveData(jsonparse.runParse("dormfood"));
//                        Prefdata mTSData = new Prefdata(SplashActivity.this);
//                        mTSData.updateTS();
//                        mHandler.sendEmptyMessage(1);
//                        Message msg = mHandler.obtainMessage();
//                        msg.what = 2;
//                        mHandler.sendMessage(msg);
//                    } else {
//                        Log.d("MenuDownload", "HTTP " + response.code());
//                        mHandler.sendEmptyMessage(1);
//                        Message msg = mHandler.obtainMessage();
//                        msg.what = -1;
//                        mHandler.sendMessage(msg);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<String> call, Throwable t) {
//                    t.printStackTrace();
//                    mHandler.sendEmptyMessage(1);
//                    Message msg = mHandler.obtainMessage();
//                    msg.what = -1;
//                    mHandler.sendMessage(msg);
//                }
//            });
        } else {
            Toast.makeText(SplashActivity.this, getString(R.string.msg_itsok), Toast.LENGTH_SHORT).show();
            kmuFoodApplication.setUpdateChecked(true);
            finish();
        }
    }

    private void updateFromCoop() {
        Toast.makeText(SplashActivity.this, getString(R.string.msg_loading), Toast.LENGTH_SHORT).show();
        Call<ApiResponse> call = APIGlobal.callInterface.coopApi("2018-09-01", "2018-09-05");
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse body = response.body();
                } else {
                    Log.d("coopApi", "API Failed with STATUS=" + Integer.toString(response.code()));
                    startActivity((new Intent(SplashActivity.this, FailMessageActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
                Log.d("coopApi", "Network Error");
                startActivity((new Intent(SplashActivity.this, FailMessageActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
