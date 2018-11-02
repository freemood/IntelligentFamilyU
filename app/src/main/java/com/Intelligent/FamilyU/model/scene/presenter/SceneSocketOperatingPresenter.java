package com.Intelligent.FamilyU.model.scene.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.scene.biz.ScoketUntieBiz;
import com.Intelligent.FamilyU.model.scene.biz.SocketOperatingBiz;
import com.Intelligent.FamilyU.model.scene.entity.ScoketRestartBean;
import com.Intelligent.FamilyU.model.scene.entity.SocketOpratingBean;
import com.Intelligent.FamilyU.model.scene.iface.IScenceOperatingView;
import com.Intelligent.FamilyU.model.scene.iface.ISocketUntieView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 解绑 presenter
 */

public class SceneSocketOperatingPresenter extends BasePresenter<IScenceOperatingView, LifecycleProvider> {

    private final String TAG = SceneSocketOperatingPresenter.class.getSimpleName();

    public SceneSocketOperatingPresenter(IScenceOperatingView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取信息
     */
    public void socketOperating(String gatewaySn, String deviceMac, String params) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new SocketOperatingBiz().socketOperating(gatewaySn, deviceMac, params, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((SocketOpratingBean) object[0]);
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
