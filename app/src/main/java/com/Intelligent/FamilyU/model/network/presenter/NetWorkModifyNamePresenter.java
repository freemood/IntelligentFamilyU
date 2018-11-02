package com.Intelligent.FamilyU.model.network.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.network.bean.NetWorkModifyNameBean;
import com.Intelligent.FamilyU.model.network.biz.NetWorkModifyNameBiz;
import com.Intelligent.FamilyU.model.network.iface.INetWorkModifyNameView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 网关name presenter
 */

public class NetWorkModifyNamePresenter extends BasePresenter<INetWorkModifyNameView, LifecycleProvider> {

    private final String TAG = NetWorkModifyNamePresenter.class.getSimpleName();

    public NetWorkModifyNamePresenter(INetWorkModifyNameView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取信息
     */
    public void mdifyNameNetWork(String networkingSn, String name) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new NetWorkModifyNameBiz().modifyNameNetWork(networkingSn, name, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((NetWorkModifyNameBean) object[0]);
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
