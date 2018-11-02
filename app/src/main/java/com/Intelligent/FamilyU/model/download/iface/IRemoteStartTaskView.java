package com.Intelligent.FamilyU.model.download.iface;

import com.Intelligent.FamilyU.model.download.entity.RemoteStartTaskBean;
import com.Intelligent.FamilyU.view.IBaseView;

/**
 *
 */

public interface IRemoteStartTaskView extends IBaseView {

    //显示结果
    void showResult(RemoteStartTaskBean bean);

}
