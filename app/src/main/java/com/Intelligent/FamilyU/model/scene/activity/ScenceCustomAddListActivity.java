package com.Intelligent.FamilyU.model.scene.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.ScenceEditDownListRecyclerViewAdapter;
import com.Intelligent.FamilyU.adapter.ScenceEditListRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.model.main.event.MessageEvent;
import com.Intelligent.FamilyU.model.scene.entity.DeviceScenceItem;
import com.Intelligent.FamilyU.model.scene.entity.MessageCount;
import com.Intelligent.FamilyU.model.scene.entity.ScenceSaveBean;
import com.Intelligent.FamilyU.model.scene.entity.SceneExecCondition;
import com.Intelligent.FamilyU.model.scene.entity.SceneOperation;
import com.Intelligent.FamilyU.model.scene.iface.IScenceSaveView;
import com.Intelligent.FamilyU.model.scene.presenter.SceneSavePresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.view.SwipeItemLayout;
import com.Intelligent.FamilyU.widget.RLoadingDialog;
import com.bigkoo.pickerview.TimePickerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设备型号
 */
public class ScenceCustomAddListActivity extends BaseFragmentActivity implements IScenceSaveView {
    @BindView(R.id.my_recycler_do_view)
    RecyclerView mRecyclerDoView;
    @BindView(R.id.my_recycler_down_view)
    RecyclerView mRecyclerDownView;

    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;
    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRl;

