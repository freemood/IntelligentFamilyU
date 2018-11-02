package com.Intelligent.FamilyU.model.gateway.iface;

import com.Intelligent.FamilyU.model.gateway.entity.GatewayRestartBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface IGatewayRestartView extends IBaseView {

    //显示结果
    void showResult(GatewayRestartBean bean);
}
