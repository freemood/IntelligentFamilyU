package com.Intelligent.FamilyU.model.scene.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.DeviceListRecyclerViewAdapter;
import com.Intelligent.FamilyU.adapter.DownloadFileStartingListRecyclerViewAdapter;
import com.Intelligent.FamilyU.adapter.ScenceDefutEditListRecyclerViewAdapter;
import com.Intelligent.FamilyU.adapter.ScenceEditListRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.model.main.event.MessageEvent;
import com.Intelligent.FamilyU.model.scene.entity.DeviceEntity;
import com.Intelligent.FamilyU.model.scene.entity.DeviceOperation;
import com.Intelligent.FamilyU.model.scene.entity.DeviceOperationListBean;
import com.Intelligent.FamilyU.model.scene.entity.DeviceScenceItem;
import com.Intelligent.FamilyU.model.scene.entity.ScenceDefultListBean;
import com.Intelligent.FamilyU.model.scene.entity.ScenceSaveBean;
import com.Intelligent.FamilyU.model.scene.entity.SceneExecCondition;
import com.Intelligent.FamilyU.model.scene.entity.SceneOperation;
import com.Intelligent.FamilyU.model.scene.entity.VolSceneEntity;
import com.Intelligent.FamilyU.model.scene.iface.IHomeCustomScenceView;
import com.Intelligent.FamilyU.model.scene.iface.IHomeDefultScenceView;
import com.Intelligent.FamilyU.model.scene.iface.IScenceOperatingView;
import com.Intelligent.FamilyU.model.scene.iface.IScenceOperationView;
import com.Intelligent.FamilyU.model.scene.iface.IScenceSaveView;
import com.Intelligent.FamilyU.model.scene.presenter.HomeOprationListPresenter;
import com.Intelligent.FamilyU.model.scene.presenter.HomeScenceCustomListPresenter;
import com.Intelligent.FamilyU.model.scene.presenter.HomeScenceDefultListPresenter;
import com.Intelligent.FamilyU.model.scene.presenter.SceneSavePresenter;
import com.Intelligent.FamilyU.model.scene.presenter.SceneSocketOperatingPresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.view.IBaseView;
import com.Intelligent.FamilyU.view.SwipeItemLayout;
import com.Intelligent.FamilyU.view.SwitchView;
import com.Intelligent.FamilyU.widget.RLoadingDialog;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.Intelligent.FamilyU.base.BaseApplication.getContext;

/**
 */
