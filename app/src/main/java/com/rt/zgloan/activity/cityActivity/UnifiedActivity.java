package com.rt.zgloan.activity.cityActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.app.App;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.base.PermissionsListener;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.bean.CreditCardAddressBean;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.util.AbStringUtil;
import com.rt.zgloan.util.AppUtil;
import com.rt.zgloan.util.SpUtil;
import com.rt.zgloan.weight.AlertFragmentDialog;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;

/**
 * 选择城市
 */
public class UnifiedActivity extends BaseActivity<CityBean> implements View.OnClickListener, UnifiedClickListener {


    @BindView(R.id.layout_height_top)
    RelativeLayout layoutHeightTop;
    @BindView(R.id.select_city_ic_back)
    ImageView select_city_ic_back;
    @BindView(R.id.select_city_et_search)
    EditText select_city_et_search;
    @BindView(R.id.select_city_ic_clean)
    ImageView select_city_ic_clean;
    @BindView(R.id.select_city_lv_search_result)
    ListView select_city_lv_search_result;
    @BindView(R.id.select_city_lv_city_list)
    ListView select_city_lv_city_list;
    @BindView(R.id.select_city_lv_flag)
    ListView select_city_lv_flag;
    @BindView(R.id.select_city_tv_search_empty)
    TextView select_city_tv_search_empty;

    private List<UnifiedBase> all_unified = new ArrayList<>();
    private List<UnifiedBase> history = new ArrayList<>();
    private List<UnifiedBase> search = new ArrayList<>();
    private List<String> litter = new ArrayList<>();
    private UnifiedListAdapter list_adapter;
    private UnifiedFlagAdapter flag_adapter;
    private UnifiedSearchAdapter search_adapter;
    private LocationManager locationManager;
    private int cityId;//定位的城市ID
    private static final int HANDLER_MSG_LOCATION_FAIL = 5;//定位失败
    private static final int HANDLER_MSG_READ_DATA_SUCCESS = 10;//读取本地城市文件数据
    private static final int HANDLER_MSG_SEARCH_DATA_SUCCESS = 15;//搜索数据
    private static final int HANDLER_MSG_LOCATION_SUCCESS = 20;//定位获取经纬度
    private static final int HANDLER_MSG_GET_CITY_SUCCESS = 25;//根据定位的经纬度获取对应的城市
    private static final int HANDLER_MSG_GET_CITY_ID_SUCCESS = 30;//根据定位的城市获取对应的城市ID


    @Override
    public int getLayoutId() {
        return R.layout.activity_unified;
    }

    @Override
    public Observable<BaseResponse<CityBean>> initObservable() {
        return HttpManager.getApi().getCityList(mapParams);
    }

    @Override
    public boolean isFirstLoad() {
        return true;
    }

    @Override
    public void initView() {
        if (AppUtil.isVersionKitkat()) {
            layoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            layoutHeightTop.setVisibility(View.GONE);
        }
        search_adapter = new UnifiedSearchAdapter(this, this);
        select_city_lv_search_result.setAdapter(search_adapter);
        flag_adapter = new UnifiedFlagAdapter(this, litter);
        select_city_lv_flag.setAdapter(flag_adapter);
        list_adapter = new UnifiedListAdapter(this, this);
        select_city_lv_city_list.setAdapter(list_adapter);
        select_city_lv_city_list.setVisibility(View.VISIBLE);
        initListener();
    }

