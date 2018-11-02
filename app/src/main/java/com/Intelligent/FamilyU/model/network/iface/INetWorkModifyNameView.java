package com.Intelligent.FamilyU.model.network.iface;

import com.Intelligent.FamilyU.model.network.bean.NetWorkModifyNameBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface INetWorkModifyNameView extends IBaseView {

    //显示结果
    void showResult(NetWorkModifyNameBean bean);
}
