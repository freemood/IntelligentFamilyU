package com.Intelligent.FamilyU.model.download.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.DownloadFiledirListRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.model.download.entity.RemoteDirListBean;
import com.Intelligent.FamilyU.model.download.iface.IRemoteDirListView;
import com.Intelligent.FamilyU.model.download.presenter.RemoteDirListPresenter;
import com.Intelligent.FamilyU.model.network.activity.NetWorkDetailActivity;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 远程下载文件夹
 */
public class RemotedownloadFileDirActivity extends BaseFragmentActivity implements IRemoteDirListView {
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.text)
    TextView textTv;
    private RLoadingDialog mLoadingDialog;
    private DownloadFiledirListRecyclerViewAdapter adapter;
    private List<HashMap<String, Object>> mlist = new ArrayList<HashMap<String, Object>>();
    private LinearLayoutManager mLayoutManager;
    private String mSerialNo;
    private RemoteDirListPresenter mRemoteListPresenter = new RemoteDirListPresenter(this, this);
    private DownloadFiledirListRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new DownloadFiledirListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
            if (null == mlist || 0 == mlist.size()) {
                return;
            }
            String title = (String) mlist.get(postion).get("title");
            textTv.setText(title);
            String path = (String) mlist.get(postion).get("title_read");
            setSharedPreferencesPath(title, path);
            Intent intent = new Intent(mContext, RemotedownloadActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("path", path);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }

        @Override
        public void onItemLongClick(int postion) {

        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_remote_download_filedir_list;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        initAdapter();
        titleTv.setText(R.string.home_remote_save_dir);
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

        StringBuffer titlesb = new StringBuffer();
        titlesb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("RemotedownloadFileDir");
        textTv.setText(Utils.readSharedPreferences(titlesb.toString()));
    }

    private void setSharedPreferencesPath(String title, String path) {
        StringBuffer titlesb = new StringBuffer();
        titlesb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("RemotedownloadFileDir");
        Utils.write(titlesb.toString(), title);

        StringBuffer readsb = new StringBuffer();
        readsb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("RemotedownloadFileDir_read");
        Utils.write(readsb.toString(), path);
    }

    private void initAdapter() {
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        adapter = new DownloadFiledirListRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
        mRecyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.page_back, R.id.save})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.save:
                finish();
                break;
            case R.id.page_back:
                finish();
                break;
        }
    }

    @Override
    public void showResult(RemoteDirListBean bean) {
        if (null == bean) {
            return;
        }
        List<String> paths = bean.getStores();
        int size = paths.size();
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < size; i++) {
            map = new HashMap<String, Object>();
            map.put("title_read", paths.get(i));
            String title = paths.get(i).replaceAll("/", "").replaceAll("\\\\", "");
            map.put("title", title);
            if (i == 0) {
                String path = textTv.getText().toString();
                if (TextUtils.isEmpty(path)) {
                    setSharedPreferencesPath(title, paths.get(i));
                    textTv.setText(title);
                }
            }
            adapter.addData(map, mlist.size() + 1 + i);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
