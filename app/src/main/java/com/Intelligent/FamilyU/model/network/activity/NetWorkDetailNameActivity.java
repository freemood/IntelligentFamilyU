package com.Intelligent.FamilyU.model.network.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.model.main.event.MessageEvent;
import com.Intelligent.FamilyU.model.network.bean.NetWorkEventBus;
import com.Intelligent.FamilyU.model.network.bean.NetWorkModifyNameBean;
import com.Intelligent.FamilyU.model.network.iface.INetWorkModifyNameView;
import com.Intelligent.FamilyU.model.network.presenter.NetWorkModifyNamePresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 组网详情
 */
public class NetWorkDetailNameActivity extends BaseFragmentActivity implements INetWorkModifyNameView {
    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;
    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRl;

    @BindView(R.id.et_name)
    TextView networkNameTv;
    private String name;
    private NetWorkEventBus mNetWorkEventBus;
    private RLoadingDialog mLoadingDialog;
    private NetWorkModifyNamePresenter mNetWorkModifyNamePresenter = new NetWorkModifyNamePresenter(this, this);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_network_detail_name;
    }

    @Override
    protected void initBundleData() {
    }

    @Override
    protected void initView() {
        registeredEventBus();
        mLoadingDialog = new RLoadingDialog(mContext, true);
        titleTv.setText(R.string.home_network_detail_name);
        pageRightRl.setVisibility(View.VISIBLE);
        pageRightTv.setText(R.string.btn_save);
    }

    @Override
    public void onFamilyEvent(Object object) {
        mNetWorkEventBus = (NetWorkEventBus) object;
        name = mNetWorkEventBus.getName();
        super.onFamilyEvent(object);
    }

    @Override
    protected void initData() {
        networkNameTv.setText(name);
    }


    @OnClick({R.id.page_back, R.id.page_right_rl})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_back:
                finish();
                break;
            case R.id.page_right_rl:
                String nameTv = networkNameTv.getText().toString();
                if (TextUtils.isEmpty(nameTv)) {
                    ToastUtils.showToast(mContext, getResources().getString(R.string.home_network_detail_name_null_no));
                    return;
                }
                name = nameTv;
                mNetWorkModifyNamePresenter.mdifyNameNetWork(mNetWorkEventBus.getBasesn(), name);
                break;
        }
    }

    @Override
    public void showResult(NetWorkModifyNameBean bean) {
        if (null == bean) {
            return;
        }
        if (bean.isResult()) {
            EventBus.getDefault().postSticky(new MessageEvent(name));
            finish();
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
