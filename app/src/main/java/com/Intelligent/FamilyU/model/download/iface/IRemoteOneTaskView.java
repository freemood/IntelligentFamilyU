package com.Intelligent.FamilyU.model.download.iface;

import com.Intelligent.FamilyU.model.download.entity.RemoteOneTaskBean;
import com.Intelligent.FamilyU.view.IBaseView;

/**
 *
 */

public interface IRemoteOneTaskView extends IBaseView {

    //显示结果
    void showResult(RemoteOneTaskBean bean);

}
