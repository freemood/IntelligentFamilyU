package com.Intelligent.FamilyU.model.gateway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.model.gateway.entity.GatewayEventBus;
import com.Intelligent.FamilyU.model.gateway.entity.GatewayModifyNameBean;
import com.Intelligent.FamilyU.model.gateway.iface.IGatewayModifyNameView;
import com.Intelligent.FamilyU.model.gateway.presenter.GatewayModifyNamePresenter;
import com.Intelligent.FamilyU.model.main.event.MessageEvent;
import com.Intelligent.FamilyU.model.network.activity.NetWorkDetailActivity;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 網関详情
 */
public class GatewayDetailInfoNameActivity extends BaseFragmentActivity implements IGatewayModifyNameView {
    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;
    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRl;

    @BindView(R.id.et_name)
    TextView networkNameTv;
    private String name;
    private GatewayModifyNamePresenter MGatewayModifyNamePresenter = new GatewayModifyNamePresenter(this, this);
    private RLoadingDialog mLoadingDialog;
    private GatewayEventBus mGatewayEventBus;
    @Override
    protected int getContentViewId() {
        registeredEventBus();
        return R.layout.activity_network_detail_name;
    }

    @Override
    protected void initBundleData() {
    }

    @Override
    public void onFamilyEvent(Object object) {
        mGatewayEventBus = (GatewayEventBus) object;
        super.onFamilyEvent(object);
    }

    @Override
    protected void initView() {
        registeredEventBus();
        mLoadingDialog = new RLoadingDialog(mContext, true);
        titleTv.setText(R.string.home_gateway_detail_name_info_neme);
        pageRightRl.setVisibility(View.VISIBLE);
        pageRightTv.setText(R.string.btn_save);

    }

    @Override
    protected void initData() {
        networkNameTv.setText(mGatewayEventBus.getName());
    }


    @OnClick({R.id.page_back, R.id.page_right_rl, R.id.network_name_rl})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_back:
                finish();
                break;
            case R.id.page_right_rl:
                String name1 = networkNameTv.getText().toString();
                if (TextUtils.isEmpty(name1)) {
                    ToastUtils.showToast(mContext, getResources().getString(R.string.home_network_detail_name_null_no));
                    return;
                }
                name = name1;
                MGatewayModifyNamePresenter.mdifyNameGateway(mGatewayEventBus.getSerialNo(), name);
                break;

        }
    }

    @Override
    public void showResult(GatewayModifyNameBean bean) {
        if (null == bean) {
            return;
        }
        if (bean.isResult()) {
            EventBus.getDefault().postSticky(new MessageEvent(name));
            finish();
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
