package com.Intelligent.FamilyU.model.message.iface;

import com.Intelligent.FamilyU.model.message.entity.MessageAllReadBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface IMessageAllReadView extends IBaseView {

    //显示结果
    void showResult(MessageAllReadBean bean);

}
