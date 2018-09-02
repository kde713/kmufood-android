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
import net.sproutlab.kmufood.utils.DateUtil;
import net.sproutlab.kmufood.utils.MenuDataHelper;
import net.sproutlab.kmufood.utils.PrefHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private KMUFoodApplication kmuFoodApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        kmuFoodApplication = (KMUFoodApplication) getApplicationContext();

        PrefHelper prefHelper = new PrefHelper(getApplicationContext());

        if (!prefHelper.checkUniqueKey()) {
            prefHelper.updateKey();
            updateFromCoop();
        } else if (prefHelper.needUpdate()) {
            updateFromCoop();
        } else {
            Toast.makeText(SplashActivity.this, getString(R.string.msg_itsok), Toast.LENGTH_SHORT).show();
            kmuFoodApplication.setUpdateChecked(true);
            finish();
        }
    }

    private void updateFromCoop() {
        Toast.makeText(SplashActivity.this, getString(R.string.msg_loading), Toast.LENGTH_SHORT).show();
        final DateUtil.WeekRange weekRange = new DateUtil.WeekRange();
        Call<ApiResponse> call = APIGlobal.callInterface.coopApi(DateUtil.getStringFromDate(weekRange.getStartDate()), DateUtil.getStringFromDate(weekRange.getEndDate()));
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        MenuDataHelper dataHelper = new MenuDataHelper(getApplicationContext(), weekRange.getStartDate());
                        ApiResponse body = response.body();
                        dataHelper.saveChungFood(body.chungFood);
                        dataHelper.saveDormFood(body.dormFood1, body.dormFood2);
                        dataHelper.saveLawFood(body.lawFood);
                        dataHelper.saveStaffFood(body.staffFood);
                        dataHelper.saveStuFood(body.stuFood);
                        Log.d("coopApi", "OK");
                        kmuFoodApplication.setUpdateChecked(true);
                        finish();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        Log.d("coopApi", "Parse Exception (NullPointerException)");
                        showFailMessage(FailMessageActivity.REASON_UNKNOWN);
                    }
                } else {
                    Log.d("coopApi", "API Failed with STATUS=" + Integer.toString(response.code()));
                    showFailMessage(FailMessageActivity.REASON_COOPAPI);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
                Log.d("coopApi", "Network Error");
                showFailMessage(FailMessageActivity.REASON_NETWORK);
            }
        });
    }

    private void showFailMessage(int reasonCode) {
        Intent failActivity = new Intent(SplashActivity.this, FailMessageActivity.class);
        failActivity.putExtra("reason", reasonCode);
        failActivity.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(failActivity);
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
