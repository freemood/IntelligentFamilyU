package com.Intelligent.FamilyU.model.scene.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.scene.biz.HomeAllDeviceListBiz;
import com.Intelligent.FamilyU.model.scene.entity.HomeDeviceListBean;
import com.Intelligent.FamilyU.model.scene.iface.IHomeScenceView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class HomeAllDeviceListPresenter extends BasePresenter<IHomeScenceView, LifecycleProvider> {
    private final String TAG = HomeAllDeviceListPresenter.class.getSimpleName();

    public HomeAllDeviceListPresenter(IHomeScenceView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 查询所有插件版本信息
     */
    public void homeDeviceListQuery() {

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

        new HomeAllDeviceListBiz().homeDeviceListQuery(getActivity(), httpRxCallback);
    }
}
