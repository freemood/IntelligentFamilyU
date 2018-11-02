package com.Intelligent.FamilyU.model.cloud.iface;

import com.Intelligent.FamilyU.model.cloud.entity.CloudAddTokenBean;
import com.Intelligent.FamilyU.view.IBaseView;

/**
 *
 */

public interface ICloudAddTokenView extends IBaseView {

    //显示结果
    void showResult(CloudAddTokenBean bean);

}
