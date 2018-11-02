package com.Intelligent.FamilyU.model.message.activity;

import android.view.View;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.view.SwitchView;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 消息设置
 */
public class MessageSettingActivity extends BaseFragmentActivity {
    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.threshold_message)
    TextView thresholdMessageTv;

    @BindView(R.id.switch_app_login)
    SwitchView mSwitchViewLogin;
    @BindView(R.id.switch_telphone)
    SwitchView mSwitchhomeTelphone;
    @BindView(R.id.switch_message)
    SwitchView mSwitchViewMessage;
    @BindView(R.id.switch_phone_fault)
    SwitchView mSwitchViewPhoneFault;
    @BindView(R.id.fault_switch_message)
    SwitchView mSwitchViewFaultMessage;
    @BindView(R.id.voice_switch_telphone)
    SwitchView mSwitchViewVoice;
    @BindView(R.id.switch_shock)
    SwitchView mSwitchViewShock;

    private RLoadingDialog mLoadingDialog;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_message_setting;
    }

    @Override
    protected void initBundleData() {
    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        titleTv.setText(R.string.home_message_setting);
        initSwitch(mSwitchViewLogin, getResources().getString(R.string.home_message_setting_applogin_name));
        initSwitch(mSwitchhomeTelphone, getResources().getString(R.string.home_monitoring_setting_threshold) + getResources().getString(R.string.home_telphone));
        initSwitch(mSwitchViewMessage, getResources().getString(R.string.home_monitoring_setting_threshold) + getResources().getString(R.string.home_message_shore));
        initSwitch(mSwitchViewPhoneFault, getResources().getString(R.string.home_device_fault) + getResources().getString(R.string.home_telphone));
        initSwitch(mSwitchViewFaultMessage, getResources().getString(R.string.home_device_fault) + getResources().getString(R.string.home_message_shore));
        initSwitch(mSwitchViewVoice, getResources().getString(R.string.home_message_voice));
        initSwitch(mSwitchViewShock, getResources().getString(R.string.home_message_shock));
        thresholdMessageTv.setText(mContext.getResources().getString(R.string.home_setting_device, "5").replace("%", ""));
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.page_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_back:
                finish();
                break;
        }
    }


    private void initSwitch(SwitchView sv, final String key) {
        String name = Utils.readSharedPreferences(getResources().getString(R.string.home_login_name));
        final StringBuffer nameSb = new StringBuffer();
        nameSb.append(name).append(key);
        boolean isPushAble = Utils.readBoolean(nameSb.toString(), false);
        sv.setOpened(isPushAble);
        sv.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                view.toggleSwitch(true); // or false
                Utils.write(nameSb.toString(), true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.toggleSwitch(false); // or true
                Utils.write(nameSb.toString(), false);
            }
        });
    }

}
