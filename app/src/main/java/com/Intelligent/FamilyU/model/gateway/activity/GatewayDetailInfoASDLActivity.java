package com.Intelligent.FamilyU.model.gateway.activity;

import android.content.Intent;
import android.os.Bundle;
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
public class
GatewayDetailInfoASDLActivity extends BaseFragmentActivity {
    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.adsl_name)
    TextView adslTv;
    private GatewayEventBus mGatewayEventBus;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_network_detail_adsl_info;
    }

    @Override
    protected void initBundleData() {
    }

    @Override
    protected void initView() {
        registeredEventBus();
        titleTv.setText(R.string.home_network_detail_zhanghao_name);

    }

    @Override
    protected void initData() {
        adslTv.setText(mGatewayEventBus.getBroadbandAccount());
    }

    @Override
    public void onFamilyEvent(Object object) {
        mGatewayEventBus = (GatewayEventBus) object;
        super.onFamilyEvent(object);
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
