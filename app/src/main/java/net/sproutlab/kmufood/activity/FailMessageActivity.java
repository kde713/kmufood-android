package net.sproutlab.kmufood.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import net.sproutlab.kmufood.KMUFoodApplication;
import net.sproutlab.kmufood.R;
import net.sproutlab.kmufood.api.APIGlobal;
import net.sproutlab.kmufood.dialog.FeedbackDialog;
import net.sproutlab.kmufood.utils.PrefHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FailMessageActivity extends AppCompatActivity {

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


    ImageButton.OnClickListener mListner = new ImageButton.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_retry:
                    loadingDiag.show();
//                    Call<String> call = APIGlobal.callInterface.downloadMenu();
//                    call.enqueue(new Callback<String>() {
//                        @Override
//                        public void onResponse(Call<String> call, Response<String> response) {
//                            if (response.isSuccessful()) {
////                                MenuJSONParse jsonparse = new MenuJSONParse(response.body());
////                                lawFooddata mLawfood = new lawFooddata(FailMessageActivity.this);
////                                mLawfood.saveData(jsonparse.runParse("lawfood"));
////                                stuFooddata mStufood = new stuFooddata(FailMessageActivity.this);
////                                mStufood.saveData(jsonparse.runParse("stufood"));
////                                staffFooddata mStafffood = new staffFooddata(FailMessageActivity.this);
////                                mStafffood.saveData(jsonparse.runParse("stafffood"));
////                                chungFooddata mChungfood = new chungFooddata(FailMessageActivity.this);
////                                mChungfood.saveData(jsonparse.runParse("chungfood"));
////                                dormFooddata mDormfood = new dormFooddata(FailMessageActivity.this);
////                                mDormfood.saveData(jsonparse.runParse("dormfood"));
//                                prefHelper.updateLastUpdate();
//                                mHandler.sendEmptyMessage(1);
//                                Message msg = mHandler.obtainMessage();
//                                msg.what = 2;
//                                mHandler.sendMessage(msg);
//                            } else {
//                                Log.d("MenuDownload", "HTTP " + response.code());
//                                mHandler.sendEmptyMessage(1);
//                                Message msg = mHandler.obtainMessage();
//                                msg.what = -1;
//                                mHandler.sendMessage(msg);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<String> call, Throwable t) {
//                            t.printStackTrace();
//                            mHandler.sendEmptyMessage(1);
//                            Message msg = mHandler.obtainMessage();
//                            msg.what = -1;
//                            mHandler.sendMessage(msg);
//                        }
//                    });
                    break;
                case R.id.btn_close:
                    finishAffinity();
                    break;
            }
        }
    };

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -1:
                    loadingDiag.dismiss();
                    Toast.makeText(FailMessageActivity.this, getString(R.string.msg_refail), Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    loadingDiag.dismiss();
                    if (!prefHelper.checkUniqueKey()) prefHelper.updateKey();
                    Toast.makeText(FailMessageActivity.this, getString(R.string.msg_resuccess), Toast.LENGTH_LONG).show();
                    kmuFoodApplication.setUpdateChecked(true);
                    finish();
                    break;
            }
        }
    };
}
