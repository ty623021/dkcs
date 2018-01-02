package com.rt.zgloan.activity.cityActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


/**
 * Android API本身提供的定位功能,也是GPS定位。
 * GPS定位，是基于卫星定位。它受环境影响很大。并且是单向定位，也就是只有你自己知道你的地理坐标。
 *
 * @author LuoSiye
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
@SuppressLint("NewApi")
public class LocationUtil {

    private static final String TAG = "LocationUtil";

    private static LocationUtil instance;
    private static Activity mActivity;
    private static LocationManager locationManager;
    private static LocationListener locationListener;

    public static LocationUtil getInstance(Activity activity) {
        mActivity = activity;
        if (instance == null) {
            instance = new LocationUtil();
        }
        locationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
        return instance;
    }

    /**
     * 判断GPS导航是否打开.
     * false：弹窗提示打开,不建议采用在后台强行开启的方式。
     * true:不做任何处理
     *
     * @return
     */
    public void isOpenGPS() {
        if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
            dialog.setMessage("GPS未打开，是否打开?");
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    // 设置完成后返回到原来的界面
                    mActivity.startActivityForResult(intent, 0);
                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    /**
     * 通过LocationListener来获取Location信息
     */
    public void formListenerGetLocation() {

        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                //位置信息变化时触发
                Log.i(TAG, "纬度：" + location.getLatitude());
                Log.i(TAG, "经度：" + location.getLongitude());
                Log.i(TAG, "海拔：" + location.getAltitude());
                Log.i(TAG, "时间：" + location.getTime());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                //GPS状态变化时触发
            }

            @Override
            public void onProviderEnabled(String provider) {
                //GPS禁用时触发
            }

            @Override
            public void onProviderDisabled(String provider) {
                //GPS开启时触发
            }
        };
        /**
         * 绑定监听
         * 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种，前者是GPS,后者是GPRS以及WIFI定位
         * 参数2，位置信息更新周期.单位是毫秒
         * 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
         * 参数4，监听
         * 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新
         */
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    /**
     * 主动获取Location，通过以下方法获取到的是最后一次定位信息。
     * 注意：Location location=new Location(LocationManager.GPS_PROVIDER)方式获取的location的各个参数值都是为0。
     */
    public Location getLocation() {
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.e(TAG, "纬度：" + location.getLatitude());
        Log.e(TAG, "经度：" + location.getLongitude());
        Log.e(TAG, "海拔：" + location.getAltitude());
        Log.e(TAG, "时间：" + location.getTime());
        return location;
    }

    /**
     * 获取GPS状态监听，包括GPS启动、停止、第一次定位、卫星变化等事件。
     */
    public void getStatusListener() {

        GpsStatus.Listener listener = new GpsStatus.Listener() {

            @Override
            public void onGpsStatusChanged(int event) {
                if (event == GpsStatus.GPS_EVENT_FIRST_FIX) {
                    //第一次定位
                } else if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
                    //卫星状态改变
                    GpsStatus gpsStauts = locationManager.getGpsStatus(null); // 取当前状态
                    int maxSatellites = gpsStauts.getMaxSatellites(); //获取卫星颗数的默认最大值
                    Iterator<GpsSatellite> it = gpsStauts.getSatellites().iterator();//创建一个迭代器保存所有卫星
                    int count = 0;
                    while (it.hasNext() && count <= maxSatellites) {
                        count++;
                        GpsSatellite s = it.next();
                    }
                    Log.i(TAG, "搜索到：" + count + "颗卫星");
                } else if (event == GpsStatus.GPS_EVENT_STARTED) {
                    //定位启动
                } else if (event == GpsStatus.GPS_EVENT_STOPPED) {
                    //定位结束
                }
            }
        };
        //绑定
        locationManager.addGpsStatusListener(listener);
    }

    /**
     * 获取所有卫星状态
     *
     * @return
     */
    public List<GpsSatellite> getGpsStatus() {
        List<GpsSatellite> result = new ArrayList<GpsSatellite>();
        GpsStatus gpsStatus = locationManager.getGpsStatus(null); // 取当前状态
        //获取默认最大卫星数
        int maxSatellites = gpsStatus.getMaxSatellites();
        //获取第一次定位时间（启动到第一次定位）
        int costTime = gpsStatus.getTimeToFirstFix();
        Log.i(TAG, "第一次定位时间:" + costTime);
        //获取卫星
        Iterable<GpsSatellite> iterable = gpsStatus.getSatellites();
        //一般再次转换成Iterator
        Iterator<GpsSatellite> itrator = iterable.iterator();
        int count = 0;
        while (itrator.hasNext() && count <= maxSatellites) {
            count++;
            GpsSatellite s = itrator.next();
            result.add(s);
        }
        return result;
    }

    /**
     * 某一个卫星的信息.
     *
     * @param gpssatellite
     */
    public void getGpsStatelliteInfo(GpsSatellite gpssatellite) {
        //卫星的方位角，浮点型数据
        Log.i(TAG, "卫星的方位角：" + gpssatellite.getAzimuth());
        //卫星的高度，浮点型数据
        Log.i(TAG, "卫星的高度：" + gpssatellite.getElevation());
        //卫星的伪随机噪声码，整形数据
        Log.i(TAG, "卫星的伪随机噪声码：" + gpssatellite.getPrn());
        //卫星的信噪比，浮点型数据
        Log.i(TAG, "卫星的信噪比：" + gpssatellite.getSnr());
        //卫星是否有年历表，布尔型数据
        Log.i(TAG, "卫星是否有年历表：" + gpssatellite.hasAlmanac());
        //卫星是否有星历表，布尔型数据
        Log.i(TAG, "卫星是否有星历表：" + gpssatellite.hasEphemeris());
        //卫星是否被用于近期的GPS修正计算
        Log.i(TAG, "卫星是否被用于近期的GPS修正计算：" + gpssatellite.hasAlmanac());
    }

}
