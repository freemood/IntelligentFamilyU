package com.Intelligent.FamilyU.model.message.iface;

import com.Intelligent.FamilyU.model.message.entity.MessageOneReadBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface IMessageOneReadView extends IBaseView {

    //显示结果
    void showResult(MessageOneReadBean bean);

}
