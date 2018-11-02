package com.Intelligent.FamilyU.model.gateway.iface;

import com.Intelligent.FamilyU.model.gateway.entity.GatewayWifiInfoBean;
import com.Intelligent.FamilyU.view.IBaseView;

/**
 * 綁定网关view接口
 *
 */

public interface IGetawaryWifiInfoView extends IBaseView {
    //显示结果
    void showResult(GatewayWifiInfoBean bean);
}
