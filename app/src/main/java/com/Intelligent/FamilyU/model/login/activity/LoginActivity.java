package com.Intelligent.FamilyU.model.login.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.base.Constants;
import com.Intelligent.FamilyU.manager.ActivityStackManager;
import com.Intelligent.FamilyU.model.login.bean.LoginBean;
import com.Intelligent.FamilyU.model.login.bean.LoginVerificationCodeBean;
import com.Intelligent.FamilyU.model.login.bean.UserRegisterInfo;
import com.Intelligent.FamilyU.model.login.iface.ILoginView;
import com.Intelligent.FamilyU.model.login.presenter.LoginCodePresenter;
import com.Intelligent.FamilyU.model.login.presenter.LoginPresenter;
import com.Intelligent.FamilyU.model.main.MainActivity;
import com.Intelligent.FamilyU.utils.NetworkUtils;
import com.Intelligent.FamilyU.utils.RxEasyUtils;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.widget.RLoadingDialog;
import com.google.gson.Gson;


import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseFragmentActivity implements ILoginView {

    @BindView(R.id.et_user_name)
    TextView nameEt;
    @BindView(R.id.et_password)
    TextView passwordEv;
    @BindView(R.id.et_phone)
    TextView phoneEv;
    @BindView(R.id.et_verification)
    TextView verificationEt;
    @BindView(R.id.verification_code_tv)
    TextView verificationCodeTv;
    @BindView(R.id.login_name_ll)
    LinearLayout loginNamell;
    @BindView(R.id.login_phone_ll)
    LinearLayout loginPhonell;
    @BindView(R.id.verification_tv)
    TextView verificationTv;
    private int codeTime = 60;
    private long clickTime = 0; // 第一次点击的时间
    private String mAccountNumber = "";
    private RLoadingDialog mLoadingDialog;
    private LoginPresenter mLoginPresenter = new LoginPresenter(this, this);
    private LoginCodePresenter mLoginCodePresenter = new LoginCodePresenter(this, this);
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
        return R.layout.activity_home_login;
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

        nameEt.setText(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name)));
        // passwordEv.setText("xiaoming");
        verificationCodeTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
    }

    @OnClick({R.id.login_no_password_tv, R.id.verification_code_tv, R.id.login, R.id.verification_tv, R.id.page_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                String verification = verificationCodeTv.getText().toString();
                if (verification.contains(getResources().getString(R.string.home_verification_code))) {
                    String name = nameEt.getText().toString();
                    String password = passwordEv.getText().toString();
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
                        showToast(getResources().getString(R.string.home_login_null));
                        return;
                    }
                    if (!(password.length() >= 6 && password.length() <= 18)) {
                        showToast(getResources().getString(R.string.home_login_password_length));
                        return;
                    }

                    if (!NetworkUtils.isNetworkConnected()) {
                        showToast(getResources().getString(R.string.home_not_network));
                        return;
                    }
                    mAccountNumber = name;
                    mLoginPresenter.startLogin(name, password);
                } else {
                    /*
                     *验证码登录
                     */
                    String phone = phoneEv.getText().toString();
                    String verificationEtstr = verificationEt.getText().toString();
                    if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(verificationEtstr)) {
                        showToast(getResources().getString(R.string.home_login_phone_yanzhen_null));
                        return;
                    }
                    mAccountNumber = phone;
                    mLoginCodePresenter.startLogin(phone, verificationEtstr);
                }
//                startActivity(new Intent(this, MainActivity.class));
//                finish();
                break;
            case R.id.login_no_password_tv:
                Intent intentPsw = new Intent(this, LoginForgetPasswordActivity.class);
                startActivity(intentPsw);
                break;
            case R.id.verification_code_tv:
                String verificationStr = verificationCodeTv.getText().toString();
                if (verificationStr.contains(getResources().getString(R.string.home_verification_code))) {
                    loginNamell.setVisibility(View.GONE);
                    loginPhonell.setVisibility(View.VISIBLE);
                    verificationCodeTv.setText(R.string.home_login_name_password);
                } else {
                    loginNamell.setVisibility(View.VISIBLE);
                    loginPhonell.setVisibility(View.GONE);
                    verificationCodeTv.setText(R.string.home_login_verification_code);
                }
                break;
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
                ActivityStackManager.getManager().exitApp(mContext);
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
    public void showResult(LoginBean bean) {
        if (null == bean) {
            return;
        }
        Constants.APP_TOKEN = bean.getToken();
        Utils.write(getResources().getString(R.string.home_login_name), mAccountNumber);
        Intent intentMain = new Intent(this, MainActivity.class);
        intentMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentMain);
        finish();
    }

    @Override
    public void showErrorResult(int code) {
//        if (500 == code) {
//            userRegister();
//        }
    }

//    private void userRegister() {
//        String verification = verificationCodeTv.getText().toString();
//        if (verification.contains(getResources().getString(R.string.home_verification_code))) {
//            return;
//        }
//        String phone = phoneEv.getText().toString();
//        String verificationEtstr = verificationEt.getText().toString();
//        Bundle b = new Bundle();
//        UserRegisterInfo mUserRegisterInfo = new UserRegisterInfo();
//        mUserRegisterInfo.setCode(verificationEtstr);
//        mUserRegisterInfo.setPhone(phone);
//        mUserRegisterInfo.setUsername(phone);
//        mUserRegisterInfo.setName(phone);
//        mUserRegisterInfo.setToken(Constants.CODE_TOKEN);
//        b.putSerializable("UserRegisterInfo", mUserRegisterInfo);
//
//        Intent intentMain = new Intent(this, LoginRegisterActivity.class);
//        intentMain.putExtras(b);
//        startActivity(intentMain);
//    }

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

    @Override
    public void onBackPressed() {
        exit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 是否触发按键为back键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        } else {
            // 如果不是back键正常响应
            return super.onKeyDown(keyCode, event);
        }
    }

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            ToastUtils.showToast(this, getResources().getString(R.string.home_exits));
            clickTime = System.currentTimeMillis();
        } else {
            ActivityStackManager.getManager().exitApp(mContext);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mhandler.removeCallbacksAndMessages(null);
    }
}
