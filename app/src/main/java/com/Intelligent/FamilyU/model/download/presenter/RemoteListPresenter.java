package com.Intelligent.FamilyU.model.download.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.download.biz.RemoteListBiz;
import com.Intelligent.FamilyU.model.download.entity.RemoteListBean;
import com.Intelligent.FamilyU.model.download.iface.IRemoteListView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 远程下载Presenter
 *
 */

public class RemoteListPresenter extends BasePresenter<IRemoteListView, LifecycleProvider> {

    private final String TAG = RemoteListPresenter.class.getSimpleName();

    public RemoteListPresenter(IRemoteListView view, LifecycleProvider activity) {
        super(view, activity);
    }


    /**
     * 获取信息
     *
     */
    public void remoteListQuery(String mSerialNo) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new RemoteListBiz().remoteListQuery(mSerialNo, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((RemoteListBean) object[0]);
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
