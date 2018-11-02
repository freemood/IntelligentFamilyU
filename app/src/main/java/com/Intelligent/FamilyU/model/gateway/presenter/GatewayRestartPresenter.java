package com.Intelligent.FamilyU.model.gateway.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.gateway.biz.GatewayRestartBiz;
import com.Intelligent.FamilyU.model.home.biz.CpuAndMemoryBiz;
import com.Intelligent.FamilyU.model.gateway.entity.GatewayRestartBean;
import com.Intelligent.FamilyU.model.gateway.iface.IGatewayRestartView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 网关重启 presenter
 *
 */

public class GatewayRestartPresenter extends BasePresenter<IGatewayRestartView, LifecycleProvider> {

    private final String TAG = GatewayRestartPresenter.class.getSimpleName();

    public GatewayRestartPresenter(IGatewayRestartView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取信息
     *
     */
    public void restartGateway(String serialNo) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new GatewayRestartBiz().restartGateway(serialNo, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((GatewayRestartBean) object[0]);
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
