package com.Intelligent.FamilyU.model.my.iface;

import com.Intelligent.FamilyU.model.my.entity.ProductOrderListBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface IProductOrderView extends IBaseView {
    //显示结果
    void showResult(ProductOrderListBean bean);
}
