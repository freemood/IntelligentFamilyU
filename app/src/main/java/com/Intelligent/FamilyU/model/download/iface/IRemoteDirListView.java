package com.Intelligent.FamilyU.model.download.iface;

import com.Intelligent.FamilyU.model.download.entity.RemoteDirListBean;
import com.Intelligent.FamilyU.view.IBaseView;

/**
 *
 */

public interface IRemoteDirListView extends IBaseView {

    //显示结果
    void showResult(RemoteDirListBean bean);

}
