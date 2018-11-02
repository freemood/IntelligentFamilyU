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
import com.Intelligent.FamilyU.model.login.bean.UserRegisterInfo;
import com.Intelligent.FamilyU.model.main.MainActivity;
import com.Intelligent.FamilyU.utils.RxEasyUtils;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.view.IBaseView;
import com.Intelligent.FamilyU.widget.RLoadingDialog;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginRegisterActivity extends BaseFragmentActivity implements IBaseView{

    @BindView(R.id.et_password)
    TextView passwordTv;
    @BindView(R.id.et_next_ok)
    TextView nextEt;
    private RLoadingDialog mLoadingDialog;
    private UserRegisterInfo mUserRegisterInfo;
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
                    String code = mLoginVerificationCodeBean.getResult();
                    Constants.APP_TOKEN = code;
                    int status = mLoginVerificationCodeBean.getStatus();
                    if (200 == status) {
                        startActivity(new Intent(mContext, MainActivity.class));
                    }
                    break;
                case 99:

                    break;
            }
        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_home_login_register;
    }

    @Override
    protected void initBundleData() {
        if (null == getIntent()) {
            return;
        }
        Bundle b = getIntent().getExtras();
        mUserRegisterInfo = (UserRegisterInfo) b.getSerializable("UserRegisterInfo");
    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(this, true);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_ok, R.id.page_back, R.id.btn_cancel})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                String passwordStr = passwordTv.getText().toString();
                String nextPasswordStr = nextEt.getText().toString();
                if (TextUtils.isEmpty(passwordStr) || TextUtils.isEmpty(nextPasswordStr)) {
                    ToastUtils.showToast(mContext, getResources().getString(R.string.home_login_password_null));
                    return;
                }
                if (!passwordStr.equals(nextPasswordStr)) {
                    ToastUtils.showToast(mContext, getResources().getString(R.string.home_login_password_atypism));
                    return;
                }
                mUserRegisterInfo.setPassword(nextPasswordStr);
                RxEasyUtils.startPatchObservable(Constants.APP_REGISTER_URL, new Gson().toJson(mUserRegisterInfo), mhandler);
                showLoading();
                break;
            case R.id.btn_cancel:
                mUserRegisterInfo.setPassword("");
                RxEasyUtils.startPatchObservable(Constants.APP_REGISTER_URL, new Gson().toJson(mUserRegisterInfo), mhandler);
                showLoading();
                break;
            case R.id.page_back:
                finish();
                break;
        }
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
