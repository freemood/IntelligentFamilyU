package com.Intelligent.FamilyU.model.scene.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.base.Constants;
import com.Intelligent.FamilyU.model.main.MainActivity;
import com.Intelligent.FamilyU.model.network.bean.NetWorkBindBean;
import com.Intelligent.FamilyU.model.network.iface.INetWorkBineView;
import com.Intelligent.FamilyU.model.network.presenter.NetWorkBindPresenter;
import com.Intelligent.FamilyU.model.scene.entity.ScenceUnbindBean;
import com.Intelligent.FamilyU.model.scene.iface.IScenceUnbineView;
import com.Intelligent.FamilyU.model.scene.presenter.ScenceUnbinePresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class ScenceAddOkActivity extends BaseFragmentActivity implements IScenceUnbineView {

    @BindView(R.id.page_title)
    TextView titleTv;
    private RLoadingDialog mLoadingDialog;
    private ScenceUnbinePresenter mScenceBindPresenter = new ScenceUnbinePresenter(this, this);
    @Override
    protected int getContentViewId() {
        return R.layout.activity_bind_getawary_ok;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(this, true);
        titleTv.setText(R.string.home_add_scence_bind);
    }

    @Override
    protected void initData() {
//        StringBuffer sb = new StringBuffer();
//        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
//       String mSerialNo = Utils.readSharedPreferences(sb.toString());
//        if (TextUtils.isEmpty(mSerialNo)) {
//            showToast("请先绑定网关设备");
//            return;
//        }
        mScenceBindPresenter.unbindScence(Constants.SCENCE_NO);
    }


    @OnClick({R.id.btn_ok, R.id.page_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                Intent mIntent = new Intent(ScenceAddOkActivity.this, MainActivity.class);
                startActivity(mIntent);
                break;
            case R.id.page_back:
                finish();
                break;
        }
    }

    @Override
    public void showResult(ScenceUnbindBean bean) {
        closeLoading();
        if (null == bean) {
            return;
        }
        boolean isGetawary = bean.isResult();
        if (isGetawary) {
            showToast(getResources().getString(R.string.home_add_gateway_ok_title));
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
