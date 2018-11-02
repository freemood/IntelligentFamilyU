package com.Intelligent.FamilyU.model.plugin.iface;

import com.Intelligent.FamilyU.model.plugin.bean.PluginListPageBean;
import com.Intelligent.FamilyU.model.plugin.bean.PluginPageEntity;
import com.Intelligent.FamilyU.view.IBaseView;

import java.util.List;

public interface IPluginListPageView extends IBaseView {
    //显示结果
    void showResult(List<PluginPageEntity.PluginGatewayInfo> bean);
}
