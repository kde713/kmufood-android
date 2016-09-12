package net.sproutlab.kmufood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import net.sproutlab.kmufood.R;
import net.sproutlab.kmufood.data.Prefdata;
import net.sproutlab.kmufood.dialog.FeedbackDialog;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    Prefdata mPrefAdapter;

    TextView txt_prefer;
    TextView txt_dataver_date;
    TextView txt_dataver_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        startActivity((new Intent(this, SplashActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));

        mPrefAdapter = new Prefdata(getApplicationContext());

        findViewById(R.id.btn_feedback).setOnClickListener(this);

        txt_prefer = (TextView) findViewById(R.id.setting_prefer_msg);
        txt_dataver_date = (TextView) findViewById(R.id.setting_dataver_label_date);
        txt_dataver_msg = (TextView) findViewById(R.id.setting_dataver_label_msg);

        switch (mPrefAdapter.getPreferfood()) {
            case "stu":
                txt_prefer.setText(getString(R.string.stu_title));
                break;
            case "law":
                txt_prefer.setText(getString(R.string.law_title));
                break;
            case "staff":
                txt_prefer.setText(getString(R.string.staff_title));
                break;
            case "dorm":
                txt_prefer.setText(getString(R.string.dorm_title));
                break;
            case "chung":
                txt_prefer.setText(getString(R.string.chung_title));
                break;
        }
        txt_dataver_date.setText(mPrefAdapter.getTS());
        if (mPrefAdapter.checkTS())
            txt_dataver_msg.setText(getString(R.string.setting_dataver_msg_old));
        else txt_dataver_msg.setText(getString(R.string.setting_dataver_msg_ok));
    }

    @Override
    public void onResume() {
        super.onResume();
        switch (mPrefAdapter.getPreferfood()) {
            case "stu":
                txt_prefer.setText(getString(R.string.stu_title));
                break;
            case "law":
                txt_prefer.setText(getString(R.string.law_title));
                break;
            case "staff":
                txt_prefer.setText(getString(R.string.staff_title));
                break;
            case "dorm":
                txt_prefer.setText(getString(R.string.dorm_title));
                break;
            case "chung":
                txt_prefer.setText(getString(R.string.chung_title));
                break;
        }
        txt_dataver_date.setText(mPrefAdapter.getTS());
        if (mPrefAdapter.checkTS())
            txt_dataver_msg.setText(getString(R.string.setting_dataver_msg_old));
        else txt_dataver_msg.setText(getString(R.string.setting_dataver_msg_ok));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_feedback:
                (new FeedbackDialog(SettingActivity.this)).show();
                break;
        }
    }
}