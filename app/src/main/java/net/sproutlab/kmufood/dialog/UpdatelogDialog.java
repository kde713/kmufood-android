package net.sproutlab.kmufood.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import net.sproutlab.kmufood.R;

/**
 * Created by kde713 on 2016. 9. 7..
 */
public class UpdatelogDialog extends Dialog implements View.OnClickListener {

    public UpdatelogDialog(Context context, String APP_VER) {
        super(context);
        setContentView(R.layout.dialog_foodinfo);

        findViewById(R.id.btn_close).setOnClickListener(this);

        ((TextView) findViewById(R.id.dialog_title)).setText(String.format(context.getString(R.string.label_updatelog), APP_VER));
        ((TextView) findViewById(R.id.dialog_content)).setText(context.getString(R.string.updatelog));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close:
                dismiss();
                break;
        }
    }
}
