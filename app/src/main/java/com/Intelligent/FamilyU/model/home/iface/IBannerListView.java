package com.Intelligent.FamilyU.model.home.iface;

import com.Intelligent.FamilyU.model.home.entity.BannerListBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface IBannerListView extends IBaseView {
    //显示结果
    void showResult(BannerListBean bean);
}
