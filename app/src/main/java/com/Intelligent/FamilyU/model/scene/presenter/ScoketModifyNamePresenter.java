package com.Intelligent.FamilyU.model.scene.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.gateway.biz.GatewayModifyNameBiz;
import com.Intelligent.FamilyU.model.gateway.entity.GatewayModifyNameBean;
import com.Intelligent.FamilyU.model.gateway.iface.IGatewayModifyNameView;
import com.Intelligent.FamilyU.model.scene.biz.SocketModifyNameBiz;
import com.Intelligent.FamilyU.model.scene.entity.SocketModifyNameBean;
import com.Intelligent.FamilyU.model.scene.iface.ISocketModifyNameView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 智能插座name presenter
 */

public class ScoketModifyNamePresenter extends BasePresenter<ISocketModifyNameView, LifecycleProvider> {

    private final String TAG = ScoketModifyNamePresenter.class.getSimpleName();

    public ScoketModifyNamePresenter(ISocketModifyNameView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取信息
     */
    public void modifyNameSocket(String deviceId, String name) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new SocketModifyNameBiz().modifyNameSocket(deviceId, name, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((SocketModifyNameBean) object[0]);
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
