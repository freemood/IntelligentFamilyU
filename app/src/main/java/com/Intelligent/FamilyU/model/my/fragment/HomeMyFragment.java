package com.Intelligent.FamilyU.model.my.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.base.Constants;
import com.Intelligent.FamilyU.model.cloud.entity.CloudSearchFileListBean;
import com.Intelligent.FamilyU.model.cloud.entity.CloudStorageFile;
import com.Intelligent.FamilyU.model.cloud.iface.ICloudSearchView;
import com.Intelligent.FamilyU.model.cloud.presenter.CloudSearchPresenter;
import com.Intelligent.FamilyU.model.login.activity.LoginActivity;
import com.Intelligent.FamilyU.model.login.activity.LoginForgetPasswordActivity;
import com.Intelligent.FamilyU.model.my.activity.ProblemReportActivity;
import com.Intelligent.FamilyU.model.my.activity.ProductOrderListActivity;
import com.Intelligent.FamilyU.model.my.entity.UpdateAppBean;
import com.Intelligent.FamilyU.model.my.iface.IUpdateAppView;
import com.Intelligent.FamilyU.model.my.persenter.UpdateAppPresenter;
import com.Intelligent.FamilyU.model.webview.WebViewActivity;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的Fragment
 */
public class HomeMyFragment extends BaseFragment implements IUpdateAppView, ICloudSearchView {

    @BindView(R.id.user_list_number)
    TextView userNumberTv;
    @BindView(R.id.file_list_number)
    TextView fileNumberTl;
    @BindView(R.id.home_login_name)
    TextView homeLoginNameTv;
    private CloudSearchPresenter mCloudSearchPresenter = new CloudSearchPresenter(this, this);
    private UpdateAppPresenter mUpdateAppPresenter = new UpdateAppPresenter(this, this);
    // private RLoadingDialog mLoadingDialog;
    private int fileNumber = Constants.CLOUD_ALL_FILES.size() - Constants.CLOUD_ALL_FILES_DIR.size();

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_main_my, null);
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        //mLoadingDialog = new RLoadingDialog(mContext, true);
        homeLoginNameTv.setText(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name)));
    }

    @Override
    protected void initData() {
        userNumberTv.setText(mContext.getResources().getString(R.string.home_individual, fileNumber));
        fileNumberTl.setText(mContext.getResources().getString(R.string.home_individual, 0));
        initList();
    }

    @OnClick({R.id.exit, R.id.my_manual_rl, R.id.my_update_rl, R.id.my_password_rl, R.id.my_order_rl, R.id.my_feedback_rl, R.id.user_list_ll, R.id.file_list_ll, R.id.my_room_mananger_rl})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_manual_rl:
                break;
            case R.id.my_password_rl:
                startActivity(new Intent(mContext, LoginForgetPasswordActivity.class));
                break;
            case R.id.user_list_ll:
                //  startActivity(new Intent(mContext, UserListActivity.class));
                break;
            case R.id.file_list_ll:
                //startActivity(new Intent(mContext, PluginActivity.class));
                break;
            case R.id.my_order_rl:
                startActivity(new Intent(mContext, ProductOrderListActivity.class));
                break;
            case R.id.my_feedback_rl:
                startActivity(new Intent(mContext, ProblemReportActivity.class));
                break;
            case R.id.my_update_rl:
                mUpdateAppPresenter.updateApp();
                break;
            case R.id.exit:
                showToast(getResources().getString(R.string.home_drawer_exit_app));
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                break;
        }
    }

    @Override
    public void showResult(UpdateAppBean bean) {
        if (null == bean) {
            return;
        }
        EventBus.getDefault().postSticky(bean);
        startActivity(new Intent(mContext, WebViewActivity.class));
    }

    @Override
    public void showResult(CloudSearchFileListBean bean) {
        if (null == bean) {
            return;
        }
        ArrayList<CloudStorageFile> files = bean.getFiles();
        Constants.CLOUD_ALL_FILES.clear();
        Constants.CLOUD_ALL_FILES = files;
        showList(files);
    }

    private void showList(ArrayList<CloudStorageFile> files) {
        int size = files.size();
        Constants.CLOUD_ALL_FILES = files;
        for (int i = 0; i < size; i++) {
            CloudStorageFile cloudStorageFile = files.get(i);
            if (cloudStorageFile.isDirectory()) {
                Constants.CLOUD_ALL_FILES_DIR.add(cloudStorageFile);
            }
        }
        fileNumber = Constants.CLOUD_ALL_FILES.size() - Constants.CLOUD_ALL_FILES_DIR.size();
        userNumberTv.setText(mContext.getResources().getString(R.string.home_individual, fileNumber));
    }

    private void initList() {
        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
        String mSerialNo = Utils.readSharedPreferences(sb.toString());
        if (null == Constants.CLOUD_ALL_FILES || Constants.CLOUD_ALL_FILES.size() == 0) {
            mCloudSearchPresenter.cloudDownSearch(mSerialNo);
        } else {
            showList(Constants.CLOUD_ALL_FILES);
        }
    }

    @Override
    public void showLoading() {
        // mLoadingDialog.show();
    }

    @Override
    public void closeLoading() {
        // mLoadingDialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(mContext, msg);
    }
}
