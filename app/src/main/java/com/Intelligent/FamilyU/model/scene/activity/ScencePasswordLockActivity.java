package com.Intelligent.FamilyU.model.scene.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.model.scene.entity.DeviceEntity;
import com.Intelligent.FamilyU.model.scene.entity.DeviceOperation;
import com.Intelligent.FamilyU.model.scene.entity.DeviceOperationListBean;
import com.Intelligent.FamilyU.model.scene.entity.ScenceScoketEventBus;
import com.Intelligent.FamilyU.model.scene.entity.SocketOpratingBean;
import com.Intelligent.FamilyU.model.scene.iface.IScenceOperatingView;
import com.Intelligent.FamilyU.model.scene.iface.IScenceOperationView;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.view.IBaseView;
import com.Intelligent.FamilyU.view.SwitchView;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ScencePasswordLockActivity extends BaseFragmentActivity implements IBaseView {
    @BindView(R.id.page_title)
    TextView titleTv;

    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRlTv;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;
    private ScenceScoketEventBus mScenceScoketEventBus;
    private RLoadingDialog mLoadingDialog;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_scence_password_lock;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    public void onFamilyEvent(Object object) {
        mScenceScoketEventBus = (ScenceScoketEventBus) object;

        super.onFamilyEvent(object);
    }

    @Override
    protected void initView() {
        registeredEventBus();
        mLoadingDialog = new RLoadingDialog(mContext, true);
        titleTv.setText(R.string.home_scence_window_lock);
        pageRightRlTv.setVisibility(View.VISIBLE);
        pageRightTv.setText(R.string.home_scence_socket_detail);
    }

    @Override
    protected void initData() {
    }


    @OnClick({R.id.page_back, R.id.page_right_rl,R.id.my_power_rl})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_back:
                finish();
                break;
            case R.id.my_power_rl:
                startActivity(new Intent(mContext, ScenceRemotLockActivity.class));
                break;
            case R.id.page_right_rl:
                unregisteredEventBus();
                EventBus.getDefault().postSticky(mScenceScoketEventBus);
                startActivity(new Intent(mContext, ScenceDeviceDetailActivity.class));
                break;
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

