package com.Intelligent.FamilyU.model.message.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.message.biz.MessageAllReadBiz;
import com.Intelligent.FamilyU.model.message.entity.MessageAllReadBean;
import com.Intelligent.FamilyU.model.message.iface.IMessageAllReadView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class MessageAllReadPresenter extends BasePresenter<IMessageAllReadView, LifecycleProvider> {

    private final String TAG = MessageAllReadPresenter.class.getSimpleName();

    public MessageAllReadPresenter(IMessageAllReadView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取信息
     *
     */
    public void allReadMessageList(String gatewaySn) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new MessageAllReadBiz().allReadMessageList(gatewaySn, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((MessageAllReadBean) object[0]);
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
