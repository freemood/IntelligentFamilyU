package com.Intelligent.FamilyU.model.cloud.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.cloud.biz.CloudAddTokenBiz;
import com.Intelligent.FamilyU.model.cloud.entity.CloudAddTokenBean;
import com.Intelligent.FamilyU.model.cloud.iface.ICloudAddTokenView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 */

public class CloudAddTokenPresenter extends BasePresenter<ICloudAddTokenView, LifecycleProvider> {

    private final String TAG = CloudAddTokenPresenter.class.getSimpleName();

    public CloudAddTokenPresenter(ICloudAddTokenView view, LifecycleProvider activity) {
        super(view, activity);
    }


    /**
     * 获取信息
     *
     */
    public void addToken(String mSerialNo) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new CloudAddTokenBiz().addToken(mSerialNo, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((CloudAddTokenBean) object[0]);
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
