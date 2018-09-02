package net.sproutlab.kmufood.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import net.sproutlab.kmufood.R;
import net.sproutlab.kmufood.activity.SettingActivity;

/**
 * Created by kde713 on 2016. 9. 7..
 */
public class OtherFoodDialog extends Dialog implements View.OnClickListener {

    private final Context c;

    public OtherFoodDialog(Context context) {
        super(context);
        this.c = context;
        setContentView(R.layout.dialog_otherfood);

        findViewById(R.id.btn_stufood).setOnClickListener(this);
        findViewById(R.id.btn_lawfood).setOnClickListener(this);
        findViewById(R.id.btn_stafffood).setOnClickListener(this);
        findViewById(R.id.btn_dormfood).setOnClickListener(this);
        findViewById(R.id.btn_chungfood).setOnClickListener(this);

        findViewById(R.id.btn_feedback).setOnClickListener(this);
        findViewById(R.id.btn_setting).setOnClickListener(this);

        findViewById(R.id.btn_close).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        OtherFoodInterface mInterface = (OtherFoodInterface) c;
        switch (view.getId()) {
            case R.id.btn_stufood:
                mInterface.switchAction("stu");
                dismiss();
                break;
            case R.id.btn_lawfood:
                mInterface.switchAction("law");
                dismiss();
                break;
            case R.id.btn_stafffood:
                mInterface.switchAction("staff");
                break;
            case R.id.btn_dormfood:
                mInterface.switchAction("dorm");
                dismiss();
                break;
            case R.id.btn_chungfood:
                mInterface.switchAction("chung");
                dismiss();
                break;
            case R.id.btn_feedback:
                (new FeedbackDialog(c)).show();
                dismiss();
                break;
            case R.id.btn_setting:
                c.startActivity(new Intent(c, SettingActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                dismiss();
                break;
            case R.id.btn_close:
                dismiss();
                break;
        }
    }
}
