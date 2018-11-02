package com.Intelligent.FamilyU.model.plugin.iface;

import com.Intelligent.FamilyU.model.plugin.bean.PluginListBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface IPluginListView extends IBaseView {
    //显示结果
    void showResult(PluginListBean bean);
}
