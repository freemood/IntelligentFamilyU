package com.Intelligent.FamilyU.model.download.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.DownloadFileStartingListRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.model.download.activity.RemotedownloadActivity;
import com.Intelligent.FamilyU.model.download.activity.RemotedownloadFileDirActivity;
import com.Intelligent.FamilyU.model.download.dialog.ConfirmDialog;
import com.Intelligent.FamilyU.model.download.entity.RemoteBuildTaskBean;
import com.Intelligent.FamilyU.model.download.entity.RemoteContinueTaskBean;
import com.Intelligent.FamilyU.model.download.entity.RemoteDeleteTaskBean;
import com.Intelligent.FamilyU.model.download.entity.RemoteListBean;
import com.Intelligent.FamilyU.model.download.entity.RemoteOneTaskBean;
import com.Intelligent.FamilyU.model.download.entity.RemoteStartTaskBean;
import com.Intelligent.FamilyU.model.download.entity.RemoteStopTaskBean;
import com.Intelligent.FamilyU.model.download.iface.IRefreshDowdLoadList;
import com.Intelligent.FamilyU.model.download.iface.IRemoteBuildTaskView;
import com.Intelligent.FamilyU.model.download.iface.IRemoteContinueTaskView;
import com.Intelligent.FamilyU.model.download.iface.IRemoteDeleteTaskView;
import com.Intelligent.FamilyU.model.download.iface.IRemoteListView;
import com.Intelligent.FamilyU.model.download.iface.IRemoteOneTaskView;
import com.Intelligent.FamilyU.model.download.iface.IRemoteStartTaskView;
import com.Intelligent.FamilyU.model.download.iface.IRemoteStopTaskView;
import com.Intelligent.FamilyU.model.download.presenter.RemoteBuildTaskPresenter;
import com.Intelligent.FamilyU.model.download.presenter.RemoteContinueTaskPresenter;
import com.Intelligent.FamilyU.model.download.presenter.RemoteDeleteTaskPresenter;
import com.Intelligent.FamilyU.model.download.presenter.RemoteListPresenter;
import com.Intelligent.FamilyU.model.download.presenter.RemoteOneTaskPresenter;
import com.Intelligent.FamilyU.model.download.presenter.RemoteStartTaskPresenter;
import com.Intelligent.FamilyU.model.download.presenter.RemoteStopTaskPresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.view.SwipeItemLayout;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 远程下载fragment
 */
public class RemoteDownloadListFragment extends BaseFragment implements IRemoteListView, IRemoteDeleteTaskView, IRemoteBuildTaskView, IRemoteStartTaskView, IRemoteStopTaskView, IRemoteContinueTaskView, IRemoteOneTaskView {

    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    private RLoadingDialog mLoadingDialog;
    private DownloadFileStartingListRecyclerViewAdapter adapter;
    private List<HashMap<String, Object>> mlist = new ArrayList<HashMap<String, Object>>();
    private LinearLayoutManager mLayoutManager;
    private String mSerialNo;
    private String url;
    private ConfirmDialog confirmDialog;
    private RemoteListPresenter mRemoteListPresenter = new RemoteListPresenter(this, this);
    private RemoteBuildTaskPresenter mRemoteBuildTaskPresenter = new RemoteBuildTaskPresenter(this, this);
    private RemoteStartTaskPresenter mRemoteStartTaskPresenter = new RemoteStartTaskPresenter(this, this);
    private RemoteStopTaskPresenter mRemoteStopTaskPresenter = new RemoteStopTaskPresenter(this, this);
    private RemoteContinueTaskPresenter mRemoteContinueTaskPresenter = new RemoteContinueTaskPresenter(this, this);
    private RemoteOneTaskPresenter mRemoteOneTaskPresenter = new RemoteOneTaskPresenter(this, this);
    private RemoteDeleteTaskPresenter mRemoteDeleteTaskPresenter = new RemoteDeleteTaskPresenter(this, this);
    private IRefreshDowdLoadList iRefreshDowdLoadList;
    private List<HashMap<String, Object>> mPauselist = new ArrayList<>();
    private Handler mHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    private DownloadFileStartingListRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new DownloadFileStartingListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {

        }

