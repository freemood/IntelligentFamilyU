package com.Intelligent.FamilyU.model.gateway.iface;

import com.Intelligent.FamilyU.model.gateway.entity.GatewayUntieBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface IGatewayUntieView extends IBaseView {

    //显示结果
    void showResult(GatewayUntieBean bean);
}
