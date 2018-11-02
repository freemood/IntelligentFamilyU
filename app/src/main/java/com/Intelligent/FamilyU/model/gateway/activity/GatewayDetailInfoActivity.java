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
import com.Intelligent.FamilyU.model.gateway.entity.GatewayUntieBean;
import com.Intelligent.FamilyU.model.gateway.iface.IGatewayUntieView;
import com.Intelligent.FamilyU.model.gateway.presenter.GatewayRestartPresenter;
import com.Intelligent.FamilyU.model.gateway.presenter.GatewayUntiePresenter;
import com.Intelligent.FamilyU.model.main.MainActivity;
import com.Intelligent.FamilyU.model.main.event.MessageEvent;
import com.Intelligent.FamilyU.model.network.activity.NetWorkDetailBaseSettingActivity;
import com.Intelligent.FamilyU.model.network.activity.NetWorkDetailNameActivity;
import com.Intelligent.FamilyU.model.network.activity.NetWorkDetailWifiInfoActivity;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.view.SwitchView;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 组网详情
 */
public class GatewayDetailInfoActivity extends BaseFragmentActivity implements IGatewayUntieView {
    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;
    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRl;
    @BindView(R.id.switch_sclete)
    SwitchView mSwitchView;
    @BindView(R.id.gateway_name_tv)
    TextView gatewayNameTv;
    @BindView(R.id.my_zhanghao_rl)
    RelativeLayout myZhanghaoRl;

    private RLoadingDialog mLoadingDialog;
    private GatewayUntiePresenter mGatewayUntiePresenter = new GatewayUntiePresenter(this, this);
    private GatewayEventBus mGatewayEventBus;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_gateway_detail_info;
    }

    @Override
    protected void initBundleData() {


    }

    @Override
    protected void initView() {
        registeredEventBus();
        mLoadingDialog = new RLoadingDialog(mContext, true);
        titleTv.setText(R.string.home_gateway_detail_name_info);
        pageRightRl.setVisibility(View.VISIBLE);
        pageRightTv.setText(R.string.btn_untie);
    }

    @Override
    public void onFamilyEvent(Object object) {
        mGatewayEventBus = (GatewayEventBus) object;
        gatewayNameTv.setText(mGatewayEventBus.getName());
        if (TextUtils.isEmpty(mGatewayEventBus.getBroadbandAccount())) {
            myZhanghaoRl.setVisibility(View.GONE);
        }
        initSwitch(mSwitchView);
        super.onFamilyEvent(object);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.page_back, R.id.gateway_base_rl, R.id.gateway_name_rl, R.id.my_setting_rl, R.id.page_right_rl, R.id.my_zhanghao_rl})
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.page_back:
                finish();
                break;
            case R.id.gateway_base_rl:
                unregisteredEventBus();
                intent.setClass(mContext, GatewayDetailBaseSettingActivity.class);
                EventBus.getDefault().postSticky(mGatewayEventBus);
                startActivity(intent);
                break;
            case R.id.my_setting_rl:
                intent.setClass(mContext, GatewayDetailWifiInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.page_right_rl:
                mGatewayUntiePresenter.untieGateway(mGatewayEventBus.getSerialNo());
                break;
            case R.id.gateway_name_rl:
                unregisteredEventBus();
                mGatewayEventBus.setName(gatewayNameTv.getText().toString());
                EventBus.getDefault().postSticky(mGatewayEventBus);
                intent.setClass(mContext, GatewayDetailInfoNameActivity.class);
                startActivity(intent);
                break;
            case R.id.my_zhanghao_rl:
                unregisteredEventBus();
                EventBus.getDefault().postSticky(mGatewayEventBus);
                intent.setClass(mContext, GatewayDetailInfoASDLActivity.class);
                startActivity(intent);
                break;


        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getEvent(MessageEvent messageEvent) {
        gatewayNameTv.setText(messageEvent.getMessage());
        // 移除粘性事件
        EventBus.getDefault().removeStickyEvent(messageEvent);
    }

    private void initSwitch(SwitchView sv) {
//        String name = Utils.readSharedPreferences(getResources().getString(R.string.home_login_name));
//        final StringBuffer nameSb = new StringBuffer();
//        nameSb.append(name).append(key);
//        boolean isPushAble = Utils.readBoolean(nameSb.toString(), false);
        sv.setOpened("ONLINE".equals(mGatewayEventBus.getStatus()));
        sv.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                view.toggleSwitch(true); // or false
//                Utils.write(nameSb.toString(), true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.toggleSwitch(false); // or true
//                Utils.write(nameSb.toString(), false);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        registeredEventBus();
    }

    @Override
    public void showResult(GatewayUntieBean bean) {
        if (null == bean) {
            return;
        }
        showToast(getResources().getString(R.string.home_gateway_detail_untie_ok));

        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
        String mSerialNo = Utils.readSharedPreferences(sb.toString());

        if (mSerialNo.equals(bean.getGatewaySn())) {
            Utils.write(sb.toString(), "");
        }
        startActivity(new Intent(mContext, MainActivity.class));
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
