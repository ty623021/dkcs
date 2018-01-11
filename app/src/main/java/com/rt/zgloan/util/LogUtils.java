package com.rt.zgloan.util;


import android.content.Context;
import android.util.Log;

import com.rt.zgloan.BuildConfig;

/**
 * 如果用于android平台，将信息记录到“LogCat”。如果用于java平台，将信息记录到“Console”
 * 使用logger封装
 */
public class LogUtils {
    public static boolean DEBUG = BuildConfig.DEBUG;//是否调试模式,上线必须改为false

    /**
     * error日志
     *
     * @param tag
     * @param message
     */
    public static void loge(String tag, String message) {
        if (DEBUG) {
            Log.e(tag, message + "");
        }
    }

    /**
     * error日志
     *
     * @param context
     * @param message
     */
    public static void loge(Context context, String message) {
        if (DEBUG) {
            String tag = context.getClass().getSimpleName();
            loge(tag, message + "");
        }
    }
}
