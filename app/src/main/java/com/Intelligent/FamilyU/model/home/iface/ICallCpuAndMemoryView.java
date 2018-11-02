package com.Intelligent.FamilyU.model.home.iface;

import com.Intelligent.FamilyU.model.home.entity.CpuAndMemoryBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface ICallCpuAndMemoryView extends IBaseView {
    void callGatewaySerialNo(String serialNo,String pluginId);

    //显示结果
    void showResult(CpuAndMemoryBean bean);
}
