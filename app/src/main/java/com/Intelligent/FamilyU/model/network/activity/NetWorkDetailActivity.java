package com.Intelligent.FamilyU.model.network.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.model.main.event.MessageEvent;
import com.Intelligent.FamilyU.model.network.bean.NetWorkEventBus;
import com.Intelligent.FamilyU.model.network.bean.NetWorkUnbindBean;
import com.Intelligent.FamilyU.model.network.iface.INetWorkUnbineView;
import com.Intelligent.FamilyU.model.network.presenter.NetWorkUnbinePresenter;
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
public class NetWorkDetailActivity extends BaseFragmentActivity implements INetWorkUnbineView {
    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;
    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRl;
    @BindView(R.id.switch_sclete)
    SwitchView mSwitchView;
    @BindView(R.id.network_name_tv)
    TextView networkNameTv;
    private RLoadingDialog mLoadingDialog;
    private NetWorkEventBus mNetWorkEventBus;
    private NetWorkUnbinePresenter mNetWorkUnbinePresenter = new NetWorkUnbinePresenter(this, this);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_network_detail;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    public void onFamilyEvent(Object object) {
        mNetWorkEventBus = (NetWorkEventBus) object;
        networkNameTv.setText(mNetWorkEventBus.getName());
        initSwitch(mSwitchView);
        super.onFamilyEvent(object);
    }

    @Override
    protected void initView() {
        registeredEventBus();
        mLoadingDialog = new RLoadingDialog(mContext, true);
        titleTv.setText(R.string.home_network_detail_title);
        pageRightRl.setVisibility(View.VISIBLE);
        pageRightTv.setText(R.string.btn_untie);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.page_back, R.id.network_base_rl, R.id.network_name_rl, R.id.my_setting_rl, R.id.page_right_rl, R.id.my_state_rl})
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.page_back:
                finish();
                break;
            case R.id.network_base_rl:
                unregisteredEventBus();
                EventBus.getDefault().postSticky(mNetWorkEventBus);

                intent.setClass(mContext, NetWorkDetailBaseSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.my_setting_rl:
                unregisteredEventBus();
                EventBus.getDefault().postSticky(mNetWorkEventBus);

                intent.setClass(mContext, NetWorkDetailWifiInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.page_right_rl:
                mNetWorkUnbinePresenter.unbindNetWork(mNetWorkEventBus.getSerialNo(), mNetWorkEventBus.getBasemac());
                break;
            case R.id.network_name_rl:
                unregisteredEventBus();
                mNetWorkEventBus.setName(networkNameTv.getText().toString());
                EventBus.getDefault().postSticky(mNetWorkEventBus);

                intent.setClass(mContext, NetWorkDetailNameActivity.class);
                startActivity(intent);
                break;
            case R.id.my_zhanghao_rl:

                break;
            case R.id.my_state_rl:

                break;

        }
    }

    private void initSwitch(SwitchView sv) {
//        String name = Utils.readSharedPreferences(getResources().getString(R.string.home_login_name));
//        final StringBuffer nameSb = new StringBuffer();
//        nameSb.append(name).append(key);
//        boolean isPushAble = Utils.readBoolean(nameSb.toString(), false);
        sv.setOpened("1".equals(mNetWorkEventBus.getStatus()));
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getEvent(MessageEvent messageEvent) {
        networkNameTv.setText(messageEvent.getMessage());
        // 移除粘性事件
        EventBus.getDefault().removeStickyEvent(messageEvent);
    }

    @Override
    public void showResult(NetWorkUnbindBean bean) {
        if (null == bean) {
            return;
        }
        if (bean.isResult()) {
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
