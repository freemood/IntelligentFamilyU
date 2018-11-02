package com.Intelligent.FamilyU.model.cloud.upload;

import android.widget.TextView;

import com.Intelligent.FamilyU.model.cloud.entity.CloudFileBean;
import com.Intelligent.FamilyU.model.cloud.iface.IUpLoadCloud;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class UploadFileRequestBody extends RequestBody {
    private RequestBody mRequestBody;
    private IUpLoadCloud mIUpLoadCloud;
    private TextView tv;
    private CloudFileBean mCloudFileBean;

    public UploadFileRequestBody(File file, IUpLoadCloud iUpLoadCloud, TextView tv, CloudFileBean mCloudFileBean) {
        mIUpLoadCloud = iUpLoadCloud;
        this.tv = tv;
        this.mCloudFileBean = mCloudFileBean;
        this.mRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
    }


    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

        CountingSink countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink);
        // 写入
        mRequestBody.writeTo(bufferedSink);
        // 刷新
        // 必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();

    }

    /**
     * CountingSink.
     */
    protected final class CountingSink extends ForwardingSink {

        private long bytesWritten = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);

            bytesWritten += byteCount;
            if (mIUpLoadCloud != null) {
                mIUpLoadCloud.onUpdateView(tv, mCloudFileBean, bytesWritten);
            }
        }

    }
}
