package com.Intelligent.FamilyU.model.message.iface;

import com.Intelligent.FamilyU.model.message.entity.MessageAllDeleteBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface IMessageAllDeleteView extends IBaseView {

    //显示结果
    void showResult(MessageAllDeleteBean bean);

}
