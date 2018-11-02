package com.Intelligent.FamilyU.model.cloud.iface;


import android.widget.TextView;

import com.Intelligent.FamilyU.model.cloud.entity.CloudFileBean;

import okhttp3.ResponseBody;
import retrofit2.Response;

public interface IDownLoadCloud {
    void onSuccessful(Response<ResponseBody> response, int position, final TextView tv);

    void onDownLoadFailful();

    void onFailure();

    void onUpdateView(TextView tv, CloudFileBean mCloudFileBean);
}
