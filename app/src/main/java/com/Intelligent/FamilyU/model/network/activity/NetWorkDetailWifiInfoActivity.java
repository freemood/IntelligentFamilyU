package com.Intelligent.FamilyU.model.network.activity;

import android.view.View;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.model.network.bean.NetWorkEventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 组网详情
 */
public class NetWorkDetailWifiInfoActivity extends BaseFragmentActivity {
    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.wifi_name)
    TextView wifiameTv;
    @BindView(R.id.password)
    TextView passwordTv;
    private NetWorkEventBus mNetWorkEventBus;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_network_detail_wifi_info;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        registeredEventBus();
        titleTv.setText(R.string.home_network_detail_wifi_info);
    }

    @Override
    public void onFamilyEvent(Object object) {
        mNetWorkEventBus = (NetWorkEventBus) object;
        super.onFamilyEvent(object);
    }

    @Override
    protected void initData() {

        wifiameTv.setText(mNetWorkEventBus.getWifiname());
        passwordTv.setText(mNetWorkEventBus.getWifipassword());
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
