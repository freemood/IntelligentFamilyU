package com.Intelligent.FamilyU.model.scene.iface;

import com.Intelligent.FamilyU.model.scene.entity.SocketModifyNameBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface ISocketModifyNameView extends IBaseView {

    //显示结果
    void showResult(SocketModifyNameBean bean);
}
