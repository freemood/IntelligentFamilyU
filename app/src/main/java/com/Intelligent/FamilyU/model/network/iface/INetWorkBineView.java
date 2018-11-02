package com.Intelligent.FamilyU.model.network.iface;

import com.Intelligent.FamilyU.model.network.bean.NetWorkBindBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface INetWorkBineView extends IBaseView {

    //显示结果
    void showResult(NetWorkBindBean bean);
}