public class ScenceEditDelutListActivity extends BaseFragmentActivity implements IScenceSaveView, IHomeCustomScenceView {
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;
    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRl;
    @BindView(R.id.my_name)
    TextView nameTv;
    @BindView(R.id.switch_sclete)
    SwitchView mSwitchView;
    private RLoadingDialog mLoadingDialog;
    private ScenceDefutEditListRecyclerViewAdapter adapter;
    private List<SceneOperation> mlist = new ArrayList<SceneOperation>();
    private List<SceneOperation> operationsList = new ArrayList<SceneOperation>();
    private LinearLayoutManager mLayoutManager;
    private String status = "1";
    private VolSceneEntity volSceneEntity;
    private SceneSavePresenter mSceneSavePresenter = new SceneSavePresenter(this, this);
    //private HomeScenceDefultListPresenter mHomeScenceDefultListPresenter = new HomeScenceDefultListPresenter(this, this);
    private HomeScenceCustomListPresenter mHomeScenceCustomListPresenter = new HomeScenceCustomListPresenter(this, this);
    private String mSerialNo;
    private ScenceDefutEditListRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new ScenceDefutEditListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
            //dapter.notifyDataSetChanged();
        }

        @Override
        public void onItemLongClick(int postion) {

        }

        @Override
        public void onItemDelete(int position) {
            scenceDelete(mlist.get(position));
            adapter.deleteItem(position);
        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_scence_defut_list;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        registeredEventBus();
        mLoadingDialog = new RLoadingDialog(mContext, true);
        titleTv.setText(R.string.home_scene_setting);
        pageRightTv.setBackgroundResource(R.mipmap.icon_add4);
        pageRightRl.setVisibility(View.VISIBLE);
        initAdapter();
        initSwitch(mSwitchView);
    }

    @Override
    protected void initData() {
        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
        mSerialNo = Utils.readSharedPreferences(sb.toString());
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getEvent(VolSceneEntity messageEvent) {
        nameTv.setText(messageEvent.getTitle());
        volSceneEntity = messageEvent;

        initQuery();
        // 移除粘性事件
        EventBus.getDefault().removeStickyEvent(messageEvent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getEvent(DeviceScenceItem deviceScenceItem) {
        String id = volSceneEntity.getId();
        if (TextUtils.isEmpty(id)) {
            id = String.valueOf(System.currentTimeMillis());
        }
        String sceneName = nameTv.getText().toString();

        List<SceneExecCondition> conditionsList = volSceneEntity.getConditions();
        operationsList = volSceneEntity.getOperations();
        if (null == operationsList) {
            operationsList = new ArrayList<>();
        }
        if (null == conditionsList) {
            conditionsList = new ArrayList<>();
        }
        SceneOperation sceneOperation = new SceneOperation();
        sceneOperation.setChildDeviceMac(deviceScenceItem.getChildDeviceMac());
        sceneOperation.setDeviceMac(deviceScenceItem.getDeviceMac());
        sceneOperation.setDeviceType(deviceScenceItem.getDeviceType());
        sceneOperation.setParamCode(deviceScenceItem.getParamOpenCode());
        sceneOperation.setParamIndex(deviceScenceItem.getName());
        sceneOperation.setParamValue(deviceScenceItem.getParamOpenValue());
        operationsList.add(sceneOperation);

//        sceneOperation = new SceneOperation();
//        sceneOperation.setChildDeviceMac(deviceScenceItem.getChildDeviceMac());
//        sceneOperation.setDeviceMac(deviceScenceItem.getDeviceMac());
//        sceneOperation.setDeviceType(deviceScenceItem.getDeviceType());
//        sceneOperation.setParamCode(deviceScenceItem.getParamCloseCode());
//        sceneOperation.setParamIndex(deviceScenceItem.getName());
//        sceneOperation.setParamValue(deviceScenceItem.getParamCloseValue());
//        operationsList.add(sceneOperation);

        mSceneSavePresenter.scenceSave(mSerialNo, id, sceneName, operationsList, conditionsList, "DEFAULT");
        // 移除粘性事件
        EventBus.getDefault().removeStickyEvent(deviceScenceItem);
    }

    private void scenceDelete(SceneOperation sceneOperation) {

        String id = volSceneEntity.getId();
        if (TextUtils.isEmpty(id)) {
            id = String.valueOf(System.currentTimeMillis());
        }
        String sceneName = nameTv.getText().toString();

        List<SceneExecCondition> conditionsList = volSceneEntity.getConditions();
        operationsList = volSceneEntity.getOperations();
        if (null == operationsList) {
            operationsList = new ArrayList<>();
        }
        if (null == conditionsList) {
            conditionsList = new ArrayList<>();
        }
        int size = operationsList.size();
        List<SceneOperation> removeList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            SceneOperation so = operationsList.get(i);
            if (so.getChildDeviceMac().equals(sceneOperation.getChildDeviceMac())) {
                removeList.add(so);
                continue;
            }
        }
        operationsList.removeAll(removeList);
        mSceneSavePresenter.scenceSave(mSerialNo, id, sceneName, operationsList, conditionsList, "DEFAULT");
    }

    private void initAdapter() {
//        String[] nameStrings = new String[]{"智能插座"};
//        String[] modelStrings = new String[]{"警告模式"};
//        int length = nameStrings.length;
//        for (int i = 0; i < length; i++) {
//            SceneOperation map = new SceneOperation();
//            map.put("title", nameStrings[i]);
//            map.put("message", modelStrings[i]);
//            mlist.add(map);
//        }
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        adapter = new ScenceDefutEditListRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
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

    private void initSwitch(SwitchView sv) {
//        String name = Utils.readSharedPreferences(getResources().getString(R.string.home_login_name));
//        final StringBuffer nameSb = new StringBuffer();
//        nameSb.append(name).append(key);
//        boolean isPushAble = Utils.readBoolean(nameSb.toString(), false);
        sv.setOpened("1".equals(status));
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
    public void showResult(ScenceSaveBean bean) {
        if (null == bean) {
            return;
        }
        if (!TextUtils.isEmpty(bean.getResult())) {
            showToast(getResources().getString(R.string.home_scence_save));
            initQuery();
        }
    }

    @OnClick({R.id.page_back, R.id.page_right_rl})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_back:
                finish();
                break;
            case R.id.page_right_rl:
                startActivity(new Intent(mContext, ScenceDefaultManualListActivity.class));
                break;
        }
    }

    private void initQuery() {
        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
        mSerialNo = Utils.readSharedPreferences(sb.toString());
        if (TextUtils.isEmpty(mSerialNo)) {
            return;
        }
        //mHomeScenceDefultListPresenter.scenceDefultListQuery(mSerialNo);
        mHomeScenceCustomListPresenter.scenceDefultListQuery(mSerialNo);
    }

    @Override
    public void showResult(ScenceDefultListBean bean) {
        if (null == bean) {
            return;
        }
        String title = nameTv.getText().toString();
        if (TextUtils.isEmpty(title)) {
            return;
        }
        adapter.clear();
        List<VolSceneEntity> list = bean.getList();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            VolSceneEntity volSceneEntity = list.get(i);
            String sceneName = volSceneEntity.getSceneName();
            if (sceneName.contains(title) && mSerialNo.equals(volSceneEntity.getGatewaySn())) {
                List<SceneOperation> operations = volSceneEntity.getOperations();
                int sizes = operations.size();
                if (sizes == 0) {
                    continue;
                }
                for (int j = 0; j < sizes; j++) {
                    String name = operations.get(j).getParamIndex();
                    if (TextUtils.isEmpty(name)) {
                        continue;
                    }
                    adapter.addData(operations.get(j), mlist.size());
                }
            }
        }

//        downingTv.setText(mContext.getResources().getString(R.string.home_scence_defult_number_decen, mlistStart.size()));

    }


    @Override
    protected void onResume() {
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
