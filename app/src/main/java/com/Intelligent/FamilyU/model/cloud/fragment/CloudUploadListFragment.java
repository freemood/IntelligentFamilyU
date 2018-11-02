package com.Intelligent.FamilyU.model.cloud.fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.CloudDownLoadListRecyclerViewAdapter;
import com.Intelligent.FamilyU.adapter.CloudStartingListRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.base.Constants;
import com.Intelligent.FamilyU.model.cloud.db.SQLiteUtil;
import com.Intelligent.FamilyU.model.cloud.entity.CloudAddTokenBean;
import com.Intelligent.FamilyU.model.cloud.entity.CloudFileBean;
import com.Intelligent.FamilyU.model.cloud.iface.ICloudAddTokenView;
import com.Intelligent.FamilyU.model.cloud.iface.IRefreshCloudDeleteList;
import com.Intelligent.FamilyU.model.cloud.iface.IRefreshCloudRecoveryList;
import com.Intelligent.FamilyU.model.cloud.iface.IUpLoadCloud;
import com.Intelligent.FamilyU.model.cloud.presenter.CloudAddTokenPresenter;
import com.Intelligent.FamilyU.model.cloud.upload.ApiUtil;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.view.IBaseView;
import com.Intelligent.FamilyU.view.SwipeItemLayout;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * 下载fragment
 */
public class CloudUploadListFragment extends BaseFragment implements IBaseView, IRefreshCloudRecoveryList, ICloudAddTokenView, IUpLoadCloud {

    @BindView(R.id.my_recycler_view_start)
    RecyclerView mRecyclerViewStart;

    @BindView(R.id.my_recycler_view_download)
    RecyclerView mRecyclerViewDownload;
    @BindView(R.id.loud_upload_tv)
    TextView downingTv;
    @BindView(R.id.loud_upload_ok_tv)
    TextView downloadOkTv;
    private CloudDownLoadListRecyclerViewAdapter adapterDownload;
    private List<CloudFileBean> mlistDownload = new ArrayList<CloudFileBean>();
    private LinearLayoutManager mLayoutManagerDownload;

