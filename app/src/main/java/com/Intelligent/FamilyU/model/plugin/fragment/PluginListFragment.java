package com.Intelligent.FamilyU.model.plugin.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.PluginListRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.model.plugin.bean.PluginListBean;
import com.Intelligent.FamilyU.model.plugin.iface.IPluginListView;
import com.Intelligent.FamilyU.model.plugin.presenter.PluginListPresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 插件列表fragment
 */
public class PluginListFragment extends BaseFragment implements IPluginListView {
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    private RLoadingDialog mLoadingDialog;
    private PluginListRecyclerViewAdapter adapter;
    private List<HashMap<String, String>> mlist = new ArrayList<HashMap<String, String>>();
    private LinearLayoutManager mLayoutManager;
    //    private String[] nameStrings = null;
//    private String[] messageStrings = null;
//    private String[] timeStrings = null;
    private PluginListPresenter mPluginListPresenter = new PluginListPresenter(this, this);

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_plugin_list, null);
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        initAdapter();
    }

    @Override
    protected void initData() {
        mPluginListPresenter.pluginListQuery();
    }

    private void initAdapter() {
//        nameStrings = new String[]{"远程下载", "家庭云储存"};
//        messageStrings = new String[]{"v1.0", "v2.1"};
//        timeStrings = new String[]{"安装", "更新"};
//        int length = nameStrings.length;
//        for (int i = 0; i < length; i++) {
//            HashMap<String, String> map = new HashMap<String, String>();
//            map.put("title", nameStrings[i]);
//            map.put("message", messageStrings[i]);
//            map.put("install", timeStrings[i]);
//            mlist.add(map);
//        }
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        adapter = new PluginListRecyclerViewAdapter(mContext, mlist);
        mRecyclerView.setAdapter(adapter);
    }


    @Override
    public void showResult(PluginListBean bean) {
        if (bean == null) {
            return;
        }
//
//        tvOperator.setText(bean.getOperator());
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
