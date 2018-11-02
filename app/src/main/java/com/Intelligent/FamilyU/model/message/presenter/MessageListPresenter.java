package com.Intelligent.FamilyU.model.message.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.message.biz.MessageListBiz;
import com.Intelligent.FamilyU.model.message.entity.MessageListBean;
import com.Intelligent.FamilyU.model.message.iface.IMessageListView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class MessageListPresenter extends BasePresenter<IMessageListView, LifecycleProvider> {

    private final String TAG = MessageListPresenter.class.getSimpleName();

    public MessageListPresenter(IMessageListView view, LifecycleProvider activity) {
        super(view, activity);
    }


    /**
     * 获取信息
     *
     */
    public void messageListQuery(String gatewaySn) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new MessageListBiz().messageListQuery(gatewaySn, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((MessageListBean) object[0]);
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
