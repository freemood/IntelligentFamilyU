package com.Intelligent.FamilyU.model.scene.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.scene.biz.HomeDeviceListBiz;
import com.Intelligent.FamilyU.model.scene.entity.HomeDeviceListBean;
import com.Intelligent.FamilyU.model.scene.iface.IHomeScenceView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class HomeDeviceListPresenter extends BasePresenter<IHomeScenceView, LifecycleProvider> {
    private final String TAG = HomeDeviceListPresenter.class.getSimpleName();

    public HomeDeviceListPresenter(IHomeScenceView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 查询所有插件版本信息
     */
    public void homeDeviceListQuery(String gatewaySn) {

        if (getView() != null)
            getView().showLoading();


        HttpRxCallback httpRxCallback = new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((HomeDeviceListBean) object[0]);
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

        new HomeDeviceListBiz().homeDeviceListQuery(gatewaySn, getActivity(), httpRxCallback);
    }
}
