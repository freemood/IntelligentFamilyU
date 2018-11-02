package com.Intelligent.FamilyU.model.scene.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.model.scene.entity.DeviceEntity;
import com.Intelligent.FamilyU.model.scene.entity.DeviceOperation;
import com.Intelligent.FamilyU.model.scene.entity.DeviceOperationListBean;
import com.Intelligent.FamilyU.model.scene.entity.ScenceScoketEventBus;
import com.Intelligent.FamilyU.model.scene.entity.SocketOpratingBean;
import com.Intelligent.FamilyU.model.scene.iface.IScenceOperatingView;
import com.Intelligent.FamilyU.model.scene.iface.IScenceOperationView;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.view.SwitchView;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ScenceWindowActivity extends BaseFragmentActivity implements IScenceOperationView, IScenceOperatingView {
    @BindView(R.id.page_title)
    TextView titleTv;

    @BindView(R.id.switch_sclete)
    SwitchView mSwitchView;
    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRlTv;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;
    private ScenceScoketEventBus mScenceScoketEventBus;
    private RLoadingDialog mLoadingDialog;
    //private HomeOprationListPresenter mHomeOprationListPresenter = new HomeOprationListPresenter(this, this);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_scence_window;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    public void onFamilyEvent(Object object) {
        mScenceScoketEventBus = (ScenceScoketEventBus) object;

        initSwitch(mSwitchView);
        super.onFamilyEvent(object);
    }

    @Override
    protected void initView() {
        registeredEventBus();
        mLoadingDialog = new RLoadingDialog(mContext, true);
        titleTv.setText(R.string.home_scence_window);
        pageRightRlTv.setVisibility(View.VISIBLE);
        pageRightTv.setText(R.string.home_scence_socket_detail);
    }

    @Override
    protected void initData() {
    }


    @OnClick({R.id.page_back, R.id.page_right_rl})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_back:
                finish();
                break;
            case R.id.page_right_rl:
                unregisteredEventBus();
                EventBus.getDefault().postSticky(mScenceScoketEventBus);
                startActivity(new Intent(mContext, ScenceDeviceDetailActivity.class));
                break;
        }
    }

    private void initSwitch(SwitchView sv) {
        String name = Utils.readSharedPreferences(getResources().getString(R.string.home_login_name));
        final StringBuffer nameSb = new StringBuffer();
        nameSb.append(name).append(titleTv.getText().toString());
        boolean isPushAble = Utils.readBoolean(nameSb.toString(), false);
        sv.setOpened(isPushAble);
        sv.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                view.toggleSwitch(true); // or false
                Utils.write(nameSb.toString(), true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.toggleSwitch(false); // or true
                Utils.write(nameSb.toString(), false);
            }
        });
    }

    @Override
    public void showResult(DeviceOperationListBean bean) {
        if (null == bean) {
            return;
        }
        List<DeviceOperation> list = bean.getList();

        int size = list.size();
        for (int i = 0; i < size; i++) {
            DeviceOperation mDeviceOperation = list.get(i);
//            String name = mDeviceOperation.getName();
//            if (name.contains("电源")) {
//
//            } else if (name.contains("重启")) {
//                mScenceScoketEventBus.setParamCode(mDeviceOperation.getCode());
//                List<DeviceOperation.OperationValueList> operationValueList = mDeviceOperation.getOperationValueList();
//                int opSize = operationValueList.size();
//                for (int j = 0; j < opSize; j++) {
//                    DeviceOperation.OperationValueList ls = operationValueList.get(j);
//                    String opname = ls.getName();
//                    if (opname.contains("重启")) {
//                        mScenceScoketEventBus.setParamValue(ls.getValue());
//                    }
//                }
//            }
        }
    }

    @Override
    public void showResult(SocketOpratingBean bean) {
        if (null == bean) {
            return;
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

