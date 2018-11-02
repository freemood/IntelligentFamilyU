package com.Intelligent.FamilyU.model.gateway.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.gateway.biz.GetawaryBaseDetailBiz;
import com.Intelligent.FamilyU.model.gateway.entity.DeviceGateWayBean;
import com.Intelligent.FamilyU.model.gateway.iface.IGetawaryBaseDetailView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 查询网关Presenter
 *
 */
public class GetawaryBaseDetailPresenter extends BasePresenter<IGetawaryBaseDetailView, LifecycleProvider> {

    private final String TAG = GetawaryBaseDetailPresenter.class.getSimpleName();

    public GetawaryBaseDetailPresenter(IGetawaryBaseDetailView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取信息
     *
     */
    public void queryGateway() {
        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new GetawaryBaseDetailBiz().queryGateway(getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((DeviceGateWayBean) object[0]);
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
