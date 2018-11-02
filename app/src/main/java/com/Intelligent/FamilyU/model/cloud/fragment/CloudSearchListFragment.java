package com.Intelligent.FamilyU.model.cloud.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.CloudSearchListRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.base.Constants;
import com.Intelligent.FamilyU.model.cloud.activity.CloudStorageTypeActivity;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 */
public class CloudSearchListFragment extends BaseFragment implements ICloudSearchView, ICloudDeleteView, ICloudAddTokenView {

    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.all_files_ll)
    LinearLayout allFilesLl;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.et_search)
    TextView etSearch;

    private RLoadingDialog mLoadingDialog;
    private CloudSearchListRecyclerViewAdapter adapter;
    private List<CloudStorageFile> mlist = new ArrayList<CloudStorageFile>();
    private LinearLayoutManager mLayoutManager;
    private String mSerialNo;
    private CloudSearchPresenter mCloudSearchPresenter = new CloudSearchPresenter(this, this);
    private CloudDeletePresenter mCloudDeletePresenter = new CloudDeletePresenter(this, this);
    private CloudAddTokenPresenter mCloudAddTokenPresenter = new CloudAddTokenPresenter(this, this);
    private CloudSearchListRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new CloudSearchListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {

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
            // initList();
        }

        @Override
        public void onItemDownload(int position) {
            CloudStorageFile cloudStorageFile = mlist.get(position);
            add(cloudStorageFile);
        }
    };

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_cloud_allfile_list, null);
    }

    @Override
    protected void initBundleData() {
    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        initAdapter();
        initEt();
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

    }

    private void add(CloudStorageFile cloudStorageFile) {
        CloudFileBean mCloudFileBean = new CloudFileBean();
        mCloudFileBean.setFileName(cloudStorageFile.getName());
        mCloudFileBean.setType("down");
        //'build', 'start', 'continue', 'stop',  'delete',download,'complete'
        mCloudFileBean.setStatus("build");
        mCloudFileBean.setSdcardFilePath(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)) + "/" + cloudStorageFile.getName());
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

    private void initEt() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int leng = charSequence.length();
                if (leng > 0) {
                    allFilesLl.setVisibility(View.GONE);
                    swipeRefresh.setVisibility(View.VISIBLE);
                    showSearch(charSequence.toString());
                } else {
                    swipeRefresh.setVisibility(View.GONE);
                    allFilesLl.setVisibility(View.VISIBLE);
                    adapter.clear();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initAdapter() {
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        adapter = new CloudSearchListRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
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


    @OnClick({R.id.file_dir, R.id.file_doc, R.id.file_music, R.id.file_video, R.id.file_photo, R.id.file_zips})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.file_dir:
                EventBus.getDefault().postSticky(new CloudEvent(getResources().getString(R.string.home_cloud_all_files)));
                startActivity(new Intent(mContext, CloudStorageTypeActivity.class));
                break;
            case R.id.file_doc:
                EventBus.getDefault().postSticky(new CloudEvent(getResources().getString(R.string.home_cloud_doc_files)));
                startActivity(new Intent(mContext, CloudStorageTypeActivity.class));
                break;
            case R.id.file_music:
                EventBus.getDefault().postSticky(new CloudEvent(getResources().getString(R.string.home_cloud_music_files)));
                startActivity(new Intent(mContext, CloudStorageTypeActivity.class));
                break;
            case R.id.file_video:
                EventBus.getDefault().postSticky(new CloudEvent(getResources().getString(R.string.home_cloud_video_files)));
                startActivity(new Intent(mContext, CloudStorageTypeActivity.class));
                break;
            case R.id.file_photo:
                EventBus.getDefault().postSticky(new CloudEvent(getResources().getString(R.string.home_cloud_photo_files)));
                startActivity(new Intent(mContext, CloudStorageTypeActivity.class));
                break;
            case R.id.file_zips:
                EventBus.getDefault().postSticky(new CloudEvent(getResources().getString(R.string.home_cloud_zip_files)));
                startActivity(new Intent(mContext, CloudStorageTypeActivity.class));
                break;


        }
    }

    @Override
    public void showLoading() {
        // mLoadingDialog.show();
    }

    @Override
    public void closeLoading() {
        //  mLoadingDialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(mContext, msg);
    }

    @Override
    public void showResult(CloudSearchFileListBean bean) {
        if (null == bean) {
            return;
        }
        ArrayList<CloudStorageFile> files = bean.getFiles();
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

    @Override
    public void onResume() {
        super.onResume();
    }

    private void showList(ArrayList<CloudStorageFile> files) {
        int size = files.size();
        Constants.CLOUD_ALL_FILES = files;
        for (int i = 0; i < size; i++) {
            if (!files.get(i).isDirectory()) {
                adapter.addData(files.get(i), mlist.size());
            }else{
                Constants.CLOUD_ALL_FILES_DIR.add(files.get(i));
            }
        }
    }

    private void initList() {

//        if (null == Constants.CLOUD_ALL_FILES || Constants.CLOUD_ALL_FILES.size() == 0) {
        mCloudSearchPresenter.cloudDownSearch(mSerialNo);
//        } else {
//            showList(Constants.CLOUD_ALL_FILES);
//        }
    }

    private void showSearch(String show) {
        int size = Constants.CLOUD_ALL_FILES.size();
        if (size == 0) {
            return;
        }
        adapter.clear();
        for (int i = 0; i < size; i++) {
            CloudStorageFile cloudStorageFile = Constants.CLOUD_ALL_FILES.get(i);
            if (!cloudStorageFile.isDirectory()) {
                String name = cloudStorageFile.getName();
                if (name.contains(show)) {
                    adapter.addData(cloudStorageFile, mlist.size());
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
