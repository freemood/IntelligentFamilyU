package com.Intelligent.FamilyU.model.cloud.fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.CloudDownLoadListRecyclerViewAdapter;
import com.Intelligent.FamilyU.adapter.CloudStartingListRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.http.retrofit.RxActionManagerImpl;
import com.Intelligent.FamilyU.model.cloud.db.SQLiteUtil;
import com.Intelligent.FamilyU.model.cloud.entity.CloudFileBean;
import com.Intelligent.FamilyU.model.cloud.iface.IDownLoadCloud;
import com.Intelligent.FamilyU.model.cloud.upload.ApiUtil;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.view.IBaseView;
import com.Intelligent.FamilyU.view.SwipeItemLayout;
import com.Intelligent.FamilyU.widget.RLoadingDialog;


import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * 远程下载fragment
 */
public class CloudDownListFragment extends BaseFragment implements IBaseView, IDownLoadCloud {

    @BindView(R.id.my_recycler_view_start)
    RecyclerView mRecyclerViewStart;

    @BindView(R.id.my_recycler_view_download)
    RecyclerView mRecyclerViewDownload;

    @BindView(R.id.loud_downing_tv)
    TextView downingTv;
    @BindView(R.id.loud_download_ok_tv)
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
    private long currentLength = 0;

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
        public void onItemPause(final int position, final TextView tv) {
            final CloudFileBean mCloudFileBean = mlistStart.get(position);
            //'build', 'start', 'continue', 'stop',  download ，'delete'，complete
            String status = mCloudFileBean.getStatus();
            if ("build".equals(status) || "stop".equals(status)) {
                //   showLoading();
                startDownload(mCloudFileBean, position, tv);
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


    private void startDownload(final CloudFileBean mCloudFileBean, final int position, final TextView tv) {
        ApiUtil.downLoadCloud(mCloudFileBean, position, tv, this);
    }

    private String writeFile(final TextView tv, Response<ResponseBody> response, CloudFileBean mCloudFileBean) {
        InputStream is = response.body().byteStream();
        String result = "";

        OutputStream os = null;

        int sBufferSize = 8192;

        try {
            os = new BufferedOutputStream(new FileOutputStream(mCloudFileBean.getSdcardFilePath()));
            byte data[] = new byte[sBufferSize];
            int len;
            while ((len = is.read(data, 0, sBufferSize)) != -1) {
                os.write(data, 0, len);
                currentLength += len;
                onUpdateView(tv, mCloudFileBean);
                result = "ok";
                //downloadListener.onProgress((int) (100 * currentLength / totalLength));
            }
            //下载完成，并返回保存的文件路径
            showToast("下载成功");
            //downloadListener.onFinish(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
            return result;
        }

    }

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_cloud_download_starting_list, null);
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        initAdapterStart();
        initAdapterDownload();
    }


    @Override
    protected void initData() {

    }

    private void initList() {
        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
        mSerialNo = Utils.readSharedPreferences(sb.toString());
//        mRemoteListPresenter.remoteListQuery(mSerialNo);
    }

    private void initDataFile() {
        adapterStart.clear();
        adapterDownload.clear();
        mlistFile = SQLiteUtil.query(mContext);
        int size = mlistFile.size();
        for (int i = 0; i < size; i++) {
            CloudFileBean mCloudFileBean = mlistFile.get(i);
            String type = mCloudFileBean.getType();
            if (!"down".equals(type)) {
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
        downingTv.setText(mContext.getResources().getString(R.string.home_cloud_downing, mlistStart.size()));
        downloadOkTv.setText(mContext.getResources().getString(R.string.home_cloud_download_ok, mlistDownload.size()));
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

    private void downloadUpComplete(CloudFileBean mCloudFileBean, int position) {
        mCloudFileBean.setStatus("complete");
        mCloudFileBean.setFileDowdloadLength(mCloudFileBean.getFileLength());
        SQLiteUtil.update(mContext, mCloudFileBean);
        adapterStart.deleteItem(position);
        adapterStart.notifyDataSetChanged();
        adapterDownload.addData(mCloudFileBean, mlistDownload.size());
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
    public void onSuccessful(final Response<ResponseBody> response, final int position, final TextView tv) {
        final CloudFileBean mCloudFileBean = mlistStart.get(position);
        Observable observable = Observable.interval(1, TimeUnit.SECONDS).create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                observableEmitter.onNext(writeFile(tv, response, mCloudFileBean));
                observableEmitter.onComplete();
            }
        });

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), false, 100)
                .subscribe(new Observer<String>() {
                    Disposable mDisposable = null;

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        String mTag = String.valueOf(System.currentTimeMillis());
                        mDisposable = disposable;
                        RxActionManagerImpl.getInstance().add(mTag, mDisposable);
                    }

                    @Override
                    public void onNext(String result) {
                        mDisposable.dispose();
                        if (!TextUtils.isEmpty(result)) {
                            mCloudFileBean.setStatus("download");
                            downloadUpComplete(mCloudFileBean, position);
                            updateView();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mDisposable.dispose();
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    public void onDownLoadFailful() {
        showToast("下载失败");
        Log.i(TAG, "下载失败啦");
    }

    @Override
    public void onFailure() {
        showToast("下载失败");
        Log.i(TAG, "下载失败啦");
    }

    @Override
    public void onUpdateView(final TextView tv, CloudFileBean mCloudFileBean) {
        final long lengs = Long.parseLong(mCloudFileBean.getFileLength());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapterStart.showSpeedTextView(tv, lengs, currentLength);
            }
        });
    }

}
