package com.Intelligent.FamilyU.model.download.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.download.biz.RemoteStartTaskBiz;
import com.Intelligent.FamilyU.model.download.entity.RemoteStartTaskBean;
import com.Intelligent.FamilyU.model.download.iface.IRemoteStartTaskView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 远程创建下载文件 Presenter
 *
 */

public class RemoteStartTaskPresenter extends BasePresenter<IRemoteStartTaskView, LifecycleProvider> {

    private final String TAG = RemoteStartTaskPresenter.class.getSimpleName();

    public RemoteStartTaskPresenter(IRemoteStartTaskView view, LifecycleProvider activity) {
        super(view, activity);
    }


    /**
     * 获取信息
     *
     */
    public void remoteStartTaskQuery(String mSerialNo, String taskId) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new RemoteStartTaskBiz().remoteStartTaskQuery(mSerialNo,taskId,getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((RemoteStartTaskBean) object[0]);
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
