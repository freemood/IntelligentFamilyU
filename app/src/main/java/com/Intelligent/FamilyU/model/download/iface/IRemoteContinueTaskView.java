package com.Intelligent.FamilyU.model.download.iface;

import com.Intelligent.FamilyU.model.download.entity.RemoteContinueTaskBean;
import com.Intelligent.FamilyU.view.IBaseView;

/**
 *
 */

public interface IRemoteContinueTaskView extends IBaseView {

    //显示结果
    void showResult(RemoteContinueTaskBean bean);

}
