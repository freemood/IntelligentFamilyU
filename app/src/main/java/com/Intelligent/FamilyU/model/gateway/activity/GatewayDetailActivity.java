package com.Intelligent.FamilyU.model.gateway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.AssociatedEquipmentListRecyclerViewAdapter;
import com.Intelligent.FamilyU.adapter.GatewayPluginListRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.model.cloud.activity.CloudStorageActivity;
import com.Intelligent.FamilyU.model.download.activity.RemotedownloadActivity;
import com.Intelligent.FamilyU.model.gateway.entity.GatewayEventBus;
import com.Intelligent.FamilyU.model.gateway.entity.GatewayRestartBean;
import com.Intelligent.FamilyU.model.gateway.iface.IGatewayRestartView;
import com.Intelligent.FamilyU.model.gateway.presenter.GatewayRestartPresenter;
import com.Intelligent.FamilyU.model.network.activity.NetWorkDetailActivity;
import com.Intelligent.FamilyU.model.network.bean.DeviceEntity;
import com.Intelligent.FamilyU.model.network.bean.NetWorkEventBus;
import com.Intelligent.FamilyU.model.network.bean.NetWorkListBean;
import com.Intelligent.FamilyU.model.network.iface.INetWorkListView;
import com.Intelligent.FamilyU.model.network.presenter.NetWorkListPresenter;
import com.Intelligent.FamilyU.model.plugin.bean.PluginPageEntity;
import com.Intelligent.FamilyU.model.plugin.iface.IPluginListPageView;
import com.Intelligent.FamilyU.model.plugin.presenter.PluginListPagePresenter;
import com.Intelligent.FamilyU.model.scene.activity.ScenceCurtainMotorActivity;
import com.Intelligent.FamilyU.model.scene.activity.ScenceInfraredDetectorActivity;
import com.Intelligent.FamilyU.model.scene.activity.ScenceMetalSwitchActivity;
import com.Intelligent.FamilyU.model.scene.activity.ScenceMiniGatwayActivity;
import com.Intelligent.FamilyU.model.scene.activity.ScencePasswordLockActivity;
import com.Intelligent.FamilyU.model.scene.activity.ScenceSocketActivity;
import com.Intelligent.FamilyU.model.scene.activity.ScenceWindowActivity;
import com.Intelligent.FamilyU.model.scene.entity.HomeDeviceListBean;
import com.Intelligent.FamilyU.model.scene.entity.ScenceScoketEventBus;
import com.Intelligent.FamilyU.model.scene.iface.IHomeScenceView;
import com.Intelligent.FamilyU.model.scene.presenter.HomeDeviceListPresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GatewayDetailActivity extends BaseFragmentActivity implements IGatewayRestartView, INetWorkListView, IHomeScenceView, IPluginListPageView {
    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.network_state_tv)
    TextView networkStatTv;
    @BindView(R.id.iv)
    ImageView roomIv;
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.my_recycler_plugin_view)
    RecyclerView mPluginRecyclerView;

    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRlTv;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;

    private RLoadingDialog mLoadingDialog;
    private AssociatedEquipmentListRecyclerViewAdapter adapter;
    private List<HashMap<String, Object>> mlist = new ArrayList<HashMap<String, Object>>();
    private LinearLayoutManager mLayoutManager;

    private GatewayPluginListRecyclerViewAdapter adapterPlugin;
    private List<HashMap<String, Object>> mlistPlugin = new ArrayList<HashMap<String, Object>>();
    private LinearLayoutManager mLayoutManagerPlugin;
    private PluginListPagePresenter mPluginListPresenter = new PluginListPagePresenter(this, this);

    private GatewayRestartPresenter mGatewayRestartPresenter = new GatewayRestartPresenter(this, this);
    private NetWorkListPresenter mNetWorkListPresenter = new NetWorkListPresenter(this, this);
    private HomeDeviceListPresenter mHomeDeviceListPresenter = new HomeDeviceListPresenter(this, this);
    private GatewayEventBus mGatewayEventBus;

    private AssociatedEquipmentListRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new AssociatedEquipmentListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
            if (null == mlist || mlist.size() == 0) {
                return;
            }
            HashMap<String, Object> map = mlist.get(postion);
            String type = (String) map.get("type");
            if ("network".equals(type)) {
                DeviceEntity mDeviceEntity = (DeviceEntity) map.get("network");
                NetWorkEventBus mGatewayEventBus = new NetWorkEventBus();
                mGatewayEventBus.setName((String) map.get("name"));
                mGatewayEventBus.setStatus(String.valueOf(mDeviceEntity.getStatus()));
                mGatewayEventBus.setBasename(mDeviceEntity.getSupplierName());
                mGatewayEventBus.setBasedevicename(mDeviceEntity.getTypeName());
                mGatewayEventBus.setBasemac(mDeviceEntity.getMac());
                mGatewayEventBus.setBasesn(mDeviceEntity.getSn());
                mGatewayEventBus.setWifiname(mDeviceEntity.getTypeCode().getText());
                mGatewayEventBus.setWifipassword(mDeviceEntity.getPassword24());
                mGatewayEventBus.setNetworkingSn(mDeviceEntity.getSn());
                EventBus.getDefault().postSticky(mGatewayEventBus);

                startActivity(new Intent(mContext, NetWorkDetailActivity.class));
            } else {
                com.Intelligent.FamilyU.model.scene.entity.DeviceEntity mDeviceEntity = (com.Intelligent.FamilyU.model.scene.entity.DeviceEntity) map.get("device");
                String name = mDeviceEntity.getName();
                ScenceScoketEventBus mScenceScoketEventBus = new ScenceScoketEventBus();
                mScenceScoketEventBus.setSerialNo(mDeviceEntity.getGatewaySn());
                mScenceScoketEventBus.setName(name);
                mScenceScoketEventBus.setStatus(mDeviceEntity.getStatus());
                mScenceScoketEventBus.setParentId(mDeviceEntity.getParentId());
                mScenceScoketEventBus.setDeviceId(mDeviceEntity.getId());
                mScenceScoketEventBus.setChildDeviceId(mDeviceEntity.getId());
                mScenceScoketEventBus.setBasename(mDeviceEntity.getName());
                mScenceScoketEventBus.setBasedevicename(mDeviceEntity.getSupplierId());
                mScenceScoketEventBus.setBasemac(mDeviceEntity.getMac());
                mScenceScoketEventBus.setBasesn(mDeviceEntity.getGatewaySn());
                mScenceScoketEventBus.setModelId(mDeviceEntity.getModelId());
                EventBus.getDefault().postSticky(mScenceScoketEventBus);

                if (name.contains("插座")) {
                    startActivity(new Intent(mContext, ScenceSocketActivity.class));
                } else if (name.contains("网关")) {
                    startActivity(new Intent(mContext, ScenceMiniGatwayActivity.class));
                } else if (name.contains("电机")) {
                    startActivity(new Intent(mContext, ScenceCurtainMotorActivity.class));
                } else if (name.contains("红外")) {
                    startActivity(new Intent(mContext, ScenceInfraredDetectorActivity.class));
                } else if (name.contains("金属")) {
                    startActivity(new Intent(mContext, ScenceMetalSwitchActivity.class));
                } else if (name.contains("门窗磁")) {
                    startActivity(new Intent(mContext, ScenceWindowActivity.class));
                } else if (name.contains("指纹锁")) {
                    startActivity(new Intent(mContext, ScencePasswordLockActivity.class));
                }
            }
        }

        @Override
        public void onItemLongClick(int postion) {

        }
    };

    private GatewayPluginListRecyclerViewAdapter.OnItemClickListener mOnItemClickListenerPlugin = new GatewayPluginListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
            if (null == mlistPlugin || mlistPlugin.size() == 0) {
                return;
            }
            PluginPageEntity.PluginGatewayInfo mPluginPageEntity = (PluginPageEntity.PluginGatewayInfo) mlistPlugin.get(postion).get("plugin");
            String name = (String) mlistPlugin.get(postion).get("title");
            if (name.contains("远程下载")) {
                startActivity(new Intent(mContext, RemotedownloadActivity.class));
            } else if (name.contains("云存储")) {
                startActivity(new Intent(mContext, CloudStorageActivity.class));

            }

        }

        @Override
        public void onItemLongClick(int postion) {

        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_getawary_detail;
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
        initAdapter();
        initPluginAdapter();
        pageRightRlTv.setVisibility(View.VISIBLE);
        pageRightTv.setText(R.string.home_gateway_detail_title);
    }

    @Override
    protected void initData() {
        initQuery(mGatewayEventBus.getSerialNo());
        initPluginQuery(mGatewayEventBus.getPluginId());
    }

    private void initQuery(String mSerialNo) {
        mNetWorkListPresenter.netWorkListQuery(mSerialNo);
    }

    private void initPluginQuery(String mPluginId) {
        mPluginListPresenter.pluginListQuery(mPluginId);
    }

    private void initAdapter() {
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        adapter = new AssociatedEquipmentListRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
        mRecyclerView.setAdapter(adapter);
    }

    private void initPluginAdapter() {

        mPluginRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManagerPlugin = new LinearLayoutManager(mContext);
        mPluginRecyclerView.setLayoutManager(mLayoutManagerPlugin);
        // specify an adapter (see also next example)
        adapterPlugin = new GatewayPluginListRecyclerViewAdapter(mContext, mlistPlugin, mOnItemClickListenerPlugin);
        mPluginRecyclerView.setAdapter(adapterPlugin);
    }

    @OnClick({R.id.page_back, R.id.restart_ll, R.id.page_right_rl})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.restart_ll:
                mGatewayRestartPresenter.restartGateway(mGatewayEventBus.getSerialNo());
                break;
            case R.id.page_back:
                finish();
                break;
            case R.id.page_right_rl:
                unregisteredEventBus();
                EventBus.getDefault().postSticky(mGatewayEventBus);
                startActivity(new Intent(mContext, GatewayDetailInfoActivity.class));
                break;
        }
    }

    @Override
    public void showResult(NetWorkListBean bean) {
        if (null == bean) {
            return;
        }
        adapter.clear();
        List<DeviceEntity> list = bean.getList();
        int size = list.size();
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < size; i++) {
            map = new HashMap<String, Object>();
            map.put("title", list.get(i).getTypeName());
            map.put("type", "network");
            map.put("network", list.get(i));
            adapter.addData(map, mlist.size());
        }
        mHomeDeviceListPresenter.homeDeviceListQuery(mGatewayEventBus.getSerialNo());
    }

    @Override
    public void showResult(HomeDeviceListBean bean) {
        if (bean == null) {
            return;
        }
        adapter.refresh();
        List<com.Intelligent.FamilyU.model.scene.entity.DeviceEntity> list = bean.getList();
        int size = list.size();
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < size; i++) {
            map = new HashMap<String, Object>();
            com.Intelligent.FamilyU.model.scene.entity.DeviceEntity mDeviceEntity = list.get(i);
            if (TextUtils.isEmpty(mDeviceEntity.getStatus())) {
                mDeviceEntity.setStatus("1");
            }
            if (TextUtils.isEmpty(mDeviceEntity.getName())) {
                mDeviceEntity.setName("智能插座" + i);
            }
            map.put("title", mDeviceEntity.getName());
            map.put("type", "device");
            map.put("device", mDeviceEntity);
            adapter.addData(map, mlist.size());
        }

    }

    @Override
    public void showResult(List<PluginPageEntity.PluginGatewayInfo> bean) {
        if (bean == null) {
            return;
        }
        adapterPlugin.clear();
        HashMap<String, Object> map = new HashMap<String, Object>();
        int size = bean.size();
        for (int i = 0; i < size; i++) {
            PluginPageEntity.PluginGatewayInfo mDeviceEntity = bean.get(i);
            map = new HashMap<String, Object>();
            map.put("title", mDeviceEntity.getPluginGateway().getPluginName());
            map.put("status", mDeviceEntity.getPluginGateway().getStatus());
            map.put("plugin", mDeviceEntity);
            adapterPlugin.addData(map, mlistPlugin.size());
        }

    }

    @Override
    public void showResult(GatewayRestartBean bean) {
        if (null == bean) {
            return;
        }
        if (bean.isResult()) {
            showToast(getResources().getString(R.string.home_gateway_detail_restart_ok));
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
