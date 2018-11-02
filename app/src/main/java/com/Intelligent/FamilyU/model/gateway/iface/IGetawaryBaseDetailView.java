package com.Intelligent.FamilyU.model.gateway.iface;

import com.Intelligent.FamilyU.model.gateway.entity.DeviceGateWayBean;
import com.Intelligent.FamilyU.view.IBaseView;

/**
 * 綁定网关view接口
 *
 */

public interface IGetawaryBaseDetailView extends IBaseView {
    //显示结果
    void showResult(DeviceGateWayBean bean);
}
