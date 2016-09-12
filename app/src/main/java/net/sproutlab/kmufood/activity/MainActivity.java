package net.sproutlab.kmufood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.sproutlab.kmufood.KMUFoodApplication;
import net.sproutlab.kmufood.R;
import net.sproutlab.kmufood.data.Prefdata;
import net.sproutlab.kmufood.dialog.FeedbackDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Prefdata mPrefAdapter;
    KMUFoodApplication kmuFoodApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kmuFoodApplication = (KMUFoodApplication) getApplicationContext();

        startActivity((new Intent(this, SplashActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));

        mPrefAdapter = new Prefdata(getApplicationContext());
        findViewById(R.id.btn_feedback).setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (kmuFoodApplication.isUpdateChecked()) {
            switch (mPrefAdapter.getPreferfood()) {
                case "stu":
                    startActivity((new Intent(this, StuFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                    break;
                case "law":
                    startActivity((new Intent(this, LawFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                    break;
                case "staff":
                    startActivity((new Intent(this, StaffFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                    break;
                case "dorm":
                    startActivity((new Intent(this, DormFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                    break;
                case "chung":
                    startActivity((new Intent(this, ChungFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                    break;
                default:
                    findViewById(R.id.btn_stufood).setOnClickListener(MainActivity.this);
                    findViewById(R.id.btn_lawfood).setOnClickListener(MainActivity.this);
                    findViewById(R.id.btn_stafffood).setOnClickListener(MainActivity.this);
                    findViewById(R.id.btn_dormfood).setOnClickListener(MainActivity.this);
                    findViewById(R.id.btn_chungfood).setOnClickListener(MainActivity.this);
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_stufood:
                mPrefAdapter.setPreferfood("stu");
                startActivity((new Intent(this, StuFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
            case R.id.btn_lawfood:
                mPrefAdapter.setPreferfood("law");
                startActivity((new Intent(this, LawFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
            case R.id.btn_stafffood:
                mPrefAdapter.setPreferfood("staff");
                startActivity((new Intent(this, StaffFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
            case R.id.btn_dormfood:
                mPrefAdapter.setPreferfood("dorm");
                startActivity((new Intent(this, DormFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
            case R.id.btn_chungfood:
                mPrefAdapter.setPreferfood("chung");
                startActivity((new Intent(this, ChungFoodActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
            case R.id.btn_feedback:
                (new FeedbackDialog(MainActivity.this)).show();
                break;
        }
    }
}