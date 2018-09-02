package net.sproutlab.kmufood.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import net.sproutlab.kmufood.KMUFoodApplication;
import net.sproutlab.kmufood.R;
import net.sproutlab.kmufood.api.APIGlobal;
import net.sproutlab.kmufood.api.models.ApiResponse;
import net.sproutlab.kmufood.dialog.FeedbackDialog;
import net.sproutlab.kmufood.utils.DateUtil;
import net.sproutlab.kmufood.utils.MenuDataHelper;
import net.sproutlab.kmufood.utils.PrefHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FailMessageActivity extends AppCompatActivity {

    public static final int REASON_NETWORK = 1;
    public static final int REASON_COOPAPI = 2;

    ProgressDialog loadingDiag;
    PrefHelper prefHelper;

    KMUFoodApplication kmuFoodApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_fail_message);

        kmuFoodApplication = (KMUFoodApplication) getApplicationContext();

        int reasonCode = getIntent().getIntExtra("reason", REASON_COOPAPI);
        int failImageId = R.drawable.msg_fail;
        switch (reasonCode) {
            case REASON_NETWORK:
                failImageId = R.drawable.msg_fail;
                break;
            case REASON_COOPAPI:
                failImageId = R.drawable.msg_fail;
                break;
        }
        ((ImageView) findViewById(R.id.img_failmessage)).setImageResource(failImageId);

        prefHelper = new PrefHelper(getApplicationContext());

        loadingDiag = new ProgressDialog(FailMessageActivity.this);
        loadingDiag.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDiag.setMessage(getString(R.string.msg_retry));

        findViewById(R.id.btn_retry).setOnClickListener(mListner);
        findViewById(R.id.btn_close).setOnClickListener(mListner);
        findViewById(R.id.btn_feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new FeedbackDialog(FailMessageActivity.this)).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadingDiag.dismiss();
    }

    private void showFailMessage() {
        loadingDiag.dismiss();
        Toast.makeText(FailMessageActivity.this, getString(R.string.msg_refail), Toast.LENGTH_LONG).show();
    }


    ImageButton.OnClickListener mListner = new ImageButton.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_retry:
                    loadingDiag.show();
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

                                    loadingDiag.dismiss();
                                    if (!prefHelper.checkUniqueKey()) prefHelper.updateKey();
                                    Toast.makeText(FailMessageActivity.this, getString(R.string.msg_resuccess), Toast.LENGTH_LONG).show();
                                    kmuFoodApplication.setUpdateChecked(true);
                                    finish();
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                    Log.d("coopApi", "Parse Exception (NullPointerException)");
                                    showFailMessage();
                                }
                            } else {
                                Log.d("coopApi", "API Failed with STATUS=" + Integer.toString(response.code()));
                                showFailMessage();
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            t.printStackTrace();
                            Log.d("coopApi", "Network Error");
                            showFailMessage();
                        }
                    });
                    break;
                case R.id.btn_close:
                    finishAffinity();
                    break;
            }
        }
    };
}
