package com.Intelligent.FamilyU.model.scene.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.network.bean.NetWorkUnbindBean;
import com.Intelligent.FamilyU.model.network.biz.NetWorkUnbineBiz;
import com.Intelligent.FamilyU.model.network.iface.INetWorkUnbineView;
import com.Intelligent.FamilyU.model.scene.biz.ScenceUnbineBiz;
import com.Intelligent.FamilyU.model.scene.entity.ScenceUnbindBean;
import com.Intelligent.FamilyU.model.scene.iface.IScenceUnbineView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 网关name presenter
 */

public class ScenceUnbinePresenter extends BasePresenter<IScenceUnbineView, LifecycleProvider> {

    private final String TAG = ScenceUnbinePresenter.class.getSimpleName();

    public ScenceUnbinePresenter(IScenceUnbineView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取信息
     */
    public void unbindScence(String mac) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new ScenceUnbineBiz().unbindScence(mac, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((ScenceUnbindBean) object[0]);
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
