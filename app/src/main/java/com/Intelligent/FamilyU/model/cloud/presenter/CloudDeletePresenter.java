package com.Intelligent.FamilyU.model.cloud.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.cloud.biz.CloudDeleteTaskBiz;
import com.Intelligent.FamilyU.model.cloud.entity.CloudDeleteTaskBean;
import com.Intelligent.FamilyU.model.cloud.iface.ICloudDeleteView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 */

public class CloudDeletePresenter extends BasePresenter<ICloudDeleteView, LifecycleProvider> {

    private final String TAG = CloudDeletePresenter.class.getSimpleName();

    public CloudDeletePresenter(ICloudDeleteView view, LifecycleProvider activity) {
        super(view, activity);
    }


    /**
     * 获取信息
     *
     */
    public void cloudDeleteTaskQuery(String mSerialNo,String source) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new CloudDeleteTaskBiz().cloudDeleteTaskQuery(mSerialNo,source, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((CloudDeleteTaskBean) object[0]);
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
