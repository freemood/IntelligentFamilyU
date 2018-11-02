package com.Intelligent.FamilyU.model.scene.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.gateway.biz.GatewayUntieBiz;
import com.Intelligent.FamilyU.model.gateway.entity.GatewayUntieBean;
import com.Intelligent.FamilyU.model.gateway.iface.IGatewayUntieView;
import com.Intelligent.FamilyU.model.scene.biz.ScoketUntieBiz;
import com.Intelligent.FamilyU.model.scene.entity.ScoketRestartBean;
import com.Intelligent.FamilyU.model.scene.iface.ISocketUntieView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 解绑 presenter
 */

public class SceneSocketUntiePresenter extends BasePresenter<ISocketUntieView, LifecycleProvider> {

    private final String TAG = SceneSocketUntiePresenter.class.getSimpleName();

    public SceneSocketUntiePresenter(ISocketUntieView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取信息
     */
    public void untieSocket(String serialNo, String deviceId) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new ScoketUntieBiz().untieScoket(serialNo, deviceId, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((ScoketRestartBean) object[0]);
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
