package com.Intelligent.FamilyU.model.download.iface;

import com.Intelligent.FamilyU.model.download.entity.RemoteBuildTaskBean;
import com.Intelligent.FamilyU.view.IBaseView;

/**
 *
 */

public interface IRemoteBuildTaskView extends IBaseView {

    //显示结果
    void showResult(RemoteBuildTaskBean bean);
    //文件夹地址
    void getFileDirPath(String path);
}
