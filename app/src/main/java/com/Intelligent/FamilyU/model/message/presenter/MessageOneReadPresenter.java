package com.Intelligent.FamilyU.model.message.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.message.biz.MessageAllReadBiz;
import com.Intelligent.FamilyU.model.message.biz.MessageOneReadBiz;
import com.Intelligent.FamilyU.model.message.entity.MessageAllReadBean;
import com.Intelligent.FamilyU.model.message.entity.MessageOneReadBean;
import com.Intelligent.FamilyU.model.message.iface.IMessageAllReadView;
import com.Intelligent.FamilyU.model.message.iface.IMessageOneReadView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class MessageOneReadPresenter extends BasePresenter<IMessageOneReadView, LifecycleProvider> {

    private final String TAG = MessageOneReadPresenter.class.getSimpleName();

    public MessageOneReadPresenter(IMessageOneReadView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取信息
     *
     */
    public void readMessageList(String id) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new MessageOneReadBiz().readMessageList(id, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((MessageOneReadBean) object[0]);
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
