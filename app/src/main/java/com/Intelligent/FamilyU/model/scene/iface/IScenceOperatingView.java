package com.Intelligent.FamilyU.model.scene.iface;

import com.Intelligent.FamilyU.model.scene.entity.SocketOpratingBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface IScenceOperatingView extends IBaseView {

    //显示结果
    void showResult(SocketOpratingBean bean);

}
