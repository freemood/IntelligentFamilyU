package com.Intelligent.FamilyU.model.network.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.network.bean.NetWorkModifyNameBean;
import com.Intelligent.FamilyU.model.network.bean.NetWorkUnbindBean;
import com.Intelligent.FamilyU.model.network.biz.NetWorkModifyNameBiz;
import com.Intelligent.FamilyU.model.network.biz.NetWorkUnbineBiz;
import com.Intelligent.FamilyU.model.network.iface.INetWorkModifyNameView;
import com.Intelligent.FamilyU.model.network.iface.INetWorkUnbineView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 网关name presenter
 */

public class NetWorkUnbinePresenter extends BasePresenter<INetWorkUnbineView, LifecycleProvider> {

    private final String TAG = NetWorkUnbinePresenter.class.getSimpleName();

    public NetWorkUnbinePresenter(INetWorkUnbineView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取信息
     */
    public void unbindNetWork(String gatewaySn,String mac) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new NetWorkUnbineBiz().unbindNetWork(gatewaySn, mac, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((NetWorkUnbindBean) object[0]);
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
