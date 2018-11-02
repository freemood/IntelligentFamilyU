package com.Intelligent.FamilyU.model.scene.iface;

import com.Intelligent.FamilyU.model.scene.entity.DeviceOperationListBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface IScenceOperationView extends IBaseView {

    //显示结果
    void showResult(DeviceOperationListBean bean);

}
