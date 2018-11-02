package com.Intelligent.FamilyU.model.login.iface;

import com.Intelligent.FamilyU.view.IBaseView;
import com.Intelligent.FamilyU.model.login.bean.LoginBean;

public interface ILoginView extends IBaseView {
    //显示结果
    void showResult(LoginBean bean);
    void showErrorResult(int code);
}
