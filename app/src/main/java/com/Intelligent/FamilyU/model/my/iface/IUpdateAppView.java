package com.Intelligent.FamilyU.model.my.iface;

import com.Intelligent.FamilyU.model.my.entity.UpdateAppBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface IUpdateAppView extends IBaseView {
    //显示结果
    void showResult(UpdateAppBean bean);
}
