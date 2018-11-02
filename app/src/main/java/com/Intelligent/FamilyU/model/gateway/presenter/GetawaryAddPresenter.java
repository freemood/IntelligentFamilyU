package com.Intelligent.FamilyU.model.gateway.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.gateway.biz.GetawaryAddBiz;
import com.Intelligent.FamilyU.model.gateway.entity.GetawaryAddBean;
import com.Intelligent.FamilyU.model.gateway.iface.IGetawaryAddView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 綁定网关Presenter
 *
 */

public class GetawaryAddPresenter extends BasePresenter<IGetawaryAddView, LifecycleProvider> {

    private final String TAG = GetawaryAddPresenter.class.getSimpleName();

    public GetawaryAddPresenter(IGetawaryAddView view, LifecycleProvider activity) {
        super(view, activity);
    }


    /**
     * 获取信息
     *
     */
    public void bindGateway(String serialNo,String userId) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new GetawaryAddBiz().bindGateway(serialNo, userId, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((GetawaryAddBean) object[0]);
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
