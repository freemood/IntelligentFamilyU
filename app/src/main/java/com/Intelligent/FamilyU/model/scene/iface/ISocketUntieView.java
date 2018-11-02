package com.Intelligent.FamilyU.model.scene.iface;

import com.Intelligent.FamilyU.model.scene.entity.ScoketRestartBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface ISocketUntieView extends IBaseView {
    //显示结果
    void showResult(ScoketRestartBean bean);
}
