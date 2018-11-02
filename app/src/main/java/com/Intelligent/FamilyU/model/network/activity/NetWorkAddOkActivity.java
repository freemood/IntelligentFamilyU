package com.Intelligent.FamilyU.model.network.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.base.Constants;
import com.Intelligent.FamilyU.model.gateway.entity.GetawaryAddBean;
import com.Intelligent.FamilyU.model.gateway.iface.IGetawaryAddView;
import com.Intelligent.FamilyU.model.gateway.presenter.GetawaryAddPresenter;
import com.Intelligent.FamilyU.model.main.MainActivity;
import com.Intelligent.FamilyU.model.network.bean.NetWorkBindBean;
import com.Intelligent.FamilyU.model.network.iface.INetWorkBineView;
import com.Intelligent.FamilyU.model.network.presenter.NetWorkBindPresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class NetWorkAddOkActivity extends BaseFragmentActivity implements INetWorkBineView {

    @BindView(R.id.page_title)
    TextView titleTv;
    private RLoadingDialog mLoadingDialog;
    private NetWorkBindPresenter mNetWorkBindPresenter = new NetWorkBindPresenter(this, this);
    @Override
    protected int getContentViewId() {
        return R.layout.activity_bind_getawary_ok;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(this, true);
        titleTv.setText(R.string.home_add_network_bind);
    }

    @Override
    protected void initData() {
        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
       String mSerialNo = Utils.readSharedPreferences(sb.toString());
        if (TextUtils.isEmpty(mSerialNo)) {
            showToast("请先绑定网关设备");
            return;
        }
        mNetWorkBindPresenter.bindNetWork(mSerialNo, Constants.NETWORK_NO);
    }


    @OnClick({R.id.btn_ok, R.id.page_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                Intent mIntent = new Intent(NetWorkAddOkActivity.this, MainActivity.class);
                startActivity(mIntent);
                break;
            case R.id.page_back:
                //finniss
                finish();
                break;
        }
    }

    @Override
    public void showResult(NetWorkBindBean bean) {
        closeLoading();
        if (null == bean) {
            return;
        }
        boolean isGetawary = bean.isResult();
        if (isGetawary) {
            showToast(getResources().getString(R.string.home_add_gateway_ok_title));
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
