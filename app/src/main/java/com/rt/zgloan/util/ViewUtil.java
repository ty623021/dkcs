package com.rt.zgloan.util;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.rt.zgloan.R;

import java.text.SimpleDateFormat;

public class ViewUtil {

	public static final String LOADING_TIP = "正在加载...";

	/*********
	 * 获取屏幕宽度
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context)
	{

	   DisplayMetrics metric = context.getResources().getDisplayMetrics();
	   int width = metric.widthPixels;     // 屏幕宽度（像素）
	   return width;
	}

	/***********
	 * 获取屏幕高度
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context)
	{
		DisplayMetrics metric = context.getResources().getDisplayMetrics();
	    int height = metric.heightPixels;     // 屏幕高度（像素）
	    return height;
	}

	/******
	 * 获取状态栏高度
	 * @param context
	 * @return
	 */
	public static int getStatusBarH(Context context)
	{
		int statusHeight = -1;
	    try {
	        Class<?> clazz = Class.forName("com.android.internal.R$dimen");
	        Object object = clazz.newInstance();
	        int height = Integer.parseInt(clazz.getField("status_bar_height")
	                .get(object).toString());
	        statusHeight = context.getResources().getDimensionPixelSize(height);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return statusHeight;
	}

	/*************
	 * 获取屏幕宽跟高
	 * @param context
	 * @return
	 */
	public static int[] getScreenExtent(Context context)
	{
		int[] buf = new int[2];
		DisplayMetrics metric = context.getResources().getDisplayMetrics();
	    int height = metric.heightPixels;     // 屏幕高度（像素）
	    int width = metric.widthPixels;     // 屏幕宽度（像素）
	    buf[0] = width;
	    buf[1] = height;
		return buf;
	}


	//获取当前app的版本名
	public static String getAppVersion(Context context){
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packInfo;
			packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}
	//获取当前app的版本号
	public static int getAppVersionCode(Context context){
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packInfo;
			packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}
	//获取当前设备ID(设备号为空时使用AndroidId)
	public static String getDeviceId(Context context){
		try {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if (tm==null||tm.getDeviceId()==null){
				return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
			}else {
				return tm.getDeviceId();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
		}
	}

	//获取当前设备ID
	public static String getInstalledTime(Context context){
		String installedTime = "";
		PackageManager packageManager = context.getPackageManager();
		try
		{
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long firstInstallTime = packageInfo.firstInstallTime;//应用第一次安装的时间
			installedTime = sdf.format(firstInstallTime);
		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return installedTime;
	}

	//获取当前设备信息
	public static String getDeviceName(){
		try {
			return Build.MODEL;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	//获取当前系统的版本号
	public static String getOsVersion(){
		try {
			return Build.VERSION.RELEASE;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/*********
	 * 获取当前网络信息
	 * @return
	 */
	public static String getNetworkType(Context context)
	{
	    String strNetworkType = "";

	    NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
	    if (networkInfo != null && networkInfo.isConnected())
	    {
	        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
	        {
	            strNetworkType = "WIFI";
	        }
	        else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
	        {
	            String _strSubTypeName = networkInfo.getSubtypeName();



	            // TD-SCDMA   networkType is 17
	            int networkType = networkInfo.getSubtype();
	            switch (networkType) {
	                case TelephonyManager.NETWORK_TYPE_GPRS:
	                case TelephonyManager.NETWORK_TYPE_EDGE:
	                case TelephonyManager.NETWORK_TYPE_CDMA:
	                case TelephonyManager.NETWORK_TYPE_1xRTT:
	                case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
	                    strNetworkType = "2G";
	                    break;
	                case TelephonyManager.NETWORK_TYPE_UMTS:
	                case TelephonyManager.NETWORK_TYPE_EVDO_0:
	                case TelephonyManager.NETWORK_TYPE_EVDO_A:
	                case TelephonyManager.NETWORK_TYPE_HSDPA:
	                case TelephonyManager.NETWORK_TYPE_HSUPA:
	                case TelephonyManager.NETWORK_TYPE_HSPA:
	                case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
	                case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
	                case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
	                    strNetworkType = "3G";
	                    break;
	                case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
	                    strNetworkType = "4G";
	                    break;
	                default:
	                    // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
	                    if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000"))
	                    {
	                        strNetworkType = "3G";
	                    }
	                    else
	                    {
	                        strNetworkType = _strSubTypeName;
	                    }

	                    break;
	             }

	        }
	    }


	    return strNetworkType;
	}


	/************
	 * Toast设置
	 * @param message
	 */
	public static void showToast(String message)
	{
		ToastUtil.showToast(message);
	}

	/*********
	 * 获取当前进程名称
	 * @param context
	 * @return
	 */
	public static String getCurProcessName(Context context) {
		  int pid = android.os.Process.myPid();

		  ActivityManager mActivityManager = (ActivityManager) context
		    .getSystemService(Context.ACTIVITY_SERVICE);

		  for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
		    .getRunningAppProcesses()) {
		   if (appProcess.pid == pid) {

			   return appProcess.processName;
		   }
		  }
		  return null;
	 }

	/**************
	 * 获取sdk路径
	 * @return
	 */
	public static String getSdCardPath()
	{
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}


	/**
     * 还原View的触摸和点击响应范围,最小不小于View自身范围
     *
     * @param view
     */
    public static void restoreViewTouchDelegate(final View view) {

        ((View) view.getParent()).post(new Runnable() {
            @Override
            public void run() {
                Rect bounds = new Rect();
                bounds.setEmpty();
                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });
    }

    /**
     * 扩大View的触摸和点击响应范围,最大不超过其父View范围
     *
     * @param view
     * @param top
     * @param bottom
     * @param left
     * @param right
     */
    public static void expandViewTouchDelegate(final View view, final int top,
											   final int bottom, final int left, final int right) {

        ((View) view.getParent()).post(new Runnable() {
            @Override
            public void run() {
                Rect bounds = new Rect();
                view.setEnabled(true);
                view.getHitRect(bounds);

                bounds.top -= top;
                bounds.bottom += bottom;
                bounds.left -= left;
                bounds.right += right;

                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });
    }


  //自定义内容加载提示窗
    private static AlertDialog loadingDialog;
    private static View loadingView;
  	public static void showLoading(Activity activity, String content)
  	{
  		hideLoading();
  		loadingDialog = new AlertDialog.Builder(activity, R.style.alert_dialog).create();
  		loadingDialog.setCancelable(false);
//		loadingDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
  		if(!activity.isFinishing())
  			loadingDialog.show();
//		hideSystemUI(loadingDialog.getWindow().getDecorView());
//		loadingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
  		Window window = loadingDialog.getWindow();
  		if(loadingView!=null){
  			window.setContentView(R.layout.layout_loading);
  			LinearLayout view = (LinearLayout) window.findViewById(R.id.loading_content);
  			view.addView(loadingView);
  		}else{
  			window.setContentView(R.layout.layout_dialog_progress);
  			//适配大小
  	  		int screenWidth = getScreenWidth(activity);
  	  		TextView contentView = (TextView) window.findViewById(R.id.contentView);
  	  		contentView.setText(content);
  		}
  	}

	public static void hideSystemUI(View view) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		}
	}

  	public static void setLoadingView(Context context, View view){
  		loadingView = view;
  	}

  	/*******
  	 * 关闭loading
  	 */
    public static void hideLoading()
    {
    	if(loadingDialog!=null)
        {
        	if(loadingDialog.isShowing()){
				try {
					loadingDialog.dismiss();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
        	if(loadingView != null){
    			((LinearLayout) (loadingDialog.getWindow().findViewById(R.id.loading_content))).removeAllViews();
    		}
        }
    	loadingDialog = null;
    }

    /*
     * 计算ListView内容高度
     */
    public static void setListViewHeight(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		int height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		params.height = height+listView.getPaddingTop()+listView.getPaddingBottom();
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}
}
