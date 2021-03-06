package com.Intelligent.FamilyU.model.gateway.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.base.Constants;
import com.Intelligent.FamilyU.model.gateway.entity.GetawaryAddBean;
import com.Intelligent.FamilyU.model.gateway.iface.IGetawaryAddView;
import com.Intelligent.FamilyU.model.gateway.presenter.GetawaryAddPresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class GatewayAddThreeActivity extends BaseFragmentActivity implements IGetawaryAddView {

    @BindView(R.id.page_title)
    TextView titleTv;
    private RLoadingDialog mLoadingDialog;
    private GetawaryAddPresenter mGetawaryAddPresenter = new GetawaryAddPresenter(this, this);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_bind_getawary_three;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(this, true);
        titleTv.setText(R.string.home_add_gateway_bind);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.btn_ok, R.id.page_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                mGetawaryAddPresenter.bindGateway(Constants.SERIAL_NO, "");
                showLoading();
                break;
            case R.id.page_back:
                finish();
                break;
        }
    }

    @Override
    public void showResult(GetawaryAddBean bean) {
        closeLoading();
        if (null == bean) {
            return;
        }
        boolean isGetawary = bean.isResult();
        if (isGetawary) {
            startActivity(new Intent(GatewayAddThreeActivity.this, GatewayAddOkActivity.class));
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
