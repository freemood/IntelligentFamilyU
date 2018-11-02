package com.Intelligent.FamilyU.model.scene.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.scene.biz.HomeAllDeviceListBiz;
import com.Intelligent.FamilyU.model.scene.biz.HomeDeviceOprationBiz;
import com.Intelligent.FamilyU.model.scene.entity.DeviceOperationListBean;
import com.Intelligent.FamilyU.model.scene.entity.HomeDeviceListBean;
import com.Intelligent.FamilyU.model.scene.iface.IHomeScenceView;
import com.Intelligent.FamilyU.model.scene.iface.IScenceOperationView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class HomeOprationListPresenter extends BasePresenter<IScenceOperationView, LifecycleProvider> {
    private final String TAG = HomeOprationListPresenter.class.getSimpleName();

    public HomeOprationListPresenter(IScenceOperationView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 查询所有插件版本信息
     */
    public void oprationListQuery(String modelId) {

        if (getView() != null)
            getView().showLoading();


        HttpRxCallback httpRxCallback = new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((DeviceOperationListBean) object[0]);
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

        new HomeDeviceOprationBiz().oprationDeviceListQuery(modelId,getActivity(), httpRxCallback);
    }
}
