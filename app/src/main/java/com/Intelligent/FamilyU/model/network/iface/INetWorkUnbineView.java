package com.Intelligent.FamilyU.model.network.iface;

import com.Intelligent.FamilyU.model.network.bean.NetWorkUnbindBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface INetWorkUnbineView extends IBaseView {

    //显示结果
    void showResult(NetWorkUnbindBean bean);
}
