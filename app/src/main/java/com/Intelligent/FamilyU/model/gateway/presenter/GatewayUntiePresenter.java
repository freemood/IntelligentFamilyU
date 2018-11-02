package com.Intelligent.FamilyU.model.gateway.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.gateway.biz.GatewayUntieBiz;
import com.Intelligent.FamilyU.model.gateway.entity.GatewayRestartBean;
import com.Intelligent.FamilyU.model.gateway.entity.GatewayUntieBean;
import com.Intelligent.FamilyU.model.gateway.iface.IGatewayRestartView;
import com.Intelligent.FamilyU.model.gateway.iface.IGatewayUntieView;
import com.Intelligent.FamilyU.model.home.biz.CpuAndMemoryBiz;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 网关解绑 presenter
 *
 */

public class GatewayUntiePresenter extends BasePresenter<IGatewayUntieView, LifecycleProvider> {

    private final String TAG = GatewayUntiePresenter.class.getSimpleName();

    public GatewayUntiePresenter(IGatewayUntieView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取信息
     *
     */
    public void untieGateway(String serialNo) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new GatewayUntieBiz().untieGateway(serialNo, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((GatewayUntieBean) object[0]);
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
