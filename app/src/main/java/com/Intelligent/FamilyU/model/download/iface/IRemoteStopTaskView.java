package com.Intelligent.FamilyU.model.download.iface;

import com.Intelligent.FamilyU.model.download.entity.RemoteStopTaskBean;
import com.Intelligent.FamilyU.view.IBaseView;

/**
 *
 */

public interface IRemoteStopTaskView extends IBaseView {

    //显示结果
    void showResult(RemoteStopTaskBean bean);

}
