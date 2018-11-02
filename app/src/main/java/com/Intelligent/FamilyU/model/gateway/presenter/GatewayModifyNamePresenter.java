package com.Intelligent.FamilyU.model.gateway.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.gateway.biz.GatewayModifyNameBiz;
import com.Intelligent.FamilyU.model.gateway.biz.GatewayRestartBiz;
import com.Intelligent.FamilyU.model.gateway.entity.GatewayModifyNameBean;
import com.Intelligent.FamilyU.model.gateway.entity.GatewayRestartBean;
import com.Intelligent.FamilyU.model.gateway.iface.IGatewayModifyNameView;
import com.Intelligent.FamilyU.model.gateway.iface.IGatewayRestartView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 网关name presenter
 */

public class GatewayModifyNamePresenter extends BasePresenter<IGatewayModifyNameView, LifecycleProvider> {

    private final String TAG = GatewayModifyNamePresenter.class.getSimpleName();

    public GatewayModifyNamePresenter(IGatewayModifyNameView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取信息
     */
    public void mdifyNameGateway(String serialNo, String name) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new GatewayModifyNameBiz().modifyNameGateway(serialNo, name, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((GatewayModifyNameBean) object[0]);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showToast(desc);
                }
            }

            @Override
            public void onCancel() {
                if (getView() != null) {
                    getView().closeLoading();
                }
            }
        });


    }


}
