package com.Intelligent.FamilyU.model.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.model.login.bean.LoginModifyBean;
import com.Intelligent.FamilyU.model.login.bean.UserResetPwdInfo;
import com.Intelligent.FamilyU.model.login.iface.ILoginModifyView;
import com.Intelligent.FamilyU.model.login.presenter.LoginModifyPresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginModifyPasswordActivity extends BaseFragmentActivity implements ILoginModifyView {

    @BindView(R.id.et_password)
    TextView passwordTv;
    @BindView(R.id.et_next_ok)
    TextView nextEt;
    private RLoadingDialog mLoadingDialog;
    private UserResetPwdInfo mUserResetPwdInfo;
    private LoginModifyPresenter mLoginModifyPresenter = new LoginModifyPresenter(this, this);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_home_login_modify_password;
    }

    @Override
    protected void initBundleData() {
        if (null == getIntent()) {
            return;
        }
        Bundle b = getIntent().getExtras();
        mUserResetPwdInfo = (UserResetPwdInfo) b.getSerializable("UserResetPwdInfo");
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
                    showToast(getResources().getString(R.string.home_login_password_null));
                    return;
                }

                if (!passwordStr.equals(nextPasswordStr)) {
                    showToast(getResources().getString(R.string.home_login_password_atypism));
                    return;
                }

                if (!(passwordStr.length() >= 6 && passwordStr.length() <= 18) || !(nextPasswordStr.length() >= 6 && nextPasswordStr.length() <= 18)) {
                    showToast(getResources().getString(R.string.home_login_password_length));
                    return;
                }

                mUserResetPwdInfo.setNewPassword(passwordStr);
                mUserResetPwdInfo.setSecInputPassword(nextPasswordStr);
                mLoginModifyPresenter.startLoginModifyPassword(mUserResetPwdInfo);
                showLoading();
                break;
            case R.id.btn_cancel:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.page_back:
                finish();
                break;
        }
    }

    @Override
    public void showResult(LoginModifyBean bean) {
        if (null == bean) {
            return;
        }
        boolean isModify = bean.isResult();
        if (isModify) {
            startActivity(new Intent(mContext, LoginActivity.class));
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
