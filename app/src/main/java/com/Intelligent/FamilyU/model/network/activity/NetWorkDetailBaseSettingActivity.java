package com.Intelligent.FamilyU.model.network.activity;

import android.content.Intent;
import android.os.Bundle;
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
public class NetWorkDetailBaseSettingActivity extends BaseFragmentActivity {
    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.manufactor_name)
    TextView manufactorTv;
    @BindView(R.id.device_name)
    TextView deviceTv;
    @BindView(R.id.mac_name)
    TextView macTv;
    @BindView(R.id.sn_name)
    TextView snTv;
    private NetWorkEventBus mNetWorkEventBus;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_network_detail_base;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        registeredEventBus();
        titleTv.setText(R.string.home_network_detail_base_name);


    }

    @Override
    public void onFamilyEvent(Object object) {
        mNetWorkEventBus = (NetWorkEventBus) object;
        super.onFamilyEvent(object);
    }

    @Override
    protected void initData() {
        manufactorTv.setText(mNetWorkEventBus.getBasename());
        deviceTv.setText(mNetWorkEventBus.getBasedevicename());
        macTv.setText(mNetWorkEventBus.getBasemac());
        snTv.setText(mNetWorkEventBus.getBasesn());
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
