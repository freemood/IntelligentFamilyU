package com.Intelligent.FamilyU.model.download.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.RemoteDownloadDeleteListRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.model.download.activity.RemotedownloadActivity;
import com.Intelligent.FamilyU.model.download.entity.RemoteListBean;
import com.Intelligent.FamilyU.model.download.iface.IRefreshDeleteList;
import com.Intelligent.FamilyU.model.download.iface.IRemoteListView;
import com.Intelligent.FamilyU.model.download.presenter.RemoteListPresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.view.SwipeItemLayout;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 远程下载fragment
 */
public class RemoteDownloadDeleteListFragment extends BaseFragment implements IRemoteListView,IRefreshDeleteList {

    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    private RLoadingDialog mLoadingDialog;
    private RemoteDownloadDeleteListRecyclerViewAdapter adapter;
    private List<HashMap<String, Object>> mlist = new ArrayList<HashMap<String, Object>>();
    private LinearLayoutManager mLayoutManager;
    private String mSerialNo;
    private RemoteListPresenter mRemoteListPresenter = new RemoteListPresenter(this, this);
    private RemoteDownloadDeleteListRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new RemoteDownloadDeleteListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
            //dapter.notifyDataSetChanged();
        }

        @Override
        public void onItemLongClick(int postion) {

        }

        @Override
        public void onItemecoveryClick(int position) {
            adapter.deleteItem(position);
        }
    };

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_download_delete_files_list, null);
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        initAdapter();
        if (getActivity() instanceof RemotedownloadActivity) {
            ((RemotedownloadActivity) getActivity()).setRefreshDeleteList(this);
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
        adapter = new RemoteDownloadDeleteListRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

//        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh);
//        swipeRefreshLayout.setColorSchemeColors(Color.RED);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                }, 2000);
//            }
//        });
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
            if ("Delete".equals(status)) {
                map = new HashMap<String, Object>();
                map.put("TaskInfo", tk);
                adapter.addData(map, mlist.size() + 1 + i);
            }
        }

    }

    @Override
    public void refreshDeleteList() {
        initList();
    }

    @Override
    public void onResume() {
        super.onResume();
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
