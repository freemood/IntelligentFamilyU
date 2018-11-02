package com.Intelligent.FamilyU.model.gateway.iface;

import com.Intelligent.FamilyU.model.gateway.entity.GetawaryAddBean;
import com.Intelligent.FamilyU.view.IBaseView;

/**
 * 綁定网关view接口
 *
 */

public interface IGetawaryAddView extends IBaseView {

    //显示结果
    void showResult(GetawaryAddBean bean);

}
