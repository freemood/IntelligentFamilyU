package com.Intelligent.FamilyU.model.download.iface;

import com.Intelligent.FamilyU.model.download.entity.RemoteDeleteTaskBean;
import com.Intelligent.FamilyU.view.IBaseView;

/**
 *
 */

public interface IRemoteDeleteTaskView extends IBaseView {

    //显示结果
    void showResult(RemoteDeleteTaskBean bean);

}
