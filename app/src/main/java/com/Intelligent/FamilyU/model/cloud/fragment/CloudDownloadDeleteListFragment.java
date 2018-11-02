package com.Intelligent.FamilyU.model.cloud.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.CloudDownloadDeleteListRecyclerViewAdapter;
import com.Intelligent.FamilyU.adapter.RemoteDownloadDeleteListRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.model.cloud.activity.CloudStorageActivity;
import com.Intelligent.FamilyU.model.cloud.db.SQLiteUtil;
import com.Intelligent.FamilyU.model.cloud.entity.CloudFileBean;
import com.Intelligent.FamilyU.model.cloud.iface.IRefreshCloudDeleteList;
import com.Intelligent.FamilyU.model.cloud.iface.IRefreshCloudRecoveryList;
import com.Intelligent.FamilyU.model.download.activity.RemotedownloadActivity;
import com.Intelligent.FamilyU.model.download.entity.RemoteListBean;
import com.Intelligent.FamilyU.model.download.iface.IRefreshDeleteList;
import com.Intelligent.FamilyU.model.download.iface.IRemoteListView;
import com.Intelligent.FamilyU.model.download.presenter.RemoteListPresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.view.IBaseView;
import com.Intelligent.FamilyU.view.SwipeItemLayout;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 */
public class CloudDownloadDeleteListFragment extends BaseFragment implements IBaseView,IRefreshCloudDeleteList {

    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    private RLoadingDialog mLoadingDialog;
    private CloudDownloadDeleteListRecyclerViewAdapter adapter;
    private List<CloudFileBean> mlist = new ArrayList<CloudFileBean>();
    private LinearLayoutManager mLayoutManager;
    private String mSerialNo;
    private List<CloudFileBean> mlistFile = new ArrayList<CloudFileBean>();
    private IRefreshCloudRecoveryList iRefreshCloudRecoveryList;
    private CloudDownloadDeleteListRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new CloudDownloadDeleteListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
            //dapter.notifyDataSetChanged();
        }

        @Override
        public void onItemLongClick(int postion) {

        }

        @Override
        public void onItemecoveryClick(int position) {
            SQLiteUtil.delete(mContext,mlist.get(position));
            adapter.deleteItem(position);
            adapter.notifyDataSetChanged();
            if(null!=iRefreshCloudRecoveryList){
                iRefreshCloudRecoveryList.refreshRecoveryList();
            }
        }
    };

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_cloud_download_delete_files_list, null);
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        initAdapter();
        if (getParentFragment() instanceof CloudTransmissionMainFragment) {
            ((CloudTransmissionMainFragment) getParentFragment()).setRefreshDeleteList(this);
        }
    }

    @Override
    protected void initData() {
        initList();
        initDataFile();
    }

    private void initList() {
        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
        mSerialNo = Utils.readSharedPreferences(sb.toString());

    }
    private void initDataFile() {
        adapter.clear();
        mlistFile = SQLiteUtil.query(mContext);
        int size = mlistFile.size();
        for (int i = 0; i < size; i++) {
            CloudFileBean mCloudFileBean = mlistFile.get(i);
            String status = mCloudFileBean.getStatus();
            if ("delete".equals(status)) {
                adapter.addData(mCloudFileBean, mlistFile.size());
            }

        }
    }
    private void initAdapter() {
//        String[] nameStrings = new String[]{"文档.doc", "音乐.ogg", "图片.png", "视频.mp4", "压缩.zip"};
//        int length = nameStrings.length;
//        for (int i = 0; i < length; i++) {
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("title", nameStrings[i]);
//            mlist.add(map);
//        }
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        adapter = new CloudDownloadDeleteListRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        initDataFile();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
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

    @Override
    public void refreshDeleteList() {
        initDataFile();
    }

    public void setRefreshRecoveryList(IRefreshCloudRecoveryList refreshRecoveryList) {
        iRefreshCloudRecoveryList = refreshRecoveryList;
    }
}
