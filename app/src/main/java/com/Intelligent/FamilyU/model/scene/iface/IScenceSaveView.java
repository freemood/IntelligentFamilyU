package com.Intelligent.FamilyU.model.scene.iface;

import com.Intelligent.FamilyU.model.scene.entity.ScenceSaveBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface IScenceSaveView extends IBaseView {
    //显示结果
    void showResult(ScenceSaveBean bean);
}
