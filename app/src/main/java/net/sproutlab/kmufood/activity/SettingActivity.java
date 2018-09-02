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

    private String APP_VER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        PrefHelper prefHelper = new PrefHelper(getApplicationContext());

        findViewById(R.id.btn_feedback).setOnClickListener(this);
        findViewById(R.id.btn_updatelog).setOnClickListener(this);
        findViewById(R.id.btn_opensource).setOnClickListener(this);

        TextView txtPrefer = findViewById(R.id.setting_prefer_msg);
        TextView txtLastUpdate = findViewById(R.id.setting_dataver_label_date);
        TextView txtUpdateMessage = findViewById(R.id.setting_dataver_label_msg);

        switch (prefHelper.getPreferFood()) {
            case "stu":
                txtPrefer.setText(getString(R.string.stu_title));
                break;
            case "law":
                txtPrefer.setText(getString(R.string.law_title));
                break;
            case "staff":
                txtPrefer.setText(getString(R.string.staff_title));
                break;
            case "dorm":
                txtPrefer.setText(getString(R.string.dorm_title));
                break;
            case "chung":
                txtPrefer.setText(getString(R.string.chung_title));
                break;
            default:
                txtPrefer.setText(getString(R.string.null_title));
                break;
        }
        txtLastUpdate.setText(prefHelper.getLastUpdate());
        if (prefHelper.needUpdate())
            txtUpdateMessage.setText(getString(R.string.setting_dataver_msg_old));
        else txtUpdateMessage.setText(getString(R.string.setting_dataver_msg_ok));

        try {
            APP_VER = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ((TextView) findViewById(R.id.setting_appver_msg)).setText(APP_VER);
        ((Button) findViewById(R.id.btn_updatelog)).setText(String.format(getString(R.string.label_updatelog), APP_VER));
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
                break;
            default:
                break;
        }
    }
}