        @Override
        public void onItemLongClick(int postion) {

        }

        @Override
        public void onItemDelete(int position) {
            adapter.deleteItem(position);
            if (null == mlist || mlist.size() == 0 || position > mlist.size()) {
                return;
            }
            HashMap<String, Object> map = mlist.get(position);
            RemoteListBean.TaskInfo tk = (RemoteListBean.TaskInfo) map.get("TaskInfo");
            mRemoteDeleteTaskPresenter.remoteDeleteTaskQuery(mSerialNo, tk.getTaskId());
        }

        @Override
        public void onItemPause(int position, TextView tv, TextView show) {

            HashMap<String, Object> map = mlist.get(position);
            RemoteListBean.TaskInfo tk = (RemoteListBean.TaskInfo) map.get("TaskInfo");
            HashMap<String, Object> mPauseMap = queryMap(tk.getTaskId());

            String status = tk.getStatus();
            boolean isStop = false;
            if ("Create".equals(status)) {
                tk.setStatus("Download");
                mRemoteStartTaskPresenter.remoteStartTaskQuery(mSerialNo, tk.getTaskId());
            } else if ("Stop".equals(status)) {
                tk.setStatus("Download");
                mRemoteContinueTaskPresenter.remoteContinueTaskQuery(mSerialNo, tk.getTaskId());
            } else if ("Download".equals(status)) {
                tk.setStatus("Stop");
                isStop = true;
                mHander.removeCallbacksAndMessages(null);
                mRemoteStopTaskPresenter.remoteStopTaskQuery(mSerialNo, tk.getTaskId());
            }
            mPauseMap.put("id", tk.getTaskId());
            mPauseMap.put("status", tk.getStatus());
            mPauseMap.put("showTv", show);
            mPauseMap.put("messageTv", tv);
            mPauseMap.put("isStop", isStop);
            mPauseMap.put("TaskInfo", tk);
            mPauseMap.put("position", position);
            int pos = (int) mPauseMap.get("pos");
            if (pos != -1) {
                mPauselist.set(pos, mPauseMap);
            } else {
                mPauselist.add(mPauseMap);
            }

            map.put("TaskInfo", tk);
            mlist.set(position, map);
//            mlist.add(map);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onItemTimeSetting(int position) {

        }
    };

    public HashMap<String, Object> queryMap(String taskId) {
        int size = mPauselist.size();
        HashMap<String, Object> mPauseMap = new HashMap<>();
        mPauseMap.put("pos", -1);
        for (int i = 0; i < size; i++) {
            String id = (String) mPauselist.get(i).get("id");
            if (id.equals(taskId)) {
                mPauseMap = mPauselist.get(i);
                mPauseMap.put("pos", i);
            }
        }
        return mPauseMap;
    }

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_remote_download_starting_list, null);
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        initAdapter();
        initListener();
    }

    private void initListener() {
        Activity mActivity = getActivity();
        if (mActivity instanceof RemotedownloadActivity) {
            ((RemotedownloadActivity) mActivity).setICallFileDirPathListener(this);
        }
    }

    @Override
    protected void initData() {

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
        adapter = new DownloadFileStartingListRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));

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

    public void updateView(String id) {
        HashMap<String, Object> map = queryMap(id);
        adapter.changeStatus((TextView) map.get("showTv"), (String) map.get("status"));
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
            if (!"Complete".equals(status) && !"Delete".equals(status)) {
                map = new HashMap<String, Object>();
                map.put("TaskInfo", tk);
                adapter.addData(map, mlist.size() + 1 + i);
            }
        }

    }

    @Override
    public void showResult(RemoteBuildTaskBean bean) {
        if (null == bean) {
            return;
        }
        if (bean.isSuccess()) {
            showToast(getResources().getString(R.string.home_remote_built_task_ok));
            initList();
        }
    }

    @Override
    public void showResult(RemoteStartTaskBean bean) {
        if (null == bean) {
            return;
        }
        if (bean.isSuccess()) {
            updateView(bean.getTaskId());
            mRemoteOneTaskPresenter.remoteOneTaskQuery(mSerialNo, bean.getTaskId());
        }
    }

    @Override
    public void showResult(RemoteStopTaskBean bean) {
        if (null == bean) {
            return;
        }
        if (bean.isSuccess()) {
            updateView(bean.getTaskId());
        }
    }

    @Override
    public void showResult(RemoteContinueTaskBean bean) {
        if (null == bean) {
            return;
        }
        if (bean.isSuccess()) {
            updateView(bean.getTaskId());
            mRemoteOneTaskPresenter.remoteOneTaskQuery(mSerialNo, bean.getTaskId());
        }
    }

    @Override
    public void showResult(RemoteOneTaskBean bean) {
        if (null == bean) {
            return;
        }
        final RemoteOneTaskBean.TaskInfo mTaskInfo = bean.getTask();
        HashMap<String, Object> map = queryMap(bean.getTaskId());
        if ("Complete".equals(mTaskInfo.getStatus()) && mTaskInfo.getDownload() == mTaskInfo.getLength()) {
            adapter.showSpeedTextView(mTaskInfo, (TextView) map.get("messageTv"));
            adapter.clear();
            initList();
            iRefreshDowdLoadList.refreshDowdLoadList();
        } else {
            if ((boolean) map.get("isStop")) {
                return;
            }
            adapter.showSpeedTextView(mTaskInfo, (TextView) map.get("messageTv"));
            mRemoteOneTaskPresenter.remoteOneTaskQuery(mSerialNo, mTaskInfo.getTaskId());

        }

    }

    @Override
    public void showResult(RemoteDeleteTaskBean bean) {
        if (null == bean) {
            return;
        }
        iRefreshDowdLoadList.refreshDowdLoadList();
    }

    @Override
    public void getFileDirPath(String path) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (TextUtils.isEmpty(path)) {
            StringBuffer readsb = new StringBuffer();
            readsb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("RemotedownloadFileDir_read");
            path = Utils.readSharedPreferences(readsb.toString());
        }
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        String delayTime = String.valueOf(0);

        mRemoteBuildTaskPresenter.remoteBuildTaskQuery(mSerialNo, fileName, path, url, delayTime);
    }

    public void confirmDialog() {
        confirmDialog = new ConfirmDialog(mContext);
        confirmDialog.show();
        confirmDialog.setClicklistener(new ConfirmDialog.ClickListenerInterface() {
            @Override
            public void doConfirm(String title) {
                url = title;
                int size = mlist.size();
                for (int i = 0; i < size; i++) {
                    RemoteListBean.TaskInfo tk = (RemoteListBean.TaskInfo) mlist.get(i).get("TaskInfo");
                    if (url.contains(tk.getFileName())) {
                        showToast("不能重复创建同名的文件");
                        return;
                    }
                }
                confirmDialog.dismiss();
                startActivityForResult(new Intent(mContext, RemotedownloadFileDirActivity.class), 5);
            }

            @Override
            public void doCancel() {
                confirmDialog.dismiss();
            }
        });
    }

    @OnClick({R.id.file})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.file:
                confirmDialog();
                break;
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

    public void setRefreshDowdLoadList(IRefreshDowdLoadList refreshDowdLoadList) {
        iRefreshDowdLoadList = refreshDowdLoadList;
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != confirmDialog) {
            confirmDialog.dismiss();
        }
        mHander.removeCallbacksAndMessages(null);
    }
}