    private RLoadingDialog mLoadingDialog;
    private CloudStartingListRecyclerViewAdapter adapterStart;
    private List<CloudFileBean> mlistStart = new ArrayList<CloudFileBean>();
    private LinearLayoutManager mLayoutManagerStart;
    private List<CloudFileBean> mlistFile = new ArrayList<CloudFileBean>();
    private String mSerialNo;
    private IRefreshCloudDeleteList irefreshDeleteList;
    private CloudAddTokenPresenter mCloudAddTokenPresenter = new CloudAddTokenPresenter(this, this);
    private CloudDownLoadListRecyclerViewAdapter.OnItemClickListener mOnItemClickListenerDownLoad = new CloudDownLoadListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
        }

        @Override
        public void onItemLongClick(int postion) {

        }

        @Override
        public void onItemDeleteClick(int position) {
            SQLiteUtil.delete(mContext, mlistDownload.get(position));
            adapterDownload.removeItem(position);
            adapterDownload.notifyDataSetChanged();
            if (null == irefreshDeleteList) {
                return;
            }
            irefreshDeleteList.refreshDeleteList();
            updateView();
        }

    };
    private CloudStartingListRecyclerViewAdapter.OnItemClickListener mOnItemClickListenerStart = new CloudStartingListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {

        }

        @Override
        public void onItemLongClick(int postion) {

        }

        @Override
        public void onItemDelete(int position) {
            SQLiteUtil.delete(mContext, mlistStart.get(position));
            adapterStart.deleteItem(position);

            adapterStart.notifyDataSetChanged();
            updateView();
        }

        @Override
        public void onItemPause(final int position, TextView tv) {
            final CloudFileBean mCloudFileBean = mlistStart.get(position);
            //'build', 'start', 'continue', 'stop',  download ，'delete'，complete
            String status = mCloudFileBean.getStatus();
            if ("build".equals(status) || "stop".equals(status)) {
               // showLoading();
                ApiUtil.uploadCloud(tv, mCloudFileBean, CloudUploadListFragment.this).enqueue(new Callback<Result<String>>() {//返回结果
                    @Override
                    public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                        if (response.isSuccessful()) {
                            // Request result = response.raw().request();
                            //upPathList.add(result.url().toString());
                            Log.i(TAG, "上传成功啦" + response.toString());
                            showToast("上传成功");
                            mCloudFileBean.setStatus("download");
                            downloadUpComplete(mCloudFileBean, position);
                            updateView();
                        } else {
                            showToast("上传失败");
                            Log.i(TAG, "上传失败啦");

                        }
                        closeLoading();
                    }

                    @Override
                    public void onFailure(Call<Result<String>> call, Throwable t) {
                        showToast("上传失败");
                        Log.i(TAG, "上传失败啦");
                        closeLoading();
                    }
                });
            } else if ("download".equals(status)) {
//                mCloudFileBean.setStatus("stop");
//                downloadUpStop(mCloudFileBean);
//                mlistStart.set(position, mCloudFileBean);
            }
            adapterStart.notifyDataSetChanged();

        }

        @Override
        public void onItemTimeSetting(int position) {

        }
    };

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_cloud_upload_starting_list, null);
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        if (getParentFragment() instanceof CloudTransmissionMainFragment) {
            ((CloudTransmissionMainFragment) getParentFragment()).setRefreshRecoveryList(this);
        }
        initAdapterStart();
        initAdapterDownload();
        initList();

    }

    private void initDataFile() {
        adapterStart.clear();
        adapterDownload.clear();
        mlistFile = SQLiteUtil.query(mContext);
        int size = mlistFile.size();
        for (int i = 0; i < size; i++) {
            CloudFileBean mCloudFileBean = mlistFile.get(i);
            String type = mCloudFileBean.getType();
            if (!"up".equals(type)) {
                continue;
            }
            String status = mCloudFileBean.getStatus();
            if (!"delete".equals(status) && !"complete".equals(status)) {
                adapterStart.addData(mCloudFileBean, mlistStart.size());
            }
            if ("complete".equals(status)) {
                adapterDownload.addData(mCloudFileBean, mlistDownload.size());
            }
        }
        updateView();
    }

    private void updateView() {
        downingTv.setText(mContext.getResources().getString(R.string.home_cloud_upload_starting, mlistStart.size()));
        downloadOkTv.setText(mContext.getResources().getString(R.string.home_cloud_upload_ok, mlistDownload.size()));
    }

    @Override
    protected void initData() {

        //initDataFile();
    }

    private void initList() {
        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
        mSerialNo = Utils.readSharedPreferences(sb.toString());
        if (!Constants.CLOUD_ADD_TOKEN) {
            mCloudAddTokenPresenter.addToken(mSerialNo);
        }

    }

    private void initAdapterDownload() {


        mRecyclerViewDownload.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManagerDownload = new LinearLayoutManager(mContext);
        mRecyclerViewDownload.setLayoutManager(mLayoutManagerDownload);
        // specify an adapter (see also next example)
        adapterDownload = new CloudDownLoadListRecyclerViewAdapter(mContext, mlistDownload, mOnItemClickListenerDownLoad);
        mRecyclerViewDownload.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(mContext));
        mRecyclerViewDownload.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh_download);
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
        mRecyclerViewDownload.setAdapter(adapterDownload);
    }

    private void initAdapterStart() {
        mRecyclerViewStart.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManagerStart = new LinearLayoutManager(mContext);
        mRecyclerViewStart.setLayoutManager(mLayoutManagerStart);
        // specify an adapter (see also next example)
        adapterStart = new CloudStartingListRecyclerViewAdapter(mContext, mlistStart, mOnItemClickListenerStart);
        mRecyclerViewStart.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(mContext));
        mRecyclerViewStart.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh_start);
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
        mRecyclerViewStart.setAdapter(adapterStart);
    }


//    @OnClick({R.id.file})
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.file:
//                confirmDialog();
//                break;
//        }
//    }

    @Override
    public void showResult(CloudAddTokenBean bean) {
        if (null == bean) {
            return;
        }
    }

    private void downloadUpComplete(CloudFileBean mCloudFileBean, int position) {
        mCloudFileBean.setStatus("complete");
        mCloudFileBean.setFileDowdloadLength(mCloudFileBean.getFileLength());
        SQLiteUtil.update(mContext, mCloudFileBean);
        adapterStart.deleteItem(position);

        adapterDownload.addData(mCloudFileBean, mlistDownload.size());
        adapterStart.notifyDataSetChanged();
    }

    private void downloadUpStop(CloudFileBean mCloudFileBean) {
        mCloudFileBean.setStatus("stop");
        //   mCloudFileBean.setFileDowdloadLength(mCloudFileBean.getFileLength());
        SQLiteUtil.update(mContext, mCloudFileBean);
        // adapterStart.deleteItem(position);
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
    public void onResume() {
        super.onResume();
        initList();
        initDataFile();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void refreshRecoveryList() {
        initDataFile();
    }

    public void setRefreshDeleteList(IRefreshCloudDeleteList refreshDeleteList) {
        irefreshDeleteList = refreshDeleteList;
    }

    @Override
    public void onUpdateView(final TextView tv, CloudFileBean mCloudFileBean, final long bytesWritten) {
        final long lengs = Long.parseLong(mCloudFileBean.getFileLength());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapterStart.showSpeedTextView(tv, lengs, bytesWritten);
            }
        });
    }
}
