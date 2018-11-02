package com.Intelligent.FamilyU.model.scene.fragment;

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
import com.Intelligent.FamilyU.adapter.HomeDevicePageRecyclerViewAdapter;
import com.Intelligent.FamilyU.adapter.MyGridLayoutManager;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.model.home.activity.DeviceAddActivity;
import com.Intelligent.FamilyU.model.home.fragment.HomeFurnishingFragment;
import com.Intelligent.FamilyU.model.plugin.iface.ICallserialNoListener;
import com.Intelligent.FamilyU.model.scene.activity.ScenceCurtainMotorActivity;
import com.Intelligent.FamilyU.model.scene.activity.ScenceInfraredDetectorActivity;
import com.Intelligent.FamilyU.model.scene.activity.ScenceMetalSwitchActivity;
import com.Intelligent.FamilyU.model.scene.activity.ScenceMiniGatwayActivity;
import com.Intelligent.FamilyU.model.scene.activity.ScencePasswordLockActivity;
import com.Intelligent.FamilyU.model.scene.activity.ScenceSocketActivity;
import com.Intelligent.FamilyU.model.scene.activity.ScenceWindowActivity;
import com.Intelligent.FamilyU.model.scene.entity.DeviceEntity;
import com.Intelligent.FamilyU.model.scene.entity.HomeDeviceListBean;
import com.Intelligent.FamilyU.model.scene.entity.ScenceScoketEventBus;
import com.Intelligent.FamilyU.model.scene.iface.IHomeScenceView;
import com.Intelligent.FamilyU.model.scene.presenter.HomeDeviceListPresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 家居设备
 */
public class HomeScenceDeviceFragment extends BaseFragment implements IHomeScenceView, ICallserialNoListener {
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.add_saomiao)
    RelativeLayout saomiaoRl;
    private RLoadingDialog mLoadingDialog;
    private HomeDevicePageRecyclerViewAdapter adapter;
    private List<HashMap<String, Object>> mlist = new ArrayList<HashMap<String, Object>>();
    private LinearLayoutManager mLayoutManager;
    private HomeDeviceListPresenter mHomeDeviceListPresenter = new HomeDeviceListPresenter(this, this);
    private String mSerialNo;

    private HomeDevicePageRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new HomeDevicePageRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
            if (null == mlist || mlist.size() == 0) {
                return;
            }
            DeviceEntity mDeviceEntity = (DeviceEntity) mlist.get(postion).get("homeDevice");
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
        mLoadingDialog = new RLoadingDialog(mContext, true);
        initAdapter();
        callbackSerialNo();
    }

    @Override
    protected void initData() {
        initSize();
    }

    private void initQuery() {
        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
        mSerialNo = Utils.readSharedPreferences(sb.toString());
        if (TextUtils.isEmpty(mSerialNo)) {
            adapter.clear();
            return;
        }
        mHomeDeviceListPresenter.homeDeviceListQuery(mSerialNo);
    }

    private void initAdapter() {
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new MyGridLayoutManager(mContext, 3);
        //mLayoutManager.setScrollEnabled(false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(mContext, 3, 20));
        adapter = new HomeDevicePageRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showResult(HomeDeviceListBean bean) {
        if (bean == null) {
            return;
        }
        adapter.clear();
        List<DeviceEntity> list = bean.getList();
        int size = list.size();
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < size; i++) {
            map = new HashMap<String, Object>();
            DeviceEntity mDeviceEntity = list.get(i);
            if (TextUtils.isEmpty(mDeviceEntity.getName())) {
                mDeviceEntity.setName("智能插座" + i);
            }
            if (TextUtils.isEmpty(mDeviceEntity.getStatus())) {
                mDeviceEntity.setStatus("1");
            }
            map.put("homeDevice", mDeviceEntity);
            adapter.addData(map, mlist.size());
        }
        initSize();

//        map = new HashMap<String, Object>();
//        DeviceEntity mDeviceEntity =  new DeviceEntity();
//        mDeviceEntity.setName("MINI网关");
//        map.put("homeDevice",mDeviceEntity);
//        adapter.addData(map, mlist.size());
//
//        map = new HashMap<String, Object>();
//        mDeviceEntity =  new DeviceEntity();
//        mDeviceEntity.setName("直流窗帘电机");
//        map.put("homeDevice",mDeviceEntity);
//        adapter.addData(map, mlist.size());
//
//        map = new HashMap<String, Object>();
//        mDeviceEntity =  new DeviceEntity();
//        mDeviceEntity.setName("红外入侵探测器");
//        map.put("homeDevice",mDeviceEntity);
//        adapter.addData(map, mlist.size());
//
//        map = new HashMap<String, Object>();
//        mDeviceEntity =  new DeviceEntity();
//        mDeviceEntity.setName("智尚金属开关");
//        map.put("homeDevice",mDeviceEntity);
//        adapter.addData(map, mlist.size());
//
//        map = new HashMap<String, Object>();
//        mDeviceEntity =  new DeviceEntity();
//        mDeviceEntity.setName("门窗磁探测器");
//        map.put("homeDevice",mDeviceEntity);
//        adapter.addData(map, mlist.size());
//
//        map = new HashMap<String, Object>();
//        mDeviceEntity =  new DeviceEntity();
//        mDeviceEntity.setName("密码指纹锁");
//        map.put("homeDevice",mDeviceEntity);
//        adapter.addData(map, mlist.size());


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

    @OnClick({R.id.add_saomiao})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_saomiao:
                startActivity(new Intent(mContext, DeviceAddActivity.class));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initQuery();
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

    @Override
    public void getCallSerualNo(String serialNo) {
        if (TextUtils.isEmpty(serialNo) || mSerialNo.equals(serialNo)) {
            return;
        }
        mSerialNo = serialNo;
        mHomeDeviceListPresenter.homeDeviceListQuery(mSerialNo);
    }

    //监听网络序列号
    private void callbackSerialNo() {
        Fragment fragment = getParentFragment();
        if (fragment instanceof HomeFurnishingFragment) {
            ((HomeFurnishingFragment) (fragment)).setHomeDeviceSerialNoListener(this);
        }
    }
}

