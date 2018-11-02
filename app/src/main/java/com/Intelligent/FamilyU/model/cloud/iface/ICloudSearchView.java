package com.Intelligent.FamilyU.model.cloud.iface;

import com.Intelligent.FamilyU.model.cloud.entity.CloudSearchFileListBean;
import com.Intelligent.FamilyU.view.IBaseView;

/**
 *
 */

public interface ICloudSearchView extends IBaseView {

    //显示结果
    void showResult(CloudSearchFileListBean bean);

}
