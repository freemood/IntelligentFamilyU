package com.Intelligent.FamilyU.model.cloud.iface;


import android.widget.TextView;

import com.Intelligent.FamilyU.model.cloud.entity.CloudFileBean;

public interface IUpLoadCloud {
    void onUpdateView(TextView tv, CloudFileBean mCloudFileBean, long bytesWritten);
}
