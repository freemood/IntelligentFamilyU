package com.Intelligent.FamilyU.model.login.iface;

import com.Intelligent.FamilyU.model.login.bean.LoginModifyBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface ILoginModifyView extends IBaseView {
    //显示结果
    void showResult(LoginModifyBean bean);
}
