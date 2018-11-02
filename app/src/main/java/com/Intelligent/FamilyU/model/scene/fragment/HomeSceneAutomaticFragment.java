package com.Intelligent.FamilyU.model.scene.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.SceneAutomaticListRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.model.scene.activity.ScenceCustomEditeListActivity;
import com.Intelligent.FamilyU.model.scene.entity.DeviceScenceEtidItem;
import com.Intelligent.FamilyU.model.scene.entity.MessageCount;
import com.Intelligent.FamilyU.model.scene.entity.ScenceDefultListBean;
import com.Intelligent.FamilyU.model.scene.entity.SceneExecCondition;
import com.Intelligent.FamilyU.model.scene.entity.SceneOperation;
import com.Intelligent.FamilyU.model.scene.entity.VolSceneEntity;
import com.Intelligent.FamilyU.model.scene.iface.IHomeCustomScenceView;
import com.Intelligent.FamilyU.model.scene.presenter.HomeScenceCustomListPresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 自动Fragment
 */
public class HomeSceneAutomaticFragment extends BaseFragment implements IHomeCustomScenceView {

    private RLoadingDialog mLoadingDialog;
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    private SceneAutomaticListRecyclerViewAdapter adapter;
    private List<HashMap<String, Object>> mlist = new ArrayList<HashMap<String, Object>>();
    private LinearLayoutManager mLayoutManager;
    private HomeScenceCustomListPresenter mHomeScenceCustomListPresenter = new HomeScenceCustomListPresenter(this, this);
    private String mSerialNo;
    private SceneAutomaticListRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new SceneAutomaticListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
            VolSceneEntity volSceneEntity = (VolSceneEntity) mlist.get(postion).get("volSceneEntity");
            List<SceneOperation> operations = volSceneEntity.getOperations();
            List<SceneExecCondition> sceneExecConditions = volSceneEntity.getConditions();
            if (null == operations) {
                operations = new ArrayList<>();
            }

            if (null == sceneExecConditions) {
                sceneExecConditions = new ArrayList<>();
            }
            DeviceScenceEtidItem mDeviceScenceEtidItem = new DeviceScenceEtidItem();
            mDeviceScenceEtidItem.setOperations(operations);
            mDeviceScenceEtidItem.setSceneExecConditions(sceneExecConditions);
            mDeviceScenceEtidItem.setPk(volSceneEntity.getId());
            unregisteredEventBus();
            EventBus.getDefault().postSticky(mDeviceScenceEtidItem);
            startActivity(new Intent(mContext, ScenceCustomEditeListActivity.class));
        }

        @Override
        public void onItemLongClick(int postion) {

        }
    };

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_automatic_sence, null);
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        initAdapter();
    }

    private void initAdapter() {
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        adapter = new SceneAutomaticListRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showResult(ScenceDefultListBean bean) {
        if (bean == null) {
            return;
        }

        if (null == bean) {
            return;
        }
        adapter.clear();
        List<VolSceneEntity> list = bean.getList();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            VolSceneEntity volSceneEntity = list.get(i);
            List<SceneOperation> operations = volSceneEntity.getOperations();
            int sizes = operations.size();
            if (sizes == 0) {
                continue;
            }
            HashMap<String, Object> map = new HashMap<>();
            String name = volSceneEntity.getSceneName();
            if (getResources().getString(R.string.home_scene_sleep_pattern).equals(name) || getResources().getString(R.string.home_scene_getup_pattern).equals(name) || getResources().getString(R.string.home_scene_gethome_pattern).equals(name) || getResources().getString(R.string.home_scene_awayhome_pattern).equals(name)) {
                continue;
            }
            map.put("title", name);
            List<SceneOperation> ls = volSceneEntity.getOperations();
            int leng = 0;
            if (null != ls) {
                leng = ls.size();
            }
            map.put("message", String.valueOf(leng));
            map.put("volSceneEntity", volSceneEntity);
            adapter.addData(map, mlist.size());
        }
        EventBus.getDefault().postSticky(new MessageCount(mlist.size()));
    }

    private void initQuery() {
        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
        mSerialNo = Utils.readSharedPreferences(sb.toString());
        if (TextUtils.isEmpty(mSerialNo)) {
            return;
        }
        mHomeScenceCustomListPresenter.scenceDefultListQuery(mSerialNo);
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
