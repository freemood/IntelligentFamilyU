package com.Intelligent.FamilyU.model.cloud.iface;

import com.Intelligent.FamilyU.model.cloud.entity.CloudDeleteTaskBean;
import com.Intelligent.FamilyU.view.IBaseView;

/**
 *
 */

public interface ICloudDeleteView extends IBaseView {

    //显示结果
    void showResult(CloudDeleteTaskBean bean);

}
