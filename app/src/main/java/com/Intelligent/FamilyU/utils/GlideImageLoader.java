package com.Intelligent.FamilyU.utils;

import android.content.Context;
import android.widget.ImageView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.model.home.fragment.CornerTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.loader.ImageLoader;

import java.io.File;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        CornerTransform transformation = new CornerTransform(context, 15);
        //只是绘制左上角和右上角圆角

        transformation.setExceptCorner(false, false, false, false);
        Glide.with(context).
                load(path).
                apply(new RequestOptions().
                        error(R.mipmap.bitmap_banner).
                        placeholder(R.mipmap.bitmap_banner).
                        diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).
                        transform(new CornerTransform(context, 15))).
                into(imageView);
    }
}
