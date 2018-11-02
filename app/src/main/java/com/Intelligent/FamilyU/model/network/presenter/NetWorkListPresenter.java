package com.Intelligent.FamilyU.model.network.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.network.bean.NetWorkListBean;
import com.Intelligent.FamilyU.model.network.biz.NetWorkListBiz;
import com.Intelligent.FamilyU.model.network.iface.INetWorkListView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class NetWorkListPresenter extends BasePresenter<INetWorkListView, LifecycleProvider> {
    private final String TAG = NetWorkListPresenter.class.getSimpleName();

    public NetWorkListPresenter(INetWorkListView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 查询所有插件版本信息
     */
    public void netWorkListQuery(String gatewaySn) {

        if (getView() != null)
            getView().showLoading();


        HttpRxCallback httpRxCallback = new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((NetWorkListBean) object[0]);
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
        };

        new NetWorkListBiz().netWorkListQuery(gatewaySn, getActivity(), httpRxCallback);
    }
}
