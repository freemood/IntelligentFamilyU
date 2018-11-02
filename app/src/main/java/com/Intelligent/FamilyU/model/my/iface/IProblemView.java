package com.Intelligent.FamilyU.model.my.iface;

import com.Intelligent.FamilyU.model.my.entity.ProblemReportBean;
import com.Intelligent.FamilyU.view.IBaseView;

public interface IProblemView extends IBaseView {
    //显示结果
    void showResult(ProblemReportBean bean);
}
