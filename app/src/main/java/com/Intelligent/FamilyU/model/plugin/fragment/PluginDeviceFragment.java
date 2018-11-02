package com.Intelligent.FamilyU.model.plugin.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.GridSpacingItemDecoration;
import com.Intelligent.FamilyU.adapter.MyGridLayoutManager;
import com.Intelligent.FamilyU.adapter.PluginDevicePageRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.base.Constants;
import com.Intelligent.FamilyU.model.cloud.activity.CloudStorageActivity;
import com.Intelligent.FamilyU.model.download.activity.RemotedownloadActivity;
import com.Intelligent.FamilyU.model.home.fragment.HomeFurnishingFragment;
import com.Intelligent.FamilyU.model.plugin.bean.PluginListPageBean;
import com.Intelligent.FamilyU.model.plugin.bean.PluginPageEntity;
import com.Intelligent.FamilyU.model.plugin.iface.ICallPluginIdListener;
import com.Intelligent.FamilyU.model.plugin.iface.ICallserialNoListener;
import com.Intelligent.FamilyU.model.plugin.iface.IPluginListPageView;
import com.Intelligent.FamilyU.model.plugin.presenter.PluginListPagePresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * 插件设备
 */
public class PluginDeviceFragment extends BaseFragment implements IPluginListPageView, ICallPluginIdListener {
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    // private RLoadingDialog mLoadingDialog;
    private PluginDevicePageRecyclerViewAdapter adapter;
    private List<HashMap<String, String>> mlist = new ArrayList<HashMap<String, String>>();
    private LinearLayoutManager mLayoutManager;
    private PluginListPagePresenter mPluginListPresenter = new PluginListPagePresenter(this, this);
    private String mPluginId;
    private PluginDevicePageRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new PluginDevicePageRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
            if (null == mlist || mlist.size() == 0) {
                return;
            }
            String name = mlist.get(postion).get("name");
            if (name.contains("远程下载")) {
                startActivity(new Intent(mContext, RemotedownloadActivity.class));
            }else if (name.contains("云存储")){
                startActivity(new Intent(mContext, CloudStorageActivity.class));

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
        //mLoadingDialog = new RLoadingDialog(mContext, true);
        initAdapter();
        callbackPluginId();
        //initQuery();
    }

    //监听网络序列号
    private void callbackPluginId() {
        Fragment fragment = getParentFragment();
        if (fragment instanceof HomeFurnishingFragment) {
            ((HomeFurnishingFragment) (fragment)).setICallPluginIdListener(this);
        }
    }

    @Override
    protected void initData() {

    }

    private void initAdapter() {

        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new MyGridLayoutManager(mContext, 3);
        //mLayoutManager.setScrollEnabled(false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(mContext, 3, 20));
        adapter = new PluginDevicePageRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
        mRecyclerView.setAdapter(adapter);
    }

    private void initQuery() {
        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("pluginId");
        mPluginId = Utils.readSharedPreferences(sb.toString());
        if (TextUtils.isEmpty(mPluginId)) {
            adapter.clear();
            return;
        }
        mPluginListPresenter.pluginListQuery(mPluginId);
    }

    @Override
    public void showLoading() {
        // mLoadingDialog.show();
    }

    @Override
    public void closeLoading() {
        // mLoadingDialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(mContext, msg);
    }

    private void addItem(int postion) {
        String name = mlist.get(postion).get("name");
        if ("add".equals(name)) {
//            Intent intent = new Intent(mContext, DeviceAddActivity.class);
//            mContext.startActivity(intent);
            HashMap<String, String> map = new HashMap<String, String>();
            Random random = new Random();
            // map.put("name", fruitStrings[random.nextInt(fruitStrings.length)]);
            adapter.addData(map, postion);
            //        adapter.addBtn();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initQuery();
    }

    @Override
    public void showResult(List<PluginPageEntity.PluginGatewayInfo> bean) {
        if (bean == null) {
            return;
        }
        adapter.clear();
        HashMap<String, String> map = new HashMap<String, String>();

//        String[] fruitStrings = new String[]{"云存储"};
//        int length = fruitStrings.length;
//        for (int i = 0; i < length; i++) {
//            map = new HashMap<String, String>();
//            map.put("name", fruitStrings[i]);
//            mlist.add(map);
//        }

        int size = bean.size();
        for (int i = 0; i < size; i++) {
            map = new HashMap<String, String>();
            map.put("name", bean.get(i).getPluginGateway().getPluginName());
            adapter.addData(map, mlist.size());
        }

    }

    @Override
    public void getCallPluginId(String pluginId) {
        if (TextUtils.isEmpty(pluginId)) {
            return;
        }
        mPluginId = pluginId;
        mPluginListPresenter.pluginListQuery(mPluginId);
    }
}