    @BindView(R.id.time)
    TextView timeTv;
    @BindView(R.id.add_up)
    RelativeLayout addUpRl;
    @BindView(R.id.add_down)
    RelativeLayout addDownRl;
    private String clikeType = "up";
    private String mSerialNo;
    private RLoadingDialog mLoadingDialog;
    private ScenceEditListRecyclerViewAdapter adapter;
    private List<HashMap<String, Object>> mlist = new ArrayList<HashMap<String, Object>>();
    private LinearLayoutManager mLayoutManager;
    private DeviceScenceItem deviceScenceItem;
    List<SceneExecCondition> conditionsList = new ArrayList<>();
    List<SceneOperation> operationsList = new ArrayList<>();
    private ScenceEditDownListRecyclerViewAdapter adapterDown;
    private List<HashMap<String, Object>> mlistDown = new ArrayList<HashMap<String, Object>>();
    private LinearLayoutManager mLayoutManagerDown;
    private SceneSavePresenter mSceneSavePresenter = new SceneSavePresenter(this, this);
    private int listCount = 0;
    private ScenceEditListRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new ScenceEditListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
            //dapter.notifyDataSetChanged();
        }

        @Override
        public void onItemLongClick(int postion) {

        }
    };

    private ScenceEditDownListRecyclerViewAdapter.OnItemClickListener mOnItemClickListenerDown = new ScenceEditDownListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
        }

        @Override
        public void onItemLongClick(int postion) {

        }

        @Override
        public void onItemDelete(int position) {
            operationsList.remove(position);
            adapterDown.deleteItem(position);
        }

    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_scence_list;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        registeredEventBus();
        mLoadingDialog = new RLoadingDialog(mContext, true);
        titleTv.setText(getResources().getString(R.string.home_scence_custom));
        pageRightTv.setText(R.string.btn_save);
        pageRightRl.setVisibility(View.VISIBLE);
        initDownAdapter();
        initUpAdapter();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getEvent(MessageCount messageEvent) {
        listCount = messageEvent.getCount();
        // 移除粘性事件
        EventBus.getDefault().removeStickyEvent(messageEvent);
    }

    @Override
    protected void initData() {
        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
        mSerialNo = Utils.readSharedPreferences(sb.toString());
    }

    private void initDownAdapter() {
//        String[] nameStrings = new String[]{"智能插座1", "智能插座2"};
//        String[] modelStrings = new String[]{"1", "0"};
//        int length = nameStrings.length;
//        for (int i = 0; i < length; i++) {
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("title", nameStrings[i]);
//            map.put("status", modelStrings[i]);
//            mlist.add(map);
//        }
        mRecyclerDoView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerDoView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        adapter = new ScenceEditListRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
        mRecyclerDoView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(mContext));
        mRecyclerDoView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));

        mRecyclerDoView.setAdapter(adapter);
    }

    private void initUpAdapter() {
//        String[] nameStrings = new String[]{"智能插座1", "智能插座2"};
//        String[] modelStrings = new String[]{"1", "0"};
//        int length = nameStrings.length;
//        for (int i = 0; i < length; i++) {
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("title", nameStrings[i]);
//            map.put("status", modelStrings[i]);
//            mlistDown.add(map);
//        }
        mRecyclerDownView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManagerDown = new LinearLayoutManager(mContext);
        mRecyclerDownView.setLayoutManager(mLayoutManagerDown);
        // specify an adapter (see also next example)
        adapterDown = new ScenceEditDownListRecyclerViewAdapter(mContext, mlistDown, mOnItemClickListenerDown);
        mRecyclerDownView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(mContext));
        mRecyclerDownView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));

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
        mRecyclerDownView.setAdapter(adapterDown);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getEvent(DeviceScenceItem deviceScenceItem) {
        this.deviceScenceItem = deviceScenceItem;
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("title", deviceScenceItem.getName());
        map.put("status", "1");
        if ("up".equals(clikeType)) {
            SceneExecCondition sceneExecCondition = new SceneExecCondition();
            sceneExecCondition.setChildDeviceMac(deviceScenceItem.getChildDeviceMac());
            sceneExecCondition.setConditionType("device");
            sceneExecCondition.setCriterion("eq");
            sceneExecCondition.setDataType(deviceScenceItem.getDeviceType());
            sceneExecCondition.setDeviceMac(deviceScenceItem.getChildDeviceMac());
            sceneExecCondition.setParamCode(deviceScenceItem.getCode());
            sceneExecCondition.setParamIndex(deviceScenceItem.getName());
            sceneExecCondition.setParamValue("open");
            sceneExecCondition.setResource("device");
            conditionsList.add(sceneExecCondition);
            mlist.add(map);
            initDownAdapter();
        } else if ("down".equals(clikeType)) {
            SceneOperation sceneOperation = new SceneOperation();
            sceneOperation.setChildDeviceMac(deviceScenceItem.getChildDeviceMac());
            sceneOperation.setDeviceMac(deviceScenceItem.getDeviceMac());
            sceneOperation.setDeviceType(deviceScenceItem.getDeviceType());
            sceneOperation.setParamCode(deviceScenceItem.getParamOpenCode());
            sceneOperation.setParamIndex(deviceScenceItem.getName());
            sceneOperation.setParamValue(deviceScenceItem.getParamOpenValue());
            operationsList.add(sceneOperation);
            mlistDown.add(map);
            initUpAdapter();
        }
        // 移除粘性事件
        EventBus.getDefault().removeStickyEvent(deviceScenceItem);


    }

    @OnClick({R.id.page_back, R.id.page_right_rl, R.id.restart, R.id.dingshi_rl, R.id.add_up, R.id.add_down})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_back:
                finish();
                break;
            case R.id.page_right_rl:
                if (conditionsList.size() == 0) {
                    showToast("请添加一种设备");
                    return;
                }
                if (operationsList.size() == 0) {
                    showToast("请添加一种执行设备");
                    return;
                }
                mSceneSavePresenter.scenceSave(mSerialNo, String.valueOf(System.currentTimeMillis()), "自定义场景"+listCount, operationsList, conditionsList, "CUSTOM");
                break;
            case R.id.add_up:
                clikeType = "up";
                startActivity(new Intent(mContext, ScenceCustomDeviceListActivity.class));
                break;
            case R.id.add_down:
                clikeType = "down";
                startActivity(new Intent(mContext, ScenceCustomDeviceListActivity.class));
                break;
            case R.id.restart:

                break;
            case R.id.dingshi_rl:
                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        timeTv.setText(Utils.getTimeShort(date));
                    }
                }).setType(new boolean[]{false, false, false, true, true, false}) //年月日时分秒 的显示与否，不设置则默认全部显示
                        .setLabel("", "", "", "时", "分", "")//默认设置为年月日时分秒
                        .build();

                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
                break;
        }
    }

    @Override
    public void showResult(ScenceSaveBean bean) {
        if (null == bean) {
            return;
        }
        if (!TextUtils.isEmpty(bean.getResult())) {
            showToast(getResources().getString(R.string.home_scence_save));
            finish();
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
