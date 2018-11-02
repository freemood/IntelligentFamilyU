package com.Intelligent.FamilyU.model.login.iface;

import com.Intelligent.FamilyU.model.login.bean.LoginBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface ILoginForgetView extends IBaseView {
    //显示结果
    void showResult(LoginBean bean);
}
