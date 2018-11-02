package com.Intelligent.FamilyU.model.scene.iface;

import com.Intelligent.FamilyU.model.scene.entity.ScenceDefultListBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface IHomeCustomScenceView extends IBaseView {

    //显示结果
    void showResult(ScenceDefultListBean bean);

}
