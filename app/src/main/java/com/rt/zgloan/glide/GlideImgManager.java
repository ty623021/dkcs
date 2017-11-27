package com.rt.zgloan.glide;

/**
 * Created by hjy on 2017/3/13.
 */

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.rt.zgloan.app.App;


/**
 * Created by QLY on 2016/6/22.
 */
public class GlideImgManager {

    /**
     * load normal  for  circle or round img
     *
     * @param url
     * @param errorImg
     * @param emptyImg
     * @param iv
     * @param tag
     */
    public void glideLoader(String url, int errorImg, int emptyImg, ImageView iv, int tag) {
        if (0 == tag) {
            Glide.with(App.getContext())
                    .load(url)
                    .placeholder(emptyImg)
                    .error(errorImg)
                    .transform(new GlideCircleTransform(App.getContext()))
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                            if (onGlideSuccessListener != null) {
                                onGlideSuccessListener.onGlideSuccessListener();
                            }
                            return false;
                        }
                    })
                    .into(iv);
        } else if (1 == tag) {
            Glide.with(App.getContext())
                    .load(url).placeholder(emptyImg)
                    .error(errorImg)
                    .transform(new GlideRoundTransform(App.getContext(), 10))
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                            if (onGlideSuccessListener != null) {
                                onGlideSuccessListener.onGlideSuccessListener();
                            }
                            return false;
                        }
                    })
                    .into(iv);
        }
    }

    private OnGlideSuccessListener onGlideSuccessListener;

    public void setOnGlideSuccessListener(OnGlideSuccessListener onGlideSuccessListener) {
        this.onGlideSuccessListener = onGlideSuccessListener;
    }

    public interface OnGlideSuccessListener {
        void onGlideSuccessListener();
    }
}