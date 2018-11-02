package com.Intelligent.FamilyU.model.scene.iface;

import com.Intelligent.FamilyU.model.scene.entity.HomeDeviceListBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface IHomeScenceView extends IBaseView {

    //显示结果
    void showResult(HomeDeviceListBean bean);

}
