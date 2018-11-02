package com.Intelligent.FamilyU.model.gateway.iface;

import com.Intelligent.FamilyU.model.gateway.entity.GatewayModifyNameBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface IGatewayModifyNameView extends IBaseView {

    //显示结果
    void showResult(GatewayModifyNameBean bean);
}
