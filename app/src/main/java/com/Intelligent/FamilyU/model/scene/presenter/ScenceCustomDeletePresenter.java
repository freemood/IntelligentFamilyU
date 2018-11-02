package com.Intelligent.FamilyU.model.scene.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.scene.biz.ScenceCustonDeleteBiz;
import com.Intelligent.FamilyU.model.scene.biz.SocketModifyNameBiz;
import com.Intelligent.FamilyU.model.scene.entity.ScenceCustomDeleteBean;
import com.Intelligent.FamilyU.model.scene.entity.SocketModifyNameBean;
import com.Intelligent.FamilyU.model.scene.iface.IScenceCustomDeleteView;
import com.Intelligent.FamilyU.model.scene.iface.ISocketModifyNameView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 智能插座name presenter
 */

public class ScenceCustomDeletePresenter extends BasePresenter<IScenceCustomDeleteView, LifecycleProvider> {

    private final String TAG = ScenceCustomDeletePresenter.class.getSimpleName();

    public ScenceCustomDeletePresenter(IScenceCustomDeleteView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取信息
     */
    public void custonDelete(String serialNo, String pk) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new ScenceCustonDeleteBiz().custonDelete(serialNo, pk, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((ScenceCustomDeleteBean) object[0]);
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
