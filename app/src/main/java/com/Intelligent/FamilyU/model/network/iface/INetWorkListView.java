package com.Intelligent.FamilyU.model.network.iface;

import com.Intelligent.FamilyU.model.network.bean.NetWorkListBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface INetWorkListView extends IBaseView {
    //显示结果
    void showResult(NetWorkListBean bean);
}