    /**
     * 开始定位权限申请
     */
    private void initAddress() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        requestPermissions(permissions, mListener);
    }

    /**
     * 申请定位权限
     */
    private String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION};
    private PermissionsListener mListener = new PermissionsListener() {
        @Override
        public void onGranted() {
            getLocation();
        }

        @Override
        public void onDenied(List<String> deniedPermissions, boolean isNeverAsk) {
            if (!isNeverAsk) {//请求权限没有全被勾选不再提示然后拒绝
                new AlertFragmentDialog.Builder(mActivity)
                        .setLeftBtnText("取消").setLeftCallBack(new AlertFragmentDialog.LeftClickCallBack() {
                    @Override
                    public void dialogLeftBtnClick() {

                    }
                }).setContent("为了能正常使用\"" + App.getAPPName() + "\"，请授予所需权限")
                        .setRightBtnText("授权").setRightCallBack(new AlertFragmentDialog.RightClickCallBack() {
                    @Override
                    public void dialogRightBtnClick() {
                        requestPermissions(permissions, mListener);
                    }
                }).build();
            } else {//全被勾选不再提示
                new AlertFragmentDialog.Builder(mActivity)
                        .setLeftBtnText("取消").setLeftCallBack(new AlertFragmentDialog.LeftClickCallBack() {
                    @Override
                    public void dialogLeftBtnClick() {

                    }
                }).setContent("\"" + App.getAPPName() + "\"缺少必要权限\n请手动授予\"" + App.getAPPName() + "\"访问您的权限")
                        .setRightBtnText("授权").setRightCallBack(new AlertFragmentDialog.RightClickCallBack() {
                    @Override
                    public void dialogRightBtnClick() {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivity(intent);
                    }
                }).build();
            }
        }
    };

    /**
     * 获取经纬度
     */
    public void getLocation() {
        new Thread() {
            @Override
            public void run() {
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    return;
                }
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Message msg = handler.obtainMessage();
                if (location != null) {
                    double latitude = location.getLatitude(); // 经度
                    double longitude = location.getLongitude(); // 纬度
                    double[] data = {latitude, longitude};
                    msg.what = HANDLER_MSG_LOCATION_SUCCESS;
                    msg.obj = data;
                } else {
                    msg.what = HANDLER_MSG_LOCATION_FAIL;
                }
                handler.sendMessage(msg);
            }
        }.run();
    }


    private void initListener() {
        select_city_ic_back.setOnClickListener(this);
        select_city_ic_clean.setOnClickListener(this);
        select_city_et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StrUtils.isNotNull(s.toString())) {
                    intoSearch();
                    long now = System.currentTimeMillis();
                    if (now - msec > 200) {
                        msec = now;
                        getSearchData(s.toString());
                    }
                } else {
                    exitSearch();
                }
            }
        });
        select_city_lv_flag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                select_city_lv_city_list.smoothScrollToPositionFromTop(position, 0, 500);
                select_city_lv_city_list.setSelection(position);
            }
        });
    }

    /**
     * 异步读取本地文件数据
     */
    private void getLocationData() {
        try {
            new Thread() {
                @Override
                public void run() {
                    try {
                        InputStream is = getAssets().open("City.txt");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        String str = new String(buffer, "UTF-8");
                        List<UnifiedBase> data = JsonUtil.Json2Lists(str, UnifiedBase.class);
                        if (data != null && data.size() > 0) {
                            all_unified.addAll(data);
                        }
                        setAllData();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAllData() {
        litter.add("");
        litter.add("");
        ArrayList<List<UnifiedBase>> all = new ArrayList<>();
        List<UnifiedBase> now = null;
        for (int i = 0; i < all_unified.size(); i++) {
            if (i == 0) {
                litter.add(all_unified.get(i).pinyinFirst);
                now = new ArrayList<>();
                now.add(all_unified.get(i));
                all.add(now);
            } else {
                if (!all_unified.get(i).pinyinFirst.equals(all_unified.get(i - 1).pinyinFirst)) {
                    litter.add(all_unified.get(i).pinyinFirst);
                    now = new ArrayList<>();
                    now.add(all_unified.get(i));
                    all.add(now);
                } else {
                    now.add(all_unified.get(i));
                }
            }
        }
        Message m = handler.obtainMessage();
        m.what = HANDLER_MSG_READ_DATA_SUCCESS;
        m.obj = all;
        handler.sendMessage(m);
    }


    /**
     * 获取城市名称
     *
     * @param data
     */
    private void getAddress(double[] data) {
        String url = "http://gc.ditu.aliyun.com/regeocoding?l=" + data[0] + "," + data[1] + "&type=010";
        FormBody.Builder formBody = new FormBody.Builder();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).post(formBody.build()).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = handler.obtainMessage();
                message.what = HANDLER_MSG_LOCATION_FAIL;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) {
                Message message = handler.obtainMessage();
                try {
                    String string = response.body().string();
                    CreditCardAddressBean addressBean = JsonUtil.Json2T(string, CreditCardAddressBean.class);
                    message.what = HANDLER_MSG_GET_CITY_SUCCESS;
                    message.obj = addressBean;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    message.what = HANDLER_MSG_LOCATION_FAIL;
                    handler.sendMessage(message);
                }
            }
        });
    }

    /**
     * 获取定位城市的ID
     *
     * @param str
     */
    private void getCityId(final String str) {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < all_unified.size(); i++) {
                    if ((StrUtils.isNotNull(all_unified.get(i).name) && all_unified.get(i).name.equals(str))) {
                        cityId = all_unified.get(i).id;
                    }
                }
                Message m = handler.obtainMessage();
                if (cityId == 0) {//获取城市ID失败
                    m.what = HANDLER_MSG_LOCATION_FAIL;
                } else {
                    //定位成功 保存定位的城市
                    SpUtil.putString(SpUtil.CITY_NAME, str);
                    SpUtil.putInt(SpUtil.CITY_ID, cityId);
                    m.what = HANDLER_MSG_GET_CITY_ID_SUCCESS;
                    m.arg1 = cityId;
                    m.obj = str;
                }
                handler.sendMessage(m);
            }
        }.run();
    }

    private void setHotData(List<UnifiedBase> hotList) {
        //TODO 获取热门城市的数据 接口拿的
        list_adapter.setHot(hotList, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_city_ic_back:
                finish();
                break;
            case R.id.select_city_ic_clean:
                select_city_et_search.setText("");
                exitSearch();
                break;
            default:
                break;
        }
    }


    private long msec = 0;

    private void exitSearch() {
        select_city_lv_city_list.setVisibility(View.VISIBLE);
        select_city_ic_clean.setVisibility(View.GONE);
        select_city_tv_search_empty.setVisibility(View.GONE);
        select_city_lv_search_result.setVisibility(View.GONE);
    }

    private void intoSearch() {
        select_city_lv_city_list.setVisibility(View.GONE);
        select_city_ic_clean.setVisibility(View.VISIBLE);
        select_city_lv_search_result.setVisibility(View.VISIBLE);
    }

    private void getSearchData(final String str) {
        new Thread() {
            @Override
            public void run() {
                ArrayList<UnifiedBase> cur_search_station = new ArrayList<>();//当次搜索数据
                String pinYin = CityData.getPinYin(str);
                for (int i = 0; i < all_unified.size(); i++) {
                    if (StrUtils.isChinese(str)) {
                        if ((StrUtils.isNotNull(all_unified.get(i).name) && all_unified.get(i).name.contains(str))) {
                            cur_search_station.add(all_unified.get(i));
                        }
                    } else {
                        if (all_unified.get(i).pinyinFirst.equals(pinYin)
                                || all_unified.get(i).pinyinFull.contains(pinYin)
                                || all_unified.get(i).pinyinAbbr.contains(pinYin)) {
                            cur_search_station.add(all_unified.get(i));
                        }
                    }
                }
                Message m = handler.obtainMessage();
                m.what = HANDLER_MSG_SEARCH_DATA_SUCCESS;
                m.obj = cur_search_station;
                handler.sendMessage(m);
            }
        }.run();
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_MSG_LOCATION_FAIL://定位失败 默认定位城市为上海市
                    history.add(new UnifiedBase(310000, "上海市"));
                    list_adapter.setHistory(history);
                    break;
                case HANDLER_MSG_READ_DATA_SUCCESS://获取城市信息展示
                    ArrayList<List<UnifiedBase>> all = (ArrayList<List<UnifiedBase>>) msg.obj;
                    flag_adapter.notifyDataSetChanged();
                    list_adapter.setAll(all);
                    list_adapter.setLetter(litter);
                    break;
                case HANDLER_MSG_SEARCH_DATA_SUCCESS://搜索数据
                    search.clear();
                    ArrayList<UnifiedBase> cur_search_base = (ArrayList<UnifiedBase>) msg.obj;
                    search.addAll(cur_search_base);
                    search_adapter.setList(search);
                    if (search == null || search.size() == 0) {
                        select_city_tv_search_empty.setVisibility(View.VISIBLE);
                    } else {
                        select_city_tv_search_empty.setVisibility(View.GONE);
                        intoSearch();
                    }
                    break;
                case HANDLER_MSG_LOCATION_SUCCESS://定位获取经纬度成功 获取对应的城市名称
                    double[] data = (double[]) msg.obj;
                    getAddress(data);
                    break;
                case HANDLER_MSG_GET_CITY_SUCCESS://根据定位的城市名称获取城市对应的id
                    CreditCardAddressBean addressBean = (CreditCardAddressBean) msg.obj;
                    List<CreditCardAddressBean.AddressBean> addrList = addressBean.getAddrList();
                    if (AbStringUtil.isListEmpty(addrList)) {
                        CreditCardAddressBean.AddressBean info = addrList.get(0);
                        String[] split = info.getAdmName().split(",");
                        getCityId(split[1]);
                    }
                    break;
                case HANDLER_MSG_GET_CITY_ID_SUCCESS://设置定位的城市
                    history.add(new UnifiedBase(msg.arg1, (String) msg.obj));
                    list_adapter.setHistory(history);
                    break;

                default:
                    break;
            }
        }
    };

    //showHot() 与 hideHot() 这里是上面的热门城市数据是否显示隐藏的操作，隐藏的话只显示6个，第六个选项是操作项
    @Override
    public void showHot() {
        list_adapter.setHot(true);
    }

    @Override
    public void hideHot() {
        list_adapter.setHot(false);
    }

    @Override
    public void choice(UnifiedBase base) {
        //TODO 当前选择的数据
        //需要历史选择的数据这里就需要保存了
        SpUtil.putString(SpUtil.CITY_NAME, base.name);
        SpUtil.putInt(SpUtil.CITY_ID, base.id);
        Intent intent = new Intent();
        intent.putExtra("UnifiedBase", base);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void remove(UnifiedBase base) {
        //TODO 删除这个数据 如果需要做历史数这里就是删除历史记录中的一个
        history.remove(base);
        list_adapter.setHistory(history);
    }

    @Override
    public void clear() {
        //TODO 删除所有数据 如果需要做历史数这里就是删除历史记录中的所有数据
        history.clear();
        list_adapter.setHistory(history);
    }

    @Override
    public void showLoading(String content) {

    }

    @Override
    public void showErrorMsg(String msg, String type) {

    }

    @Override
    public void recordSuccess(CityBean cityBean) {
        if (AbStringUtil.isListEmpty(cityBean.getCityList())) {
            all_unified.addAll(cityBean.getCityList());
            setAllData();
            setHotData(cityBean.getHotCityList());
            int city_id = SpUtil.getInt(SpUtil.CITY_ID, 0);
            String city_name = SpUtil.getString(SpUtil.CITY_NAME);
            if (AbStringUtil.isEmpty(city_name)) {
                //用户第一次进入的时候通过定位选择数据
                initAddress();
            } else {
                history.add(new UnifiedBase(city_id, city_name));
                list_adapter.setHistory(history);
            }
        }
    }

}
