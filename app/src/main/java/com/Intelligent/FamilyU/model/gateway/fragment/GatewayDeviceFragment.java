package com.Intelligent.FamilyU.model.gateway.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.GatewayDevicePageRecyclerViewAdapter;
import com.Intelligent.FamilyU.adapter.GridSpacingItemDecoration;
import com.Intelligent.FamilyU.adapter.MyGridLayoutManager;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.model.gateway.activity.GatewayDetailActivity;
import com.Intelligent.FamilyU.model.gateway.entity.DeviceGateWayBean;
import com.Intelligent.FamilyU.model.gateway.entity.DeviceGateWayDTO;
import com.Intelligent.FamilyU.model.gateway.entity.GatewayEventBus;
import com.Intelligent.FamilyU.model.gateway.iface.IAddGetawary;
import com.Intelligent.FamilyU.model.gateway.iface.IGetawaryBaseDetailView;
import com.Intelligent.FamilyU.model.gateway.presenter.GetawaryBaseDetailPresenter;
import com.Intelligent.FamilyU.model.home.iface.ICallCpuAndMemoryView;
import com.Intelligent.FamilyU.model.main.MainActivity;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.widget.RLoadingDialog;
import com.example.jpushdemo.ExampleUtil;
import com.example.jpushdemo.LocalBroadcastManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 网关设备
 */
public class GatewayDeviceFragment extends BaseFragment implements IGetawaryBaseDetailView, IAddGetawary {

    //private RLoadingDialog mLoadingDialog;
    private GatewayDevicePageRecyclerViewAdapter adapter;
    private List<HashMap<String, Object>> mlist = new ArrayList<HashMap<String, Object>>();
    private LinearLayoutManager mLayoutManager;
    private ICallCpuAndMemoryView mICallGatewayCpuAndMemory = null;
    private GetawaryBaseDetailPresenter mGetawaryDetailPresenter = new GetawaryBaseDetailPresenter(this, this);
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    private String mSerialNo;
    private String mPluginId;
    private GatewayDevicePageRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new GatewayDevicePageRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
            if (null == mlist || mlist.size() == 0) {
                return;
            }
            DeviceGateWayDTO gateway = (DeviceGateWayDTO) mlist.get(postion).get("gateway");
            String name = (String) mlist.get(postion).get("name");
            String serialNo = (String) mlist.get(postion).get("serialNo");
            String status = (String) mlist.get(postion).get("status");

            GatewayEventBus mGatewayEventBus = new GatewayEventBus();
            mGatewayEventBus.setSerialNo(serialNo);
            mGatewayEventBus.setName(name);
            mGatewayEventBus.setStatus(status);
            mGatewayEventBus.setBroadbandAccount(gateway.getBroadbandAccount());
            mGatewayEventBus.setBasename(gateway.getDeviceDTO().getDeviceSupplierDTO().getName());
            mGatewayEventBus.setBasedevicename(gateway.getDeviceDTO().getName());
            mGatewayEventBus.setBasemac(gateway.getRouterMac());
            mGatewayEventBus.setBasesn(gateway.getSerialNo());
            mGatewayEventBus.setPluginId(gateway.getId());
            EventBus.getDefault().postSticky(mGatewayEventBus);

            Intent intent = new Intent(mContext, GatewayDetailActivity.class);
            startActivity(intent);

        }

        @Override
        public void onItemLongClick(int postion) {
            if (null == mlist || mlist.size() == 0) {
                return;
            }
            DeviceGateWayDTO gateway = (DeviceGateWayDTO) mlist.get(postion).get("gateway");
            final String serialNo = (String) mlist.get(postion).get("serialNo");
            final String pluginId = gateway.getId();

            new AlertDialog.Builder(mContext).setTitle("切换网关")
                    .setMessage("选择是否切换网关")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //回调网络序列号接口
                            callBackInterface(serialNo, pluginId);
                            if (getActivity() instanceof MainActivity) {
                                ((MainActivity) getActivity()).setAlias(serialNo);
                            }
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();

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
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setIAddGetawary(this);
        }
        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
        mSerialNo = Utils.readSharedPreferences(sb.toString());

        StringBuffer pluginIdsb = new StringBuffer();
        pluginIdsb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("pluginId");
        mPluginId = Utils.readSharedPreferences(pluginIdsb.toString());
    }

    @Override
    protected void initData() {
        // mGetawaryDetailPresenter.queryGateway();
    }

    private void initAdapter() {

        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new MyGridLayoutManager(mContext, 4);
        //mLayoutManager.setScrollEnabled(false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(mContext, 4, 20));
        adapter = new GatewayDevicePageRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
        mRecyclerView.setAdapter(adapter);


    }

    @Override
    public void showLoading() {
        // mLoadingDialog.show();
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
    public void onResume() {
        super.onResume();
        adapter.clear();
        mGetawaryDetailPresenter.queryGateway();
    }


    @Override
    public void addGetawary() {
        if (null == adapter) {
            return;
        }
        mGetawaryDetailPresenter.queryGateway();
    }

    public void setICallGatewayCpuAndMemory(ICallCpuAndMemoryView iCallGatewayCpuAndMemory) {
        mICallGatewayCpuAndMemory = iCallGatewayCpuAndMemory;
    }

    @Override
    public void showResult(DeviceGateWayBean bean) {
        if (null == bean) {
            return;
        }
        if (null == bean.getList() || 0 == bean.getList().size()) {
            return;
        }
        adapter.clear();
        List<DeviceGateWayDTO> list = bean.getList();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            DeviceGateWayDTO mDeviceGateWayDTO = list.get(i);
            String name = mDeviceGateWayDTO.getName();
            String serialNo = mDeviceGateWayDTO.getSerialNo();
            String status = mDeviceGateWayDTO.getStatus().getValue();
            String pluginId = mDeviceGateWayDTO.getId();

            if (0 == i) {
                if (!TextUtils.isEmpty(mSerialNo)) {
                    callBackInterface(mSerialNo, mPluginId);
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).setAlias(mSerialNo);
                    }
                } else {
                    //回调网络序列号接口
                    callBackInterface(serialNo, pluginId);
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).setAlias(serialNo);
                    }
                }
            }
            addItem(mDeviceGateWayDTO, serialNo, name, status);
        }

    }

    private void callBackInterface(String serialNo, String pluginId) {
        StringBuffer serialNosb = new StringBuffer();
        serialNosb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
        Utils.write(serialNosb.toString(), serialNo);

        StringBuffer pluginIdsb = new StringBuffer();
        pluginIdsb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("pluginId");
        Utils.write(pluginIdsb.toString(), pluginId);
        if (null == mICallGatewayCpuAndMemory) {
            return;
        }
        mICallGatewayCpuAndMemory.callGatewaySerialNo(serialNo, pluginId);
    }

    private void addItem(DeviceGateWayDTO gateway, String serialNo, String name, String status) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("serialNo", serialNo);
        map.put("status", status);
        map.put("gateway", gateway);
        adapter.addData(map, mlist.size());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();


    }

}

