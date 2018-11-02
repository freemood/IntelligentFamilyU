package com.Intelligent.FamilyU.model.gateway.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.model.home.activity.DeviceAddActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class GatewayAddTwoActivity extends BaseFragmentActivity {

    @BindView(R.id.page_title)
    TextView titleTv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_bind_getawary_two;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        titleTv.setText(R.string.home_add_gateway_bind);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.add_gateway, R.id.page_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_gateway:
                startActivity(new Intent(GatewayAddTwoActivity.this, GatewayAddThreeActivity.class));
                break;
            case R.id.page_back:
                finish();
                break;
        }
    }

}
