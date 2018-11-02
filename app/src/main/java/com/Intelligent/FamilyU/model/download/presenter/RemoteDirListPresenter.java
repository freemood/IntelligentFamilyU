package com.Intelligent.FamilyU.model.download.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.download.biz.RemoteDirListBiz;
import com.Intelligent.FamilyU.model.download.entity.RemoteDirListBean;
import com.Intelligent.FamilyU.model.download.iface.IRemoteDirListView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 远程下载文件夹 Presenter
 *
 */

public class RemoteDirListPresenter extends BasePresenter<IRemoteDirListView, LifecycleProvider> {

    private final String TAG = RemoteDirListPresenter.class.getSimpleName();

    public RemoteDirListPresenter(IRemoteDirListView view, LifecycleProvider activity) {
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
        new RemoteDirListBiz().remoteListQuery(mSerialNo, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((RemoteDirListBean) object[0]);
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
