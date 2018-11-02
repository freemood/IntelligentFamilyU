package com.Intelligent.FamilyU.model.download.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.download.biz.RemoteStopTaskBiz;
import com.Intelligent.FamilyU.model.download.entity.RemoteStopTaskBean;
import com.Intelligent.FamilyU.model.download.iface.IRemoteStopTaskView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 远程创建下载文件 Presenter
 */

public class RemoteStopTaskPresenter extends BasePresenter<IRemoteStopTaskView, LifecycleProvider> {

    private final String TAG = RemoteStopTaskPresenter.class.getSimpleName();

    public RemoteStopTaskPresenter(IRemoteStopTaskView view, LifecycleProvider activity) {
        super(view, activity);
    }


    /**
     * 获取信息
     */
    public void remoteStopTaskQuery(String mSerialNo, String taskId) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new RemoteStopTaskBiz().remoteStopTaskQuery(mSerialNo, taskId, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((RemoteStopTaskBean) object[0]);
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
