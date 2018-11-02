package com.Intelligent.FamilyU.model.gateway.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.gateway.biz.GatewayUntieBiz;
import com.Intelligent.FamilyU.model.gateway.biz.GatewayWifiInfoBiz;
import com.Intelligent.FamilyU.model.gateway.entity.GatewayUntieBean;
import com.Intelligent.FamilyU.model.gateway.entity.GatewayWifiInfoBean;
import com.Intelligent.FamilyU.model.gateway.iface.IGatewayUntieView;
import com.Intelligent.FamilyU.model.gateway.iface.IGetawaryWifiInfoView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 网关wifipresenter
 *
 */

public class GatewayWifiInfoPresenter extends BasePresenter<IGetawaryWifiInfoView, LifecycleProvider> {

    private final String TAG = GatewayWifiInfoPresenter.class.getSimpleName();

    public GatewayWifiInfoPresenter(IGetawaryWifiInfoView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取信息
     *
     */
    public void gatewayWifiInfo(String serialNo) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new GatewayWifiInfoBiz().gatewayWifiInfo(serialNo, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((GatewayWifiInfoBean) object[0]);
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
