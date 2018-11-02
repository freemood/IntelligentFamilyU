package com.Intelligent.FamilyU.model.download.fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.FileMabagerDoenliadListRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.model.download.activity.RemotedownloadActivity;
import com.Intelligent.FamilyU.model.download.entity.RemoteDeleteTaskBean;
import com.Intelligent.FamilyU.model.download.entity.RemoteListBean;
import com.Intelligent.FamilyU.model.download.iface.IRefreshDeleteList;
import com.Intelligent.FamilyU.model.download.iface.IRefreshDowdLoadList;
import com.Intelligent.FamilyU.model.download.iface.IRemoteDeleteTaskView;
import com.Intelligent.FamilyU.model.download.iface.IRemoteListView;
import com.Intelligent.FamilyU.model.download.presenter.RemoteDeleteTaskPresenter;
import com.Intelligent.FamilyU.model.download.presenter.RemoteListPresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.view.IBaseView;
import com.Intelligent.FamilyU.view.SwipeItemLayout;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 已完成Fragment
 */
public class RemoteDownloadOkListFragment extends BaseFragment implements IRemoteListView, IRemoteDeleteTaskView, IRefreshDowdLoadList {
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    private RLoadingDialog mLoadingDialog;
    private FileMabagerDoenliadListRecyclerViewAdapter adapter;
    private List<HashMap<String, Object>> mlist = new ArrayList<HashMap<String, Object>>();
    private LinearLayoutManager mLayoutManager;
    private String mSerialNo;
    private RemoteListPresenter mRemoteListPresenter = new RemoteListPresenter(this, this);
    private RemoteDeleteTaskPresenter mRemoteDeleteTaskPresenter = new RemoteDeleteTaskPresenter(this, this);
    private int deletePos = 0;
    private IRefreshDeleteList iRefreshDeleteList;
    private FileMabagerDoenliadListRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new FileMabagerDoenliadListRecyclerViewAdapter.OnItemClickListener() {
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
            RemoteListBean.TaskInfo tk = (RemoteListBean.TaskInfo) mlist.get(position).get("TaskInfo");
            String taskId = tk.getTaskId();
            deletePos = position;
            mRemoteDeleteTaskPresenter.remoteDeleteTaskQuery(mSerialNo, taskId);
        }
    };

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_download_files_list, null);
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        initAdapter();
        if (getActivity() instanceof RemotedownloadActivity) {
            ((RemotedownloadActivity) getActivity()).setRefreshDowdLoadList(this);
        }
    }

    @Override
    protected void initData() {
        initList();
    }

    private void initList() {
        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
        mSerialNo = Utils.readSharedPreferences(sb.toString());
        mRemoteListPresenter.remoteListQuery(mSerialNo);
    }

    private void initAdapter() {
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        adapter = new FileMabagerDoenliadListRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh);
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

    @Override
    public void showResult(RemoteListBean bean) {
        if (null == bean) {
            return;
        }
        adapter.clear();
        List<RemoteListBean.TaskInfo> list = bean.getTasks();

        if (null == list || 0 == list.size()) {
            return;
        }
        int size = list.size();
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < size; i++) {
            RemoteListBean.TaskInfo tk = list.get(i);
            String status = tk.getStatus();
            if ("Complete".equals(status) && tk.getDownload() != tk.getLength()) {
                tk.setStatus("Stop");
            }
            status = tk.getStatus();
            if ("Complete".equals(status)) {
                map = new HashMap<String, Object>();
                map.put("TaskInfo", tk);
                adapter.addData(map, mlist.size() + 1 + i);
            }
        }

    }

    @Override
    public void showResult(RemoteDeleteTaskBean bean) {
        if (null == bean) {
            return;
        }
        if (bean.isSuccess()) {
            adapter.removeItem(deletePos);
            iRefreshDeleteList.refreshDeleteList();
        }
    }

    @Override
    public void refreshDowdLoadList() {
        initList();
    }

    public void setRefreshDeleteList(IRefreshDeleteList refreshDeleteList) {
        iRefreshDeleteList = refreshDeleteList;
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
