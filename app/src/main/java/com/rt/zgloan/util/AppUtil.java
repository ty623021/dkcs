package com.rt.zgloan.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Xml;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import org.xmlpull.v1.XmlPullParser;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by geek on 2016/6/21.
 */
public class AppUtil {


    /**
     * 判断是否是大于19（5.0以上的系统）
     *
     * @return
     */
    public static boolean isVersionKitkat() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT;
    }

    public static void getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 15) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
//            localIntent.setData(Uri.fromParts("package", MaApplication.getMaApplication().getPackageName(), null));
        }
//        MaApplication.getMaApplication().startActivity(localIntent);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public static void backgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }


    //隐藏虚拟键盘
    public static void HideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    /**
     * 获取手机的详细信息
     *
     * @return
     */
    public static String getDevice() {
        return "品牌: " + Build.BRAND + "  型号: " + Build.MODEL + " Android版本: " + Build.VERSION.RELEASE;
    }

    /**
     * 获取屏幕分辨率
     *
     * @param context
     * @return
     */
    public static int[] getScreenDispaly(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        int width = windowManager.getDefaultDisplay().getWidth();
        @SuppressWarnings("deprecation")
        int height = windowManager.getDefaultDisplay().getHeight();
        int result[] = {width, height};
        return result;
    }

    //OOM出现 解决办法
    public static Bitmap createBitmap(int width, int height, Bitmap.Config config) {
        Bitmap bitmap = null;
        try {
            bitmap = Bitmap.createBitmap(width, height, config);
        } catch (OutOfMemoryError e) {
            while (bitmap == null) {
                System.gc();
                System.runFinalization();
                bitmap = createBitmap(width, height, config);
            }
        }
        return bitmap;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return yinsujun 2015-8-20 下午5:34:18
     */
//    public static int getWindowWith(Context context) {
////        WindowManager windowManager = (WindowManager) MaApplication.getMaApplication().getSystemService(Context.WINDOW_SERVICE);
//        @SuppressWarnings("deprecation")
////        int width = windowManager.getDefaultDisplay().getWidth();
//        return width;
//    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return yinsujun 2015-8-20 下午5:34:18
     */
//    public static int getWindowHeight(Context context) {
////        WindowManager windowManager = (WindowManager) MaApplication.getMaApplication().getSystemService(Context.WINDOW_SERVICE);
//        @SuppressWarnings("deprecation")
//        int height = windowManager.getDefaultDisplay().getHeight();
//        return height;
//    }

    /**
     * 获取屏幕尺寸与密度.
     *
     * @param context the context
     * @return mDisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        Resources mResources;
        if (context == null) {
            mResources = Resources.getSystem();

        } else {
            mResources = context.getResources();
        }
        //DisplayMetrics{density=1.5, width=480, height=854, scaledDensity=1.5, xdpi=160.421, ydpi=159.497}
        //DisplayMetrics{density=2.0, width=720, height=1280, scaledDensity=2.0, xdpi=160.42105, ydpi=160.15764}
        DisplayMetrics mDisplayMetrics = mResources.getDisplayMetrics();
        return mDisplayMetrics;
    }


    /**
     * 获取包信息.
     *
     * @param context the context
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            String packageName = context.getPackageName();
            info = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 版本号
     *
     * @param con
     * @return
     */
    public static int getAppVersionCode(Context con) {
        try {
            PackageInfo pkgInfo = con.getPackageManager().getPackageInfo(
                    con.getPackageName(), 0);
            if (pkgInfo != null) {
                return pkgInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 版本名称
     *
     * @param con
     * @return
     */
    public static String getAppVersionName(Context con) {
        try {
            PackageInfo pkgInfo = con.getPackageManager().getPackageInfo(
                    con.getPackageName(), 0);
            if (pkgInfo != null) {
                return pkgInfo.versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取IMSI.
     *
     * @return
     */
    public static String getIMSI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getSubscriberId() == null) {
            return null;
        } else {
            return telephonyManager.getSubscriberId();
        }
    }

    /**
     * 获取IMEI.
     *
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getDeviceId() == null) {
            return null;
        } else {
            return telephonyManager.getDeviceId();
        }
    }


    /**
     * 获取QQ号.
     *
     * @return
     */
    public static String getQQNumber(Context context) {
        String path = "/data/data/com.tencent.mobileqq/shared_prefs/Last_Login.xml";
        getRootPermission(context);
        File file = new File(path);
        getRootPermission(path);
        boolean flag = file.canRead();
        String qq = null;
        if (flag) {
            try {
                FileInputStream is = new FileInputStream(file);
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(is, "UTF-8");
                int event = parser.getEventType();
                while (event != XmlPullParser.END_DOCUMENT) {

                    switch (event) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            if ("map".equals(parser.getName())) {
                            }
                            if ("string".equals(parser.getName())) {
                                String uin = parser.getAttributeValue(null, "name");
                                if (uin.equals("uin")) {
                                    qq = parser.nextText();
                                    return qq;
                                }
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                    }
                    event = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取WEIXIN号.
     *
     * @return
     */
    public static String getWeiXinNumber(Context context) {
        String path = "/data/data/com.tencent.mm/shared_prefs/com.tencent.mm_preferences.xml";
        getRootPermission(context);
        File file = new File(path);
        getRootPermission(path);
        boolean flag = file.canRead();
        String weixin = null;
        if (flag) {
            try {
                FileInputStream is = new FileInputStream(file);
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(is, "UTF-8");
                int event = parser.getEventType();
                while (event != XmlPullParser.END_DOCUMENT) {

                    switch (event) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            if ("map".equals(parser.getName())) {
                            }
                            if ("string".equals(parser.getName())) {
                                String nameString = parser.getAttributeValue(null, "name");
                                if (nameString.equals("login_user_name")) {
                                    weixin = parser.nextText();
                                    return weixin;
                                }
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                    }
                    event = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
     *
     * @return 应用程序是/否获取Root权限
     */
    public static boolean getRootPermission(Context context) {
        String path = context.getPackageCodePath();
        return getRootPermission(path);
    }

    /**
     * 修改文件权限
     *
     * @return 文件路径
     */
    public static boolean getRootPermission(String path) {
        Process process = null;
        DataOutputStream os = null;
        try {
            File file = new File(path);
            if (!file.exists()) {
                return false;
            }
            String cmd = "chmod 777 " + path;
            // 切换到root帐号
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    /**
     * 拨打电话
     *
     * @param context
     * @param mobile
     */
    public static void call(Context context, String mobile) {
        if (mobile != null) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + mobile));
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            }
        }
    }


    /**
     * 判断微信是否安装
     *
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }
}
