package com.Intelligent.FamilyU.model.scene.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.model.main.event.MessageEvent;
import com.Intelligent.FamilyU.model.scene.entity.ScenceScoketEventBus;
import com.Intelligent.FamilyU.model.scene.entity.SocketModifyNameBean;
import com.Intelligent.FamilyU.model.scene.iface.ISocketModifyNameView;
import com.Intelligent.FamilyU.model.scene.presenter.ScoketModifyNamePresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.view.IBaseView;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 */
public class ScenceScoketDetailInfoNameActivity extends BaseFragmentActivity implements ISocketModifyNameView {
    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;
    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRl;

    @BindView(R.id.et_name)
    TextView networkNameTv;
    private String name;
    private RLoadingDialog mLoadingDialog;
    private ScenceScoketEventBus mScenceScoketEventBus;
    private ScoketModifyNamePresenter mScoketModifyNamePresenter = new ScoketModifyNamePresenter(this, this);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_network_detail_name;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    public void onFamilyEvent(Object object) {
        mScenceScoketEventBus = (ScenceScoketEventBus) object;
        name = mScenceScoketEventBus.getName();
        super.onFamilyEvent(object);
    }

    @Override
    protected void initView() {
        registeredEventBus();
        mLoadingDialog = new RLoadingDialog(mContext, true);
        titleTv.setText(R.string.home_gateway_detail_name_info_neme);
        pageRightRl.setVisibility(View.VISIBLE);
        pageRightTv.setText(R.string.btn_save);

    }

    @Override
    protected void initData() {
        networkNameTv.setText(name);
    }


    @OnClick({R.id.page_back, R.id.page_right_rl, R.id.network_name_rl})
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
                mScoketModifyNamePresenter.modifyNameSocket(mScenceScoketEventBus.getDeviceId(), nameTv);
                break;

        }
    }

    @Override
    public void showResult(SocketModifyNameBean bean) {
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
