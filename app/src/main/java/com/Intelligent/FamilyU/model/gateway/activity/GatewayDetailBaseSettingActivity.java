package com.Intelligent.FamilyU.model.gateway.activity;

import android.view.View;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.model.gateway.entity.GatewayEventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 网详情
 */
public class GatewayDetailBaseSettingActivity extends BaseFragmentActivity {
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
    private GatewayEventBus mGatewayEventBus;

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
        titleTv.setText(R.string.home_gateway_detail_name_info_base);


    }

    @Override
    public void onFamilyEvent(Object object) {
        mGatewayEventBus = (GatewayEventBus) object;
        super.onFamilyEvent(object);
    }

    @Override
    protected void initData() {
        manufactorTv.setText(mGatewayEventBus.getBasename());
        deviceTv.setText(mGatewayEventBus.getBasedevicename());
        macTv.setText(mGatewayEventBus.getBasemac());
        snTv.setText(mGatewayEventBus.getBasesn());
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
