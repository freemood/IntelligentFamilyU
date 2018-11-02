package com.Intelligent.FamilyU.model.scene.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.ScenceSocketListRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.model.scene.entity.DeviceOperation;
import com.Intelligent.FamilyU.model.scene.entity.DeviceOperationListBean;
import com.Intelligent.FamilyU.model.scene.entity.ScenceScoketEventBus;
import com.Intelligent.FamilyU.model.scene.entity.SocketOpratingBean;
import com.Intelligent.FamilyU.model.scene.iface.IScenceOperatingView;
import com.Intelligent.FamilyU.model.scene.iface.IScenceOperationView;
import com.Intelligent.FamilyU.model.scene.presenter.HomeOprationListPresenter;
import com.Intelligent.FamilyU.model.scene.presenter.SceneSocketOperatingPresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.view.SwipeItemLayout;
import com.Intelligent.FamilyU.view.SwitchView;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ScenceSocketActivity extends BaseFragmentActivity implements IScenceOperationView, IScenceOperatingView {
    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRlTv;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;
    @BindView(R.id.switch_sclete)
    SwitchView mSwitchView;
    @BindView(R.id.my_power)
    TextView myPowerTv;

    private DeviceOperation mDeviceOperationPwor = null;
    private DeviceOperation mDeviceOperationRestar = null;
    private ScenceScoketEventBus mScenceScoketEventBus;
    private RLoadingDialog mLoadingDialog;
    private ScenceSocketListRecyclerViewAdapter adapter;
    private List<HashMap<String, Object>> mlist = new ArrayList<HashMap<String, Object>>();
    private LinearLayoutManager mLayoutManager;
    private HomeOprationListPresenter mHomeOprationListPresenter = new HomeOprationListPresenter(this, this);
    private SceneSocketOperatingPresenter mSceneSocketOperatingPresenter = new SceneSocketOperatingPresenter(this, this);
    private ScenceSocketListRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new ScenceSocketListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
        }

        @Override
        public void onItemLongClick(int postion) {

        }

        @Override
        public void onItemDeleteClick(int position) {
            if (null == mlist || 0 == mlist.size()) {
                return;
            }
            adapter.removeItem(position);

        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_scence_socket;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    public void onFamilyEvent(Object object) {
        mScenceScoketEventBus = (ScenceScoketEventBus) object;

        initSwitch(mSwitchView);
        super.onFamilyEvent(object);
    }

    @Override
    protected void initView() {
        registeredEventBus();
        mLoadingDialog = new RLoadingDialog(mContext, true);
        titleTv.setText(R.string.home_scence_socket);
        pageRightRlTv.setVisibility(View.VISIBLE);
        pageRightTv.setText(R.string.home_scence_socket_detail);
        initAdapter();

    }

    @Override
    protected void initData() {
        mHomeOprationListPresenter.oprationListQuery(mScenceScoketEventBus.getModelId());
    }


    private void initAdapter() {
        String[] nameStrings = new String[]{"14:00-15:00", "01:00-05:00"};
        String[] messageStrings = new String[]{"2018-09-18~2018-09-28", "2018-10-01~2018-10-28"};
        String[] status = new String[]{"1", "0"};
        int length = nameStrings.length;
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < length; i++) {
            map = new HashMap<String, Object>();
            map.put("title", nameStrings[i]);
            map.put("message", messageStrings[i]);
            map.put("status", status[i]);
            mlist.add(map);
        }
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        adapter = new ScenceSocketListRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(Color.RED);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.page_back, R.id.restart_ll, R.id.page_right_rl, R.id.my_add})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.restart_ll:
                try {
                    JSONArray ja = new JSONArray();
                    JSONObject jo = new JSONObject();
                    jo.put("paramCode", mScenceScoketEventBus.getParamCode());
                    jo.put("paramValue", mScenceScoketEventBus.getParamValue());
                    ja.put(jo);
                    mSceneSocketOperatingPresenter.socketOperating(mScenceScoketEventBus.getBasesn(), mScenceScoketEventBus.getBasemac(), ja.toString());
                } catch (Exception e) {
                }

                break;
            case R.id.page_back:
                finish();
                break;
            case R.id.my_add:
                break;
            case R.id.page_right_rl:
                unregisteredEventBus();
                EventBus.getDefault().postSticky(mScenceScoketEventBus);
                startActivity(new Intent(mContext, ScenceSocketDetailActivity.class));
                break;
        }
    }

    private void initSwitch(SwitchView sv) {
//        String name = Utils.readSharedPreferences(getResources().getString(R.string.home_login_name));
//        final StringBuffer nameSb = new StringBuffer();
//        nameSb.append(name).append(key);
//        boolean isPushAble = Utils.readBoolean(nameSb.toString(), false);
        sv.setOpened("1".equals(mScenceScoketEventBus.getStatus()));
        sv.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                view.toggleSwitch(true); // or false
                try {
                    List<DeviceOperation.OperationValueList> list = mDeviceOperationPwor.getOperationValueList();
                    int size = list.size();
                    String paramValue = "";
                    for (int i = 0; i < size; i++) {
                        String value = list.get(i).getCode();
                        if(value.contains("open")){
                            paramValue = list.get(i).getValue();
                        }
                    }
                    JSONArray ja = new JSONArray();
                    JSONObject jo = new JSONObject();
                    jo.put("paramCode", mDeviceOperationPwor.getCode());
                    jo.put("paramValue", paramValue);
                    ja.put(jo);
                    mSceneSocketOperatingPresenter.socketOperating(mScenceScoketEventBus.getBasesn(), mScenceScoketEventBus.getBasemac(), ja.toString());
                } catch (Exception e) {
                }
//                Utils.write(nameSb.toString(), true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.toggleSwitch(false); // or true
                try {
                    List<DeviceOperation.OperationValueList> list = mDeviceOperationPwor.getOperationValueList();
                    int size = list.size();
                    String paramValue = "";
                    for (int i = 0; i < size; i++) {
                        String value = list.get(i).getCode();
                        if(value.contains("close")){
                            paramValue = list.get(i).getValue();
                        }
                    }
                    JSONArray ja = new JSONArray();
                    JSONObject jo = new JSONObject();
                    jo.put("paramCode", mDeviceOperationPwor.getCode());
                    jo.put("paramValue", paramValue);
                    ja.put(jo);
                    mSceneSocketOperatingPresenter.socketOperating(mScenceScoketEventBus.getBasesn(), mScenceScoketEventBus.getBasemac(), ja.toString());
                } catch (Exception e) {
                }
//                Utils.write(nameSb.toString(), false);
            }
        });
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
            if (name.contains("电源")) {
                mDeviceOperationPwor = mDeviceOperation;
                myPowerTv.setText(name);
            } else if (name.contains("重启")) {
                mDeviceOperationRestar = mDeviceOperation;
                mScenceScoketEventBus.setParamCode(mDeviceOperation.getCode());
                List<DeviceOperation.OperationValueList> operationValueList = mDeviceOperation.getOperationValueList();
                int opSize = operationValueList.size();
                for (int j = 0; j < opSize; j++) {
                    DeviceOperation.OperationValueList ls = operationValueList.get(j);
                    String opname = ls.getName();
                    if (opname.contains("重启")) {
                        mScenceScoketEventBus.setParamValue(ls.getValue());
                    }
                }
            }
        }
    }

    @Override
    public void showResult(SocketOpratingBean bean) {
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
