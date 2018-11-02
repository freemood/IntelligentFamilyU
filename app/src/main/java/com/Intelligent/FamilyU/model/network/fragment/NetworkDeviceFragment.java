package com.Intelligent.FamilyU.model.network.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.GridSpacingItemDecoration;
import com.Intelligent.FamilyU.adapter.MyGridLayoutManager;
import com.Intelligent.FamilyU.adapter.NetworkDevicePageRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.model.home.activity.DeviceAddActivity;
import com.Intelligent.FamilyU.model.home.fragment.HomeFurnishingFragment;
import com.Intelligent.FamilyU.model.network.activity.NetWorkDetailActivity;
import com.Intelligent.FamilyU.model.network.bean.DeviceEntity;
import com.Intelligent.FamilyU.model.network.bean.NetWorkEventBus;
import com.Intelligent.FamilyU.model.network.bean.NetWorkListBean;
import com.Intelligent.FamilyU.model.network.iface.INetWorkListView;
import com.Intelligent.FamilyU.model.network.presenter.NetWorkListPresenter;
import com.Intelligent.FamilyU.model.plugin.iface.ICallserialNoListener;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 网关设备
 */
public class NetworkDeviceFragment extends BaseFragment implements INetWorkListView, ICallserialNoListener {
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.add_saomiao)
    RelativeLayout saomiaoRl;
    //private RLoadingDialog mLoadingDialog;
    private NetworkDevicePageRecyclerViewAdapter adapter;
    private List<HashMap<String, Object>> mlist = new ArrayList<HashMap<String, Object>>();
    private LinearLayoutManager mLayoutManager;
    private NetWorkListPresenter mNetWorkListPresenter = new NetWorkListPresenter(this, this);
    private String mSerialNo;
    private NetworkDevicePageRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new NetworkDevicePageRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
            if (null == mlist || mlist.size() == 0) {
                return;
            }
            Intent intent = new Intent(mContext, NetWorkDetailActivity.class);
            DeviceEntity mDeviceEntity = (DeviceEntity) mlist.get(postion).get("network");
            NetWorkEventBus mGatewayEventBus = new NetWorkEventBus();
            mGatewayEventBus.setName((String) mlist.get(postion).get("name"));
            mGatewayEventBus.setStatus(String.valueOf(mDeviceEntity.getStatus()));
            mGatewayEventBus.setBasename(mDeviceEntity.getSupplierName());
            mGatewayEventBus.setBasedevicename(mDeviceEntity.getTypeName());
            mGatewayEventBus.setBasemac(mDeviceEntity.getMac());
            mGatewayEventBus.setBasesn(mDeviceEntity.getSn());
            mGatewayEventBus.setWifiname(mDeviceEntity.getTypeCode().getText());
            mGatewayEventBus.setWifipassword(mDeviceEntity.getPassword24());
            mGatewayEventBus.setNetworkingSn(mDeviceEntity.getSn());
            EventBus.getDefault().postSticky(mGatewayEventBus);

            startActivity(intent);
        }

        @Override
        public void onItemLongClick(int postion) {

        }
    };

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_more_list, null);
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        //mLoadingDialog = new RLoadingDialog(mContext, true);
        initAdapter();
        callbackSerialNo();
//        initQuery();
    }

    private void initQuery() {
        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
        mSerialNo = Utils.readSharedPreferences(sb.toString());
        if (TextUtils.isEmpty(mSerialNo)) {
            adapter.clear();
            return;
        }
        mNetWorkListPresenter.netWorkListQuery(mSerialNo);
    }

    @Override
    protected void initData() {
        initSize();
    }

    private void initAdapter() {
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new MyGridLayoutManager(mContext, 3);
        //mLayoutManager.setScrollEnabled(false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(mContext, 3, 20));
        adapter = new NetworkDevicePageRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
        mRecyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.add_saomiao})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_saomiao:
                startActivity(new Intent(mContext, DeviceAddActivity.class));
                break;
        }
    }

    @Override
    public void showLoading() {
        //mLoadingDialog.show();
    }

    @Override
    public void closeLoading() {
        //mLoadingDialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(mContext, msg);
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
            map.put("name", list.get(i).getTypeName());
            map.put("status", list.get(i).getStatus());
            map.put("network", list.get(i));
            adapter.addData(map, mlist.size() + 1 + i);
        }
        initSize();
    }

    private void initSize() {
        if (mlist.size() == 0) {
            saomiaoRl.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            saomiaoRl.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        initQuery();
    }

    @Override
    public void getCallSerualNo(String serialNo) {
        if (TextUtils.isEmpty(serialNo) || mSerialNo.equals(serialNo)) {
            return;
        }
        mSerialNo = serialNo;
        mNetWorkListPresenter.netWorkListQuery(mSerialNo);
    }

    //监听网络序列号
    private void callbackSerialNo() {
        Fragment fragment = getParentFragment();
        if (fragment instanceof HomeFurnishingFragment) {
            ((HomeFurnishingFragment) (fragment)).setINetWorkserialNoListener(this);
        }
    }

}

