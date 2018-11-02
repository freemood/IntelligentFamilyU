package com.Intelligent.FamilyU.model.download.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.download.biz.RemoteBuildTaskBiz;
import com.Intelligent.FamilyU.model.download.biz.RemoteDirListBiz;
import com.Intelligent.FamilyU.model.download.entity.RemoteBuildTaskBean;
import com.Intelligent.FamilyU.model.download.entity.RemoteDirListBean;
import com.Intelligent.FamilyU.model.download.iface.IRemoteBuildTaskView;
import com.Intelligent.FamilyU.model.download.iface.IRemoteDirListView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 远程创建下载文件 Presenter
 *
 */

public class RemoteBuildTaskPresenter extends BasePresenter<IRemoteBuildTaskView, LifecycleProvider> {

    private final String TAG = RemoteBuildTaskPresenter.class.getSimpleName();

    public RemoteBuildTaskPresenter(IRemoteBuildTaskView view, LifecycleProvider activity) {
        super(view, activity);
    }


    /**
     * 获取信息
     *
     */
    public void remoteBuildTaskQuery(String mSerialNo, String fileName, String store, String url,String delayTime) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new RemoteBuildTaskBiz().remoteBuildTaskQuery(mSerialNo,fileName,store, url, delayTime,getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((RemoteBuildTaskBean) object[0]);
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
