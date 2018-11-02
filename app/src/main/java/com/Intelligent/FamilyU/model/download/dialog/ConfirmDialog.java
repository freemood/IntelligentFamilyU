package com.Intelligent.FamilyU.model.download.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.utils.ToastUtils;

public class ConfirmDialog extends Dialog {

    private Context context;
    private TextView etUrl;
    private ClickListenerInterface clickListenerInterface;
    public interface ClickListenerInterface {

        public void doConfirm(String title);

        public void doCancel();
    }

    public ConfirmDialog(Context context) {
        super(context, R.style.MyDialog);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_show_download, null);
        setContentView(view);

        TextView tvConfirm = (TextView) view.findViewById(R.id.btm_ok);
        TextView tvCancel = (TextView) view.findViewById(R.id.btm_cannel);
         etUrl = (TextView) view.findViewById(R.id.et_url);
        etUrl.setText("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4");
        tvConfirm.setOnClickListener(new clickListener());
        tvCancel.setOnClickListener(new clickListener());

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            int id = v.getId();
            switch (id) {
                case R.id.btm_ok:
                    String title = etUrl.getText().toString();
                    if (TextUtils.isEmpty(title)) {
                        ToastUtils.showToast(context, context.getResources().getString(R.string.home_url_null_no));
                        return;
                    }
                    clickListenerInterface.doConfirm(title);
                    break;
                case R.id.btm_cannel:
                    clickListenerInterface.doCancel();
                    break;
            }
        }

    }

    ;

}