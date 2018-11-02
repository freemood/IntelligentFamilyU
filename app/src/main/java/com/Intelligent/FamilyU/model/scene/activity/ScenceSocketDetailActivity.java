package com.Intelligent.FamilyU.model.scene.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.model.main.event.MessageEvent;
import com.Intelligent.FamilyU.model.network.activity.NetWorkDetailBaseSettingActivity;
import com.Intelligent.FamilyU.model.network.activity.NetWorkDetailNameActivity;
import com.Intelligent.FamilyU.model.network.activity.NetWorkDetailWifiInfoActivity;
import com.Intelligent.FamilyU.model.scene.entity.ScenceScoketEventBus;
import com.Intelligent.FamilyU.model.scene.entity.ScoketRestartBean;
import com.Intelligent.FamilyU.model.scene.iface.ISocketUntieView;
import com.Intelligent.FamilyU.model.scene.presenter.SceneSocketUntiePresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
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
public class ScenceSocketDetailActivity extends BaseFragmentActivity implements ISocketUntieView {
    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;
    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRl;

    @BindView(R.id.name_tv)
    TextView networkNameTv;
    @BindView(R.id.sn_name)
    TextView snTv;
    @BindView(R.id.manufactor_name)
    TextView manufactorTv;
    @BindView(R.id.device_name)
    TextView deviceTv;
    @BindView(R.id.mac_name)
    TextView macTv;
    private RLoadingDialog mLoadingDialog;
    private SceneSocketUntiePresenter mSceneSocketUntiePresenter = new SceneSocketUntiePresenter(this, this);
    private ScenceScoketEventBus mScenceScoketEventBus;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_scence_socket_detail;
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
        titleTv.setText(R.string.home_scence_socket_detail);
        pageRightRl.setVisibility(View.VISIBLE);
        pageRightTv.setText(R.string.btn_untie);

    }

    @Override
    protected void initData() {
        networkNameTv.setText(mScenceScoketEventBus.getName());
        snTv.setText(mScenceScoketEventBus.getBasesn());
        macTv.setText(mScenceScoketEventBus.getBasemac());
        manufactorTv.setText(mScenceScoketEventBus.getBasename());
        deviceTv.setText(mScenceScoketEventBus.getBasedevicename());
    }


    @OnClick({R.id.page_back, R.id.name_rl, R.id.page_right_rl})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_back:
                finish();
                break;
            case R.id.page_right_rl:
                mSceneSocketUntiePresenter.untieSocket(mScenceScoketEventBus.getBasesn(), mScenceScoketEventBus.getBasemac());
                break;
            case R.id.name_rl:
                unregisteredEventBus();
                mScenceScoketEventBus.setName(networkNameTv.getText().toString());
                EventBus.getDefault().postSticky(mScenceScoketEventBus);
                startActivity(new Intent(mContext, ScenceScoketDetailInfoNameActivity.class));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getEvent(MessageEvent messageEvent) {
        networkNameTv.setText(messageEvent.getMessage());
        // 移除粘性事件
        EventBus.getDefault().removeStickyEvent(messageEvent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        registeredEventBus();
    }

    @Override
    public void showResult(ScoketRestartBean bean) {
        if (null == bean) {
            return;
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
