package com.Intelligent.FamilyU.model.cloud.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.cloud.biz.CloudSearchBiz;
import com.Intelligent.FamilyU.model.cloud.entity.CloudSearchFileListBean;
import com.Intelligent.FamilyU.model.cloud.iface.ICloudSearchView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 */

public class CloudSearchPresenter extends BasePresenter<ICloudSearchView, LifecycleProvider> {

    private final String TAG = CloudSearchPresenter.class.getSimpleName();

    public CloudSearchPresenter(ICloudSearchView view, LifecycleProvider activity) {
        super(view, activity);
    }


    /**
     * 获取信息
     *
     */
    public void cloudDownSearch(String mSerialNo) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new CloudSearchBiz().cloudDownSearch(mSerialNo, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((CloudSearchFileListBean) object[0]);
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
