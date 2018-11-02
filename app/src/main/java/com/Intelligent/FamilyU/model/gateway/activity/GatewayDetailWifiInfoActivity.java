package com.Intelligent.FamilyU.model.gateway.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.model.gateway.entity.GatewayWifiInfoBean;
import com.Intelligent.FamilyU.model.gateway.iface.IGetawaryWifiInfoView;
import com.Intelligent.FamilyU.model.gateway.presenter.GatewayWifiInfoPresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 组网详情
 */
public class GatewayDetailWifiInfoActivity extends BaseFragmentActivity implements IGetawaryWifiInfoView {
    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.wifi_name)
    TextView wifiameTv;
    @BindView(R.id.password)
    TextView passwordTv;
    private RLoadingDialog mLoadingDialog;
    private GatewayWifiInfoPresenter mGatewayRestartPresenter = new GatewayWifiInfoPresenter(this, this);
    private String mSerialNo;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_network_detail_wifi_info;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        titleTv.setText(R.string.home_gateway_detail_name_info_wifi);


    }

    @Override
    protected void initData() {
        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
        mSerialNo = Utils.readSharedPreferences(sb.toString());
        if (TextUtils.isEmpty(mSerialNo)) {
            return;
        }
        mGatewayRestartPresenter.gatewayWifiInfo(mSerialNo);
    }

    @Override
    public void showResult(GatewayWifiInfoBean bean) {
        if (null == bean) {
            return;
        }
        wifiameTv.setText(bean.getSsid());
        passwordTv.setText(bean.getPassword());
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

    @OnClick({R.id.page_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_back:
                finish();
                break;
        }
    }

}
