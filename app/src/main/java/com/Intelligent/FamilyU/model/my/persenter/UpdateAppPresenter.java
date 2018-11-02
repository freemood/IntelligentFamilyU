package com.Intelligent.FamilyU.model.my.persenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.my.biz.UpdateBiz;
import com.Intelligent.FamilyU.model.my.entity.UpdateAppBean;
import com.Intelligent.FamilyU.model.my.iface.IUpdateAppView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 */

public class UpdateAppPresenter extends BasePresenter<IUpdateAppView, LifecycleProvider> {

    private final String TAG = UpdateAppPresenter.class.getSimpleName();

    public UpdateAppPresenter(IUpdateAppView view, LifecycleProvider activity) {
        super(view, activity);
    }


    /**
     * 获取信息
     */
    public void updateApp() {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new UpdateBiz().updateApp(getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((UpdateAppBean) object[0]);
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
