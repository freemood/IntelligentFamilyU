package com.Intelligent.FamilyU.model.scene.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.ScencedefaultAddRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.model.scene.entity.DeviceEntity;
import com.Intelligent.FamilyU.model.scene.entity.DeviceOperation;
import com.Intelligent.FamilyU.model.scene.entity.DeviceOperationListBean;
import com.Intelligent.FamilyU.model.scene.entity.DeviceScenceItem;
import com.Intelligent.FamilyU.model.scene.entity.HomeDeviceListBean;
import com.Intelligent.FamilyU.model.scene.iface.IHomeScenceView;
import com.Intelligent.FamilyU.model.scene.iface.IScenceOperationView;
import com.Intelligent.FamilyU.model.scene.presenter.HomeDeviceListPresenter;
import com.Intelligent.FamilyU.model.scene.presenter.HomeOprationListPresenter;
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
 * 设备型号
 */
public class ScenceDefaultManualListActivity extends BaseFragmentActivity implements IHomeScenceView,IScenceOperationView {
    @BindView(R.id.page_title)
    TextView titleTv;

    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    private RLoadingDialog mLoadingDialog;
    private ScencedefaultAddRecyclerViewAdapter adapter;
    private List<HashMap<String, Object>> mlist = new ArrayList<HashMap<String, Object>>();
    private LinearLayoutManager mLayoutManager;
    private HomeDeviceListPresenter mHomeDeviceListPresenter = new HomeDeviceListPresenter(this, this);
    private HomeOprationListPresenter mHomeOprationListPresenter = new HomeOprationListPresenter(this, this);
    private String mSerialNo;
    private DeviceScenceItem deviceScenceItem;
    private ScencedefaultAddRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new ScencedefaultAddRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
            if (null == mlist || mlist.size() == 0) {
                return;
            }
            DeviceEntity mDeviceEntity = (DeviceEntity) mlist.get(postion).get("homeDevice");
            deviceScenceItem = new DeviceScenceItem();
            deviceScenceItem.setChildDeviceMac(mDeviceEntity.getMac());
            deviceScenceItem.setChildDeviceMac(mDeviceEntity.getMac());
            deviceScenceItem.setDeviceType(mDeviceEntity.getId());
            deviceScenceItem.setName(mDeviceEntity.getName());
            mHomeOprationListPresenter.oprationListQuery(mDeviceEntity.getModelId());
        }

        @Override
        public void onItemLongClick(int postion) {

        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_defult_list;
    }

    @Override
    protected void initBundleData() {
    }


    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        titleTv.setText(R.string.home_scence_add_dence);
        initAdapter();
    }

    @Override
    protected void initData() {

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
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        adapter = new ScencedefaultAddRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
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

    }

    @Override
    public void showResult(DeviceOperationListBean bean) {
        if (null == bean) {
            return;
        }
        List<DeviceOperation> list = bean.getList();

        int size = list.size();
        for (int i = 0; i < size; i++) {
            DeviceOperation mDeviceOperation = list.get(i);
            String name = mDeviceOperation.getName();
            if (name.contains("电源")||name.contains("状态")) {
                //deviceScenceItem.setName(name);
                deviceScenceItem.setCode(mDeviceOperation.getCode());
                List<DeviceOperation.OperationValueList> operationValueList = mDeviceOperation.getOperationValueList();
                int opSize = operationValueList.size();
                for (int j = 0; j < opSize; j++) {
                    DeviceOperation.OperationValueList ls = operationValueList.get(j);
                    String opname = ls.getName();
                    deviceScenceItem.setParamIndex(name);
                    if (opname.contains("开")) {
                        deviceScenceItem.setParamOpenCode(ls.getCode());
                        deviceScenceItem.setParamOpenValue(ls.getValue());
                        deviceScenceItem.setParamOpenName(opname);
                    }else{
                        deviceScenceItem.setParamCloseCode(ls.getCode());
                        deviceScenceItem.setParamCloseValue(ls.getValue());
                        deviceScenceItem.setParamCloseName(opname);
                    }
                }
            }
//            else if (name.contains("重启")) {
//                deviceScenceItem.setParamCode(mDeviceOperation.getCode());
//                List<DeviceOperation.OperationValueList> operationValueList = mDeviceOperation.getOperationValueList();
//                int opSize = operationValueList.size();
//                for (int j = 0; j < opSize; j++) {
//                    DeviceOperation.OperationValueList ls = operationValueList.get(j);
//                    String opname = ls.getName();
//                    if (opname.contains("重启")) {
//                        mScenceScoketEventBus.setParamValue(ls.getValue());
//                    }
//                }
//            }
        }
        EventBus.getDefault().postSticky(deviceScenceItem);
        finish();

    }


    @OnClick({R.id.page_back})
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.download_file_task_rl:
//                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragmentList.get(0));
//                viewPager.setCurrentItem(0);
//                titleTv.setText(R.string.home_user_list);
//                break;
            case R.id.page_back:
                finish();
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

}
