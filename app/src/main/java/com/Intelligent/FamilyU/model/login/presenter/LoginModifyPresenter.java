package com.Intelligent.FamilyU.model.login.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.login.bean.LoginModifyBean;
import com.Intelligent.FamilyU.model.login.bean.UserResetPwdInfo;
import com.Intelligent.FamilyU.model.login.biz.LoginModifyBiz;
import com.Intelligent.FamilyU.model.login.iface.ILoginModifyView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class LoginModifyPresenter extends BasePresenter<ILoginModifyView, LifecycleProvider> {
    private final String TAG = LoginModifyPresenter.class.getSimpleName();

    public LoginModifyPresenter(ILoginModifyView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 登录
     */
    public void startLoginModifyPassword(UserResetPwdInfo userResetPwdInfo) {

        if (getView() != null)
            getView().showLoading();


        HttpRxCallback httpRxCallback = new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((LoginModifyBean) object[0]);
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
        };

        new LoginModifyBiz().startLoginModifyParm(userResetPwdInfo, getActivity(), httpRxCallback);
    }

}
