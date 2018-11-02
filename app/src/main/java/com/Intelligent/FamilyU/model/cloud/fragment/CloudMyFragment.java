package com.Intelligent.FamilyU.model.cloud.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.view.IBaseView;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的Fragment
 */
public class CloudMyFragment extends BaseFragment implements IBaseView {

    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.cloud_space_tv)
    TextView cloudSpaceTv;
    @BindView(R.id.user_name_tv)
    TextView userNameTv;

    private RLoadingDialog mLoadingDialog;

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_cloud_my, null);
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
    }

    @Override
    protected void initData() {
        userNameTv.setText(mContext.getResources().getString(R.string.home_individual, 3));
    }

    @OnClick({R.id.name_rl, R.id.user_rl, R.id.doaction_rl, R.id.download_path_rl})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.name_rl:
                break;
            case R.id.user_rl:
                break;
            case R.id.doaction_rl:
                break;
            case R.id.download_path_rl:
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
}
