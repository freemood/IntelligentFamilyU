package com.Intelligent.FamilyU.model.message.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.message.biz.MessageAllDeleteBiz;
import com.Intelligent.FamilyU.model.message.entity.MessageAllDeleteBean;
import com.Intelligent.FamilyU.model.message.iface.IMessageAllDeleteView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class MessageAllDeletePresenter extends BasePresenter<IMessageAllDeleteView, LifecycleProvider> {

    private final String TAG = MessageAllDeletePresenter.class.getSimpleName();

    public MessageAllDeletePresenter(IMessageAllDeleteView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取信息
     *
     */
    public void allDeleteMessageList(String gatewaySn) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new MessageAllDeleteBiz().allDeleteMessageList(gatewaySn, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((MessageAllDeleteBean) object[0]);
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
