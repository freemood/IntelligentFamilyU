package com.Intelligent.FamilyU.model.download.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.download.biz.RemoteDeleteTaskBiz;
import com.Intelligent.FamilyU.model.download.entity.RemoteDeleteTaskBean;
import com.Intelligent.FamilyU.model.download.iface.IRemoteDeleteTaskView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 远程创建下载文件 Presenter
 */

public class RemoteDeleteTaskPresenter extends BasePresenter<IRemoteDeleteTaskView, LifecycleProvider> {

    private final String TAG = RemoteDeleteTaskPresenter.class.getSimpleName();

    public RemoteDeleteTaskPresenter(IRemoteDeleteTaskView view, LifecycleProvider activity) {
        super(view, activity);
    }


    /**
     * 获取信息
     */
    public void remoteDeleteTaskQuery(String mSerialNo, String taskId) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new RemoteDeleteTaskBiz().remoteDeleteTaskQuery(mSerialNo, taskId, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((RemoteDeleteTaskBean) object[0]);
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
