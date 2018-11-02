package com.Intelligent.FamilyU.model.cloud.activity;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.CloudTypeListRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.base.Constants;
import com.Intelligent.FamilyU.model.cloud.db.SQLiteUtil;
import com.Intelligent.FamilyU.model.cloud.entity.CloudAddTokenBean;
import com.Intelligent.FamilyU.model.cloud.entity.CloudDeleteTaskBean;
import com.Intelligent.FamilyU.model.cloud.entity.CloudEvent;
import com.Intelligent.FamilyU.model.cloud.entity.CloudFileBean;
import com.Intelligent.FamilyU.model.cloud.entity.CloudSearchFileListBean;
import com.Intelligent.FamilyU.model.cloud.entity.CloudStorageFile;
import com.Intelligent.FamilyU.model.cloud.iface.ICloudAddTokenView;
import com.Intelligent.FamilyU.model.cloud.iface.ICloudDeleteView;
import com.Intelligent.FamilyU.model.cloud.iface.ICloudSearchView;
import com.Intelligent.FamilyU.model.cloud.presenter.CloudAddTokenPresenter;
import com.Intelligent.FamilyU.model.cloud.presenter.CloudDeletePresenter;
import com.Intelligent.FamilyU.model.cloud.presenter.CloudSearchPresenter;
import com.Intelligent.FamilyU.utils.MediaFile;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.view.SwipeItemLayout;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 云存储
 */
public class CloudStorageTypeActivity extends BaseFragmentActivity implements ICloudSearchView, ICloudDeleteView, ICloudAddTokenView {

    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private RLoadingDialog mLoadingDialog;
    private CloudTypeListRecyclerViewAdapter adapter;
    private List<CloudStorageFile> mlist = new ArrayList<CloudStorageFile>();
    private LinearLayoutManager mLayoutManager;
    private String mSerialNo;
    private String type;
    private CloudSearchPresenter mCloudSearchPresenter = new CloudSearchPresenter(this, this);
    private CloudDeletePresenter mCloudDeletePresenter = new CloudDeletePresenter(this, this);
    private CloudAddTokenPresenter mCloudAddTokenPresenter = new CloudAddTokenPresenter(this, this);
    private CloudTypeListRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new CloudTypeListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            Uri uri;
//            if (Build.VERSION.SDK_INT >= 24) {
//                File file = new File(filePath);
//                uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", new File(filePath));
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//注意加上这句话
//
//            } else {
//                uri = Uri.fromFile(new File(filePath));
//            }
//            intent.setDataAndType(uri, "image/*");
//            startActivity(intent);

        }

        @Override
        public void onItemLongClick(int postion) {

        }

        @Override
        public void onItemDelete(int position) {
            mCloudDeletePresenter.cloudDeleteTaskQuery(mSerialNo, mlist.get(position).getPath().replace("\\", "/"));
            adapter.deleteItem(position);
            Constants.CLOUD_ALL_FILES.remove(position);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onItemDownload(int position) {
            CloudStorageFile cloudStorageFile = mlist.get(position);
            add(cloudStorageFile);

        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_cloud_list;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        registeredEventBus();
        mLoadingDialog = new RLoadingDialog(mContext, true);
        initAdapter();
        if (Constants.CLOUD_ADD_TOKEN) {
            initList();
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
            mSerialNo = Utils.readSharedPreferences(sb.toString());
            mCloudAddTokenPresenter.addToken(mSerialNo);
        }

    }

    @Override
    protected void initData() {
        //  titleTv.setText(R.string.home_cloud_title);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getEvent(CloudEvent messageEvent) {
        type = messageEvent.getMessage();
        titleTv.setText(type);
        // 移除粘性事件
        EventBus.getDefault().removeStickyEvent(messageEvent);
    }

    @OnClick({R.id.page_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_back:
                finish();
                break;
        }
    }

    private void initAdapter() {
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        adapter = new CloudTypeListRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
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

    @Override
    public void showResult(CloudSearchFileListBean bean) {
        if (null == bean) {
            return;
        }
        ArrayList<CloudStorageFile> files = bean.getFiles();
        Constants.CLOUD_ALL_FILES.clear();
        Constants.CLOUD_ALL_FILES = files;
        showList(Constants.CLOUD_ALL_FILES);
    }

    @Override
    public void showResult(CloudDeleteTaskBean bean) {
        if (null == bean) {
            return;
        }
        if (bean.isSuccess()) {
            showToast("刪除成功");
        }

    }

    @Override
    public void showResult(CloudAddTokenBean bean) {
        if (null == bean) {
            return;
        }
        initList();
    }

    private void add(CloudStorageFile cloudStorageFile) {
        CloudFileBean mCloudFileBean = new CloudFileBean();
        mCloudFileBean.setFileName(cloudStorageFile.getName());
        mCloudFileBean.setType("down");
        //'build', 'start', 'continue', 'stop',  'delete',download,'complete'
        mCloudFileBean.setStatus("build");
        mCloudFileBean.setSdcardFilePath(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM))+"/"+cloudStorageFile.getName());
        mCloudFileBean.setFileDowdloadLength("0");
        mCloudFileBean.setFileLength(String.valueOf(cloudStorageFile.getSize()));
        mCloudFileBean.setFileDownPath(cloudStorageFile.getPath().replace("\\", "/"));
        mCloudFileBean.setUserName(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name)));
        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
        mCloudFileBean.setSerialNo(Utils.readSharedPreferences(sb.toString()));
        //根目录
        mCloudFileBean.setFileUpPath("/");
        SQLiteUtil.add(mContext, mCloudFileBean);
        ToastUtils.showToast(mContext, "已加入下在队列");
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
    protected void onDestroy() {
        super.onDestroy();

    }

    private void showList(ArrayList<CloudStorageFile> files) {
        int size = files.size();
        Constants.CLOUD_ALL_FILES = files;
        for (int i = 0; i < size; i++) {
            CloudStorageFile cloudStorageFile = files.get(i);
            if (!cloudStorageFile.isDirectory()) {
                String name = cloudStorageFile.getName();
                if (getResources().getString(R.string.home_cloud_all_files).equals(type)) {
                    adapter.addData(files.get(i), mlist.size());
                } else if (MediaFile.isAudioFileType(name) && getResources().getString(R.string.home_cloud_music_files).equals(type)) {
                    adapter.addData(files.get(i), mlist.size());
                } else if (MediaFile.isVideoFileType(name) && getResources().getString(R.string.home_cloud_video_files).equals(type)) {
                    adapter.addData(files.get(i), mlist.size());
                } else if (MediaFile.isImageFileType(name) && getResources().getString(R.string.home_cloud_photo_files).equals(type)) {
                    adapter.addData(files.get(i), mlist.size());
                } else if (MediaFile.isZipFileType(name) && getResources().getString(R.string.home_cloud_zip_files).equals(type)) {
                    adapter.addData(files.get(i), mlist.size());
                } else if (!MediaFile.isAudioFileType(name) && !MediaFile.isVideoFileType(name) && !MediaFile.isImageFileType(name) && !MediaFile.isZipFileType(name) && getResources().getString(R.string.home_cloud_doc_files).equals(type)) {
                    adapter.addData(files.get(i), mlist.size());
                }
            }else{
                Constants.CLOUD_ALL_FILES_DIR.add(cloudStorageFile);
            }
        }
    }

    private void initList() {
        if (null == Constants.CLOUD_ALL_FILES || Constants.CLOUD_ALL_FILES.size() == 0) {
            mCloudSearchPresenter.cloudDownSearch(mSerialNo);
        } else {
            showList(Constants.CLOUD_ALL_FILES);
        }
    }

}
