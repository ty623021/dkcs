package com.rt.zgloan.viewPager;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.rt.zgloan.R;
import com.rt.zgloan.util.AbImageUtil;


/**
 * ImageView创建工厂
 */
public class ViewFactory {

    /**
     * 获取ImageView视图的同时加载显示url
     *
     * @return
     */
    public static ImageView getImageView(Context context, String url) {
        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(
                R.layout.view_banner, null);
        AbImageUtil.glideImageList(url, imageView, R.mipmap.banner, R.mipmap.banner);
        return imageView;
    }
}
