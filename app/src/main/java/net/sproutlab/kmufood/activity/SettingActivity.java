package net.sproutlab.kmufood.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.sproutlab.kmufood.R;
import net.sproutlab.kmufood.dialog.FeedbackDialog;
import net.sproutlab.kmufood.dialog.UpdatelogDialog;
import net.sproutlab.kmufood.utils.PrefHelper;

import de.psdev.licensesdialog.LicensesDialog;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    PrefHelper prefHelper;

    TextView txt_prefer;
    TextView txt_dataver_date;
    TextView txt_dataver_msg;

    String APP_VER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        startActivity((new Intent(this, SplashActivity.class)).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));

        prefHelper = new PrefHelper(getApplicationContext());

        findViewById(R.id.btn_feedback).setOnClickListener(this);
        findViewById(R.id.btn_updatelog).setOnClickListener(this);
        findViewById(R.id.btn_opensource).setOnClickListener(this);

        txt_prefer = (TextView) findViewById(R.id.setting_prefer_msg);
        txt_dataver_date = (TextView) findViewById(R.id.setting_dataver_label_date);
        txt_dataver_msg = (TextView) findViewById(R.id.setting_dataver_label_msg);

        switch (prefHelper.getPreferFood()) {
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
        txt_dataver_date.setText(prefHelper.getLastUpdate());
        if (prefHelper.needUpdate())
            txt_dataver_msg.setText(getString(R.string.setting_dataver_msg_old));
        else txt_dataver_msg.setText(getString(R.string.setting_dataver_msg_ok));

        try {
            APP_VER = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ((TextView) findViewById(R.id.setting_appver_msg)).setText(APP_VER);
        ((Button) findViewById(R.id.btn_updatelog)).setText(String.format(getString(R.string.label_updatelog), APP_VER));
    }

    @Override
    public void onResume() {
        super.onResume();
        switch (prefHelper.getPreferFood()) {
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
        txt_dataver_date.setText(prefHelper.getLastUpdate());
        if (prefHelper.needUpdate())
            txt_dataver_msg.setText(getString(R.string.setting_dataver_msg_old));
        else txt_dataver_msg.setText(getString(R.string.setting_dataver_msg_ok));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_feedback:
                (new FeedbackDialog(SettingActivity.this)).show();
                break;
            case R.id.btn_updatelog:
                (new UpdatelogDialog(SettingActivity.this, APP_VER)).show();
                break;
            case R.id.btn_opensource:
                new LicensesDialog.Builder(SettingActivity.this)
                        .setNotices(R.raw.opensourcelicense)
                        .build()
                        .show();
        }
    }
}