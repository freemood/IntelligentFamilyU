package com.Intelligent.FamilyU.model.message.iface;

import com.Intelligent.FamilyU.view.IBaseView;
import com.Intelligent.FamilyU.model.message.entity.MessageListBean;

public interface IMessageListView extends IBaseView {

    //显示结果
    void showResult(MessageListBean bean);

}
