package com.Intelligent.FamilyU.model.scene.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.scene.biz.ScenceDefultListBiz;
import com.Intelligent.FamilyU.model.scene.entity.ScenceDefultListBean;
import com.Intelligent.FamilyU.model.scene.iface.IHomeDefultScenceView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class HomeScenceDefultListPresenter extends BasePresenter<IHomeDefultScenceView, LifecycleProvider> {
    private final String TAG = HomeScenceDefultListPresenter.class.getSimpleName();

    public HomeScenceDefultListPresenter(IHomeDefultScenceView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 查询所有插件版本信息
     */
    public void scenceDefultListQuery(String serialNo) {

        if (getView() != null)
            getView().showLoading();


        HttpRxCallback httpRxCallback = new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((ScenceDefultListBean) object[0]);
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

        new ScenceDefultListBiz().scenceDefultListQuery(serialNo, getActivity(), httpRxCallback);
    }
}
