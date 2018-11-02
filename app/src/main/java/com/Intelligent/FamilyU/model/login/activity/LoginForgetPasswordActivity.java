package com.Intelligent.FamilyU.model.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.base.Constants;
import com.Intelligent.FamilyU.model.login.bean.LoginVerificationCodeBean;
import com.Intelligent.FamilyU.model.login.bean.UserResetPwdInfo;
import com.Intelligent.FamilyU.utils.RxEasyUtils;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.view.IBaseView;
import com.Intelligent.FamilyU.widget.RLoadingDialog;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginForgetPasswordActivity extends BaseFragmentActivity implements IBaseView {
    private RLoadingDialog mLoadingDialog;
    @BindView(R.id.et_phone)
    TextView phoneEv;
    @BindView(R.id.et_verification)
    TextView verificationEt;
    @BindView(R.id.verification_tv)
    TextView verificationTv;
    private int codeTime = 60;
    private android.os.Handler mhandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    closeLoading();
                    String result = (String) msg.obj;
                    if (TextUtils.isEmpty(result)) {
                        return;
                    }
                    Gson gson = new Gson();
                    LoginVerificationCodeBean mLoginVerificationCodeBean = gson.fromJson(result, LoginVerificationCodeBean.class);
                    if (TextUtils.isEmpty(mLoginVerificationCodeBean.getResult())) {
                        return;
                    }
                    String verificationCode = mLoginVerificationCodeBean.getResult();
                    Constants.CODE_TOKEN = verificationCode;
                    break;
                case 99:
                    closeLoading();
                    break;
                case 1:
                    postCodeTime();
                    break;
            }
        }
    };
    @Override
    protected int getContentViewId() {
        return R.layout.activity_home_login_find_password;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(this, true);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_ok, R.id.verification_tv,R.id.page_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verification_tv:
                String phone = phoneEv.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    showToast(getResources().getString(R.string.home_login_phone_null));
                    return;
                }
                if (!Utils.isMobile(phone) || !Utils.isChinaPhoneLegal(phone)) {
                    showToast(getResources().getString(R.string.home_login_phone_pattern));
                    return;
                }
                StringBuffer phoneUrl = new StringBuffer();
                phoneUrl.append(Constants.APP_SEND_MESSAGE_URL).append(phone);
                postCodeTime();
                RxEasyUtils.startPostObservable(phoneUrl.toString(), "", mhandler);
                showLoading();
                break;
            case R.id.page_back:
                finish();
                break;
            case R.id.btn_ok:
                String phonestr = phoneEv.getText().toString();
                String verificationEtstr = verificationEt.getText().toString();
                if (TextUtils.isEmpty(phonestr) || TextUtils.isEmpty(verificationEtstr)) {
                    showToast(getResources().getString(R.string.home_login_phone_yanzhen_null));
                    return;
                }
                Bundle b = new Bundle();
                UserResetPwdInfo mUserResetPwdInfo = new UserResetPwdInfo();
                mUserResetPwdInfo.setCode(verificationEtstr);
                mUserResetPwdInfo.setPhone(phonestr);
                mUserResetPwdInfo.setUsername(phonestr);
                mUserResetPwdInfo.setToken(Constants.CODE_TOKEN);
                b.putSerializable("UserResetPwdInfo", mUserResetPwdInfo);

                Intent intentMain = new Intent(this, LoginModifyPasswordActivity.class);
                intentMain.putExtras(b);
                startActivity(intentMain);
                break;
        }
    }


    private void postCodeTime() {
        if (codeTime == 0) {
            mhandler.removeCallbacksAndMessages(null);
            codeTime = 60;
            verificationTv.setEnabled(true);
            verificationTv.setText(mContext.getResources().getString(R.string.home_get_verification_code));
            return;
        }

        verificationTv.setEnabled(false);
        verificationTv.setText(mContext.getResources().getString(R.string.home_verification_code_time, codeTime));
        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                codeTime -= 1;
                mhandler.sendEmptyMessage(1);
            }
        }, 1000);
    }

    @Override
    public void showLoading() {
        mLoadingDialog.show();
    }

    @Override
    public void closeLoading() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(mContext, msg);
    }
}
