package com.rt.zgloan.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.rt.zgloan.R;
import com.rt.zgloan.activity.myActivity.GetProvinceActivity;
import com.rt.zgloan.adapter.PersonalDataAdapter;
import com.rt.zgloan.adapter.PersonalDataNewAdapter;
import com.rt.zgloan.app.App;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.bean.DataBean;
import com.rt.zgloan.bean.GetCitySonInfo;
import com.rt.zgloan.bean.GetProvinceSonInfo;
import com.rt.zgloan.bean.PersonalDataNewInfo;
import com.rt.zgloan.bean.PersonalDataSonInfo;
import com.rt.zgloan.bean.UserDataBean;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.http.HttpSubscriber;
import com.rt.zgloan.util.AppUtil;
import com.rt.zgloan.util.DialogUtil;
import com.rt.zgloan.util.SpUtil;
import com.rt.zgloan.util.StringUtil;
import com.rt.zgloan.util.ToastUtil;
import com.rt.zgloan.weight.EditTextWithDel;
import com.rt.zgloan.weight.LoadingFragment;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;

/**
 * 完善个人资料
 * Created by hjy on 2017/8/25.
 */

public class PersonalDataActivity extends BaseActivity<UserDataBean> {

    @BindView(R.id.rel_loan_amount_range)
    RelativeLayout rel_loan_amount_range;
    @BindView(R.id.main)
    View main;
    @BindView(R.id.rel_loan_time_range)
    RelativeLayout rel_loan_time_range;
    @BindView(R.id.tv_loan_amount)
    TextView tv_loan_amount;
    @BindView(R.id.tv_loan_time)
    TextView tv_loan_time;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @BindView(R.id.tv_input_phone)
    EditText tv_input_phone;
    @BindView(R.id.edit_name)
    EditTextWithDel edit_name;
    @BindView(R.id.edit_id_card)
    EditTextWithDel edit_id_card;
    @BindView(R.id.tv_city)
    TextView tv_city;
    @BindView(R.id.rel_city_name)
    RelativeLayout rel_city_name;
    @BindView(R.id.money_range)
    TextView moneyRange;
    @BindView(R.id.time_range)
    TextView timeRange;
    @BindView(R.id.city_range)
    TextView cityRange;
    @BindView(R.id.layout_height_top)
    RelativeLayout mLayoutHeightTop;
    @BindView(R.id.tv_choice_city)
    TextView mTvChoiceCity;
    @BindView(R.id.tv_choice_education)
    TextView mTvChoiceEducation;
    @BindView(R.id.tv_choice_marital_status)
    TextView mTvChoiceMaritalStatus;
    @BindView(R.id.tv_choice_work)
    TextView mTvChoiceWork;

    private String str_edit_name, str_edit_id_card, str_phone;
    private String str_choose_amount, str_choose_time, str_amount, str_time;
    private String mStringCtiy = "";
    private String mStringEducation = "";
    private String mStringMarital = "";
    private String mStringWork = "";
    private PopupWindow window;

    private PersonalDataAdapter adapter;
    private List<PersonalDataNewInfo> personalDataNewInfos = new ArrayList<>();
    private List<PersonalDataNewInfo> personalDataNewInfos2 = new ArrayList<>();
    private PersonalDataNewAdapter newAdapter;
    private PersonalDataSonInfo personalDataSonInfo;

    //选择框内容项
    private String[] mEducation = {"初中及以下", "高中", "中专", "大专", "本科", "研究生及以上"};
    private String[] mMarital = {"未婚", "已婚未育", "已婚已育", "离异", "其他"};
    private String[] mWork = {"上班族", "学生党", "企业主", "自由职业", "其他"};
    private String[] amount = {"￥3000以下", "￥3000~￥10000", "￥10000~￥50000", "￥50000~￥100000", "￥100000以上"};
    private String[] time = {"1个月以下", "1个月~3个月", "3个月~6个月", "6个月~12个月", "12个月以上"};
    private String[] occupationInfor = {"上班族", "学生党", "商人", "无职业", "事业单位", "其他"};
    private String[] occupationInfor2 = {"有银行卡", "有信用卡", "本科学历", "征信良好", "有过借款", "有房贷", "有车一族", "有社保", "有公积金", "芝麻分高"};
    private GetProvinceSonInfo provinceSonInfo;
    private GetCitySonInfo cityInfo;

    private List<String> listMarital = new ArrayList<>();
    private List<String> listWork = new ArrayList<>();
    private List<String> listEducation = new ArrayList<>();
    private String provinceName;//省份名称
    private String cityName;//城市名称
    private String location;//省份及城市
    private String phoneNum;//手机号
    private String userName;//用户姓名
    private String idCardNum;//身份证
    private String education;//学历
    private String occupation;//职业
    private String marital;//婚姻状况
    private String idEducation = "-1", idOccupation = "-1", idMarital = "-1", idProvince = "-1", idCity = "-1";


    @Override
    public Observable<BaseResponse<UserDataBean>> initObservable() {

//        mapParams.put("mobile", SpUtil.getString(SpUtil.mobile));
        mapParams.put("id", SpUtil.getString(SpUtil.userId));

        return HttpManager.getApi().getUsersById(mapParams);
    }

    @Override
    public boolean isFirstLoad() {
        return true;
    }

    @OnClick({R.id.rel_loan_amount_range, R.id.rel_loan_time_range, R.id.rel_city_name, R.id.Rl_life_city, R.id.Rl_education,
            R.id.Rl_marital_status, R.id.Rl_work, R.id.btn_save})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //城市选择
            case R.id.Rl_life_city:
                this.startActivity(GetProvinceActivity.class);
                break;
            //学历选择
            case R.id.Rl_education:
                getCreditByType2("2");//获取学历信息
                break;
            //婚姻状况选择
            case R.id.Rl_marital_status:
                getCreditByType1("1");//获取婚姻状态
                break;
            //职业身份选择
            case R.id.Rl_work:
                getCreditByType3("3");//获取职业
                break;
            //保存
            case R.id.btn_save:
                getEditContent();
//                if (!StringUtil.isBlank(userName)) {
//                    if (userName.length() < 2 || userName.length() > 5) {
//                        ToastUtil.showToast("姓名长度应为2-5个字");
//                        return;
//                    }
//
//                    if (!AbStringUtil.checkNameChese(userName)) {
//                        ToastUtil.showToast("请输入正确的姓名");
//                        return;
//                    }
//                }

//                if (!StringUtil.isBlank(idCardNum)) {
//                    if (!AbStringUtil.isEmpty(StringUtil.checkPersonalId(idCardNum))) {
//                        ToastUtil.showToast(StringUtil.checkPersonalId(idCardNum));
//                        return;
//                    }
//                }
                saveHttp();
                break;

            case R.id.rel_loan_amount_range:
                showAmountRangePopWindows();
                break;

            case R.id.rel_loan_time_range:
                showTimeRangePopWindows();
                break;
            case R.id.rel_city_name:
                this.startActivity(GetProvinceActivity.class);
                break;

        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_data;
    }

    @Override
    public void initView() {
        if (AppUtil.isVersionKitkat()) {
            mLayoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeightTop.setVisibility(View.GONE);
        }
        mTitle.setTitle(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, "个人资料");


        GridLayoutManager layoutManager2 = new GridLayoutManager(this, 4);
        recyclerView1.setLayoutManager(layoutManager2);
        recyclerView1.setNestedScrollingEnabled(false);

        GridLayoutManager layoutManager1 = new GridLayoutManager(this, 4);
        recyclerView2.setLayoutManager(layoutManager1);
        recyclerView2.setNestedScrollingEnabled(false);
        recyclerView2.setFocusable(false);


//


    }

    private void getCreditByType1(String type) {
        mapParams.put("type", type);
        mPresenter.toSubscribe(HttpManager.getApi().getCreditByType(mapParams), new HttpSubscriber<DataBean>() {
            @Override
            protected void _onStart() {
//                LoadingFragment.getInstends().show(((FragmentActivity) mContext).getSupportFragmentManager(), "");
            }

            @Override
            protected void _onNext(DataBean dataBean) {

                if (dataBean != null) {
                    Logger.e("dataBean" + dataBean);
                    if (listMarital.isEmpty()) {
                        for (int i = 0; i < dataBean.getTypes().size(); i++) {
                            Logger.e("dataBean.getTypes().size()===" + dataBean.getTypes().size());
                            listMarital.add(dataBean.getTypes().get(i).getName().toString());
                        }
                    }

                    showMaritalPopWidows(dataBean);
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showToast(message);
            }

            @Override
            protected void _onCompleted() {

            }
        });
    }

    private void getCreditByType2(String type) {
        mapParams.put("type", type);
        mPresenter.toSubscribe(HttpManager.getApi().getCreditByType(mapParams), new HttpSubscriber<DataBean>() {
            @Override
            protected void _onStart() {

            }

            @Override
            protected void _onNext(DataBean dataBean) {
                if (dataBean != null) {
                    Logger.e("dataBean" + dataBean);
                    if (listEducation.isEmpty()) {
                        for (int i = 0; i < dataBean.getTypes().size(); i++) {
                            Logger.e("dataBean.getTypes().size()===" + dataBean.getTypes().size());
                            listEducation.add(dataBean.getTypes().get(i).getName().toString());
                        }
                    }

                    showEducationPopWidows(dataBean);
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showToast(message);
            }

            @Override
            protected void _onCompleted() {

            }
        });
    }

    private void getCreditByType3(String type) {
        mapParams.put("type", type);
        mPresenter.toSubscribe(HttpManager.getApi().getCreditByType(mapParams), new HttpSubscriber<DataBean>() {
            @Override
            protected void _onStart() {

            }

            @Override
            protected void _onNext(DataBean dataBean) {
                if (dataBean != null) {
                    Logger.e("dataBean" + dataBean);
                    if (listWork.isEmpty()) {
                        for (int i = 0; i < dataBean.getTypes().size(); i++) {
                            Logger.e("dataBean.getTypes().size()===" + dataBean.getTypes().size());
                            listWork.add(dataBean.getTypes().get(i).getName().toString());
                        }
                    }

                    showWorkPopWidows(dataBean);
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showToast(message);
            }

            @Override
            protected void _onCompleted() {
//                LoadingFragment.getInstends().dismiss();
            }
        });
    }

    //学历选择框
    private void showEducationPopWidows(final DataBean dataBean) {
        View view = View.inflate(this, R.layout.choose_range_popwindows, null);
        RelativeLayout rel_btn_cancel = (RelativeLayout) view.findViewById(R.id.rel_btn_cancel);
        RelativeLayout rel_btn_sure = (RelativeLayout) view.findViewById(R.id.rel_btn_sure);
        final WheelView wheelview = (WheelView) view.findViewById(R.id.wheelview);

        wheelview.setWheelAdapter(new ArrayWheelAdapter(this)); // 文本数据源
        wheelview.setSkin(WheelView.Skin.Holo); // common皮肤
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.backgroundColor = Color.parseColor("#FFFFFF");
        style.holoBorderColor = Color.parseColor("#DCDBDA");   // holo样式边框颜色 红色
        style.textColor = Color.argb(255, 125, 125, 125); // 文本颜色 灰色
        style.selectedTextColor = Color.parseColor("#ff4a4a"); // 选中文本颜色 黑色
        style.textSize = 14;// 文本大小
        style.selectedTextSize = 16;   // 选中文本大小
        style.textAlpha = 0.5f;  // 文本透明度(0f ~ 1f)
        wheelview.setStyle(style);
        wheelview.setSelection(2);
        wheelview.setWheelSize(5);

//        List<String> list = new ArrayList<>();
//        for (int i = 0; i <= 5; i++) {
//            list.add(mEducation[i]);
//        }
        wheelview.setWheelData(listEducation);


        rel_btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("wheelview", wheelview.getSelectionItem().toString() + "");
                window.dismiss();
                mTvChoiceEducation.setText(wheelview.getSelectionItem().toString());
                mTvChoiceEducation.setTextColor(ContextCompat.getColor(App.getContext(), R.color.color_666666));
                mStringEducation = wheelview.getSelectionItem().toString();
                idEducation = dataBean.getTypes().get(wheelview.getCurrentPosition()).getId();
                Logger.e("wheelview.getCurrentPosition()" + wheelview.getCurrentPosition());
                Logger.e("idEducation" + idEducation);

            }
        });
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //让pop覆盖在输入法上面
        window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //让pop自适应输入状态
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00808080);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.my_popshow_anim_style);
        //设置在底部显示
        window.showAtLocation(main, Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);

        rel_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                AppUtil.backgroundAlpha(PersonalDataActivity.this, 1f);
            }
        });
        AppUtil.backgroundAlpha(PersonalDataActivity.this, 0.5f);
    }

    //婚姻状态选择框
    private void showMaritalPopWidows(final DataBean dataBean) {
        View view = View.inflate(this, R.layout.choose_range_popwindows, null);
        RelativeLayout rel_btn_cancel = (RelativeLayout) view.findViewById(R.id.rel_btn_cancel);
        RelativeLayout rel_btn_sure = (RelativeLayout) view.findViewById(R.id.rel_btn_sure);
        final WheelView wheelview = (WheelView) view.findViewById(R.id.wheelview);

        wheelview.setWheelAdapter(new ArrayWheelAdapter(this)); // 文本数据源
        wheelview.setSkin(WheelView.Skin.Holo); // common皮肤
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.backgroundColor = Color.parseColor("#FFFFFF");
        style.holoBorderColor = Color.parseColor("#DCDBDA");   // holo样式边框颜色 红色
        style.textColor = Color.argb(255, 125, 125, 125); // 文本颜色 灰色
        style.selectedTextColor = Color.parseColor("#ff4a4a"); // 选中文本颜色 黑色
        style.textSize = 14;// 文本大小
        style.selectedTextSize = 16;   // 选中文本大小
        style.textAlpha = 0.5f;  // 文本透明度(0f ~ 1f)
        wheelview.setStyle(style);
        wheelview.setSelection(2);
        wheelview.setWheelSize(5);

//        List<String> list = new ArrayList<>();
//        for (int i = 0; i <= 4; i++) {
//            list.add(mMarital[i]);
//        }
        wheelview.setWheelData(listMarital);

        rel_btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("wheelview", wheelview.getSelectionItem().toString() + "");
                window.dismiss();
                mTvChoiceMaritalStatus.setText(wheelview.getSelectionItem().toString());
                mTvChoiceMaritalStatus.setTextColor(ContextCompat.getColor(App.getContext(), R.color.color_666666));
                mStringMarital = wheelview.getSelectionItem().toString();
                idMarital = dataBean.getTypes().get(wheelview.getCurrentPosition()).getId();

            }
        });
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //让pop覆盖在输入法上面
        window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //让pop自适应输入状态
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00808080);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.my_popshow_anim_style);
        //设置在底部显示
        window.showAtLocation(main, Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);

        rel_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                AppUtil.backgroundAlpha(PersonalDataActivity.this, 1f);
            }
        });
        AppUtil.backgroundAlpha(PersonalDataActivity.this, 0.5f);
    }

    //职业选择框
    private void showWorkPopWidows(final DataBean dataBean) {
        View view = View.inflate(this, R.layout.choose_range_popwindows, null);
        RelativeLayout rel_btn_cancel = (RelativeLayout) view.findViewById(R.id.rel_btn_cancel);
        RelativeLayout rel_btn_sure = (RelativeLayout) view.findViewById(R.id.rel_btn_sure);
        final WheelView wheelview = (WheelView) view.findViewById(R.id.wheelview);

        wheelview.setWheelAdapter(new ArrayWheelAdapter(this)); // 文本数据源
        wheelview.setSkin(WheelView.Skin.Holo); // common皮肤
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.backgroundColor = Color.parseColor("#FFFFFF");
        style.holoBorderColor = Color.parseColor("#DCDBDA");   // holo样式边框颜色 红色
        style.textColor = Color.argb(255, 125, 125, 125); // 文本颜色 灰色
        style.selectedTextColor = Color.parseColor("#ff4a4a"); // 选中文本颜色 黑色
        style.textSize = 14;// 文本大小
        style.selectedTextSize = 16;   // 选中文本大小
        style.textAlpha = 0.5f;  // 文本透明度(0f ~ 1f)
        wheelview.setStyle(style);
        wheelview.setSelection(2);
        wheelview.setWheelSize(5);

//        List<String> list = new ArrayList<>();
//        for (int i = 0; i <= 4; i++) {
//            list.add(mWork[i]);
//        }
        wheelview.setWheelData(listWork);

        rel_btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("wheelview", wheelview.getSelectionItem().toString() + "");
                window.dismiss();
                mTvChoiceWork.setText(wheelview.getSelectionItem().toString());
                mTvChoiceWork.setTextColor(ContextCompat.getColor(App.getContext(), R.color.color_666666));
                mStringWork = wheelview.getSelectionItem().toString();
                idOccupation = dataBean.getTypes().get(wheelview.getCurrentPosition()).getId();

            }
        });
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //让pop覆盖在输入法上面
        window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //让pop自适应输入状态
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00808080);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.my_popshow_anim_style);
        //设置在底部显示
        window.showAtLocation(main, Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);

        rel_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                AppUtil.backgroundAlpha(PersonalDataActivity.this, 1f);
            }
        });
        AppUtil.backgroundAlpha(PersonalDataActivity.this, 0.5f);
    }

    private void showAmountRangePopWindows() {
        View view = View.inflate(this, R.layout.choose_range_popwindows, null);
        RelativeLayout rel_btn_cancel = (RelativeLayout) view.findViewById(R.id.rel_btn_cancel);
        RelativeLayout rel_btn_sure = (RelativeLayout) view.findViewById(R.id.rel_btn_sure);
        final WheelView wheelview = (WheelView) view.findViewById(R.id.wheelview);

        wheelview.setWheelAdapter(new ArrayWheelAdapter(this)); // 文本数据源
        wheelview.setSkin(WheelView.Skin.Holo); // common皮肤
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.backgroundColor = Color.parseColor("#FFFFFF");
        style.holoBorderColor = Color.parseColor("#DCDBDA");   // holo样式边框颜色 红色
        style.textColor = Color.argb(255, 125, 125, 125); // 文本颜色 灰色
        style.selectedTextColor = Color.parseColor("#ff4a4a"); // 选中文本颜色 黑色
        style.textSize = 14;// 文本大小
        style.selectedTextSize = 16;   // 选中文本大小
        style.textAlpha = 0.5f;  // 文本透明度(0f ~ 1f)
        wheelview.setStyle(style);
        wheelview.setSelection(2);
        wheelview.setWheelSize(5);

        List<String> list = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            list.add(amount[i]);
        }
        wheelview.setWheelData(list);

        rel_btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("aa", wheelview.getSelectionItem().toString() + "");
                window.dismiss();
                tv_loan_amount.setText(wheelview.getSelectionItem().toString());
                str_choose_amount = wheelview.getSelectionItem().toString();
                moneyRange.setVisibility(View.GONE);

            }
        });
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //让pop覆盖在输入法上面
        window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //让pop自适应输入状态
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00808080);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.my_popshow_anim_style);
        //设置在底部显示
        window.showAtLocation(main, Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);

        rel_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                AppUtil.backgroundAlpha(PersonalDataActivity.this, 1f);
            }
        });
        AppUtil.backgroundAlpha(PersonalDataActivity.this, 0.5f);

    }

    private void showTimeRangePopWindows() {
        View view = View.inflate(this, R.layout.choose_range_popwindows, null);
        RelativeLayout rel_btn_cancel = (RelativeLayout) view.findViewById(R.id.rel_btn_cancel);
        RelativeLayout rel_btn_sure = (RelativeLayout) view.findViewById(R.id.rel_btn_sure);
        final WheelView wheelview = (WheelView) view.findViewById(R.id.wheelview);

        wheelview.setWheelAdapter(new ArrayWheelAdapter(this)); // 文本数据源
        wheelview.setSkin(WheelView.Skin.Holo); // common皮肤
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.backgroundColor = Color.parseColor("#FFFFFF");
        style.holoBorderColor = Color.parseColor("#DCDBDA");   // holo样式边框颜色 红色
        style.textColor = Color.argb(255, 125, 125, 125); // 文本颜色 灰色
        style.selectedTextColor = Color.parseColor("#27A2FE"); // 选中文本颜色 黑色
        style.textSize = 14;// 文本大小
        style.selectedTextSize = 16;   // 选中文本大小
        style.textAlpha = 0.5f;  // 文本透明度(0f ~ 1f)
        wheelview.setStyle(style);
        wheelview.setSelection(2);
        wheelview.setWheelSize(5);

        List<String> list = new ArrayList<>();

        for (int i = 0; i <= 4; i++) {
            list.add(time[i]);
        }

        wheelview.setWheelData(list);
        rel_btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("aa", wheelview.getSelectionItem().toString() + "");
                window.dismiss();
                tv_loan_time.setText(wheelview.getSelectionItem().toString());
                str_choose_time = wheelview.getSelectionItem().toString();
                timeRange.setVisibility(View.GONE);

            }
        });
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //让pop覆盖在输入法上面
        window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //让pop自适应输入状态
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00808080);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.my_popshow_anim_style);
        //设置在底部显示
        window.showAtLocation(main, Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);

        rel_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                AppUtil.backgroundAlpha(PersonalDataActivity.this, 1f);
            }
        });
        AppUtil.backgroundAlpha(PersonalDataActivity.this, 0.5f);

    }

    @Override
    public void showLoading(String content) {

    }


    /**
     * 保存用户资料
     */
    private void saveHttp() {
        getEditContent();
        Logger.e("idEducation=" + idEducation + "idMarital=" + idMarital +
                "idOccupation=" + idOccupation + "idProvince=" + idProvince + "idCity=" + idCity);
        mapParams.put("id", SpUtil.getString(SpUtil.userId));
        mapParams.put("realname", userName);
        mapParams.put("card_no", idCardNum);
        mapParams.put("education", idEducation);
        mapParams.put("marriage", idMarital);
        mapParams.put("occupation", idOccupation);
        mapParams.put("province", idProvince);
        mapParams.put("city", idCity);


        mPresenter.toSubscribe(
                HttpManager.getApi().saveUserData(mapParams), new HttpSubscriber() {
                    @Override
                    protected void _onStart() {
                        LoadingFragment.getInstends().show(((FragmentActivity) mContext).getSupportFragmentManager(), "正在保存...");
                    }

                    @Override
                    protected void _onNext(Object o) {
                        SpUtil.putString(SpUtil.is_state, "1");
                        showSaveSuccessDialog();
                    }


                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(message);


                    }

                    @Override
                    protected void _onCompleted() {

                        LoadingFragment.getInstends().dismiss();
                    }
                }
        );

    }

    @Override
    public void showErrorMsg(String msg, String type) {
        Log.e("tag", msg);
        ToastUtil.showToast(msg);

    }

    @Override
    public void recordSuccess(UserDataBean userDataBean) {
        if (userDataBean != null) {
            setValue(userDataBean);
        }
    }

    //设置资料
    private void setValue(UserDataBean userDataBean) {
        if (!StringUtil.isBlank(userDataBean.getMobile())) {//设置手机号
            tv_input_phone.setText(userDataBean.getMobile() + "");
            tv_input_phone.setTextColor(ContextCompat.getColor(App.getContext(), R.color.color_666666));
            tv_input_phone.setEnabled(false);
            phoneNum = userDataBean.getMobile();

        }
        if (!StringUtil.isBlank(userDataBean.getRealname())) {//设置姓名
            edit_name.setText(userDataBean.getRealname() + "");
            edit_name.setTextColor(ContextCompat.getColor(App.getContext(), R.color.color_666666));
            userName = userDataBean.getRealname();
        }
        if (!StringUtil.isBlank(userDataBean.getCard_no())) {//设置身份证号码
            edit_id_card.setText(userDataBean.getCard_no() + "");
            edit_id_card.setTextColor(ContextCompat.getColor(App.getContext(), R.color.color_666666));
            idCardNum = userDataBean.getCard_no();
        }
        if (!StringUtil.isBlank(userDataBean.getEducationName()) && !StringUtil.isBlank(userDataBean.getEducationId())) {//设置学历
            mTvChoiceEducation.setText(userDataBean.getEducationName() + "");
            mTvChoiceEducation.setTextColor(ContextCompat.getColor(App.getContext(), R.color.color_666666));
            education = userDataBean.getEducationName();
            idEducation = userDataBean.getEducationId();
        }
        if (!StringUtil.isBlank(userDataBean.getMarriageId()) && !StringUtil.isBlank(userDataBean.getMarriageName())) {//设置婚姻状况
            mTvChoiceMaritalStatus.setText(userDataBean.getMarriageName() + "");
            mTvChoiceMaritalStatus.setTextColor(ContextCompat.getColor(App.getContext(), R.color.color_666666));
            marital = userDataBean.getMarriageName();
            idMarital = userDataBean.getMarriageId();
        }
        if (!StringUtil.isBlank(userDataBean.getOccupationId()) && !StringUtil.isBlank(userDataBean.getOccupationName())) {//设置职业
            mTvChoiceWork.setText(userDataBean.getOccupationName() + "");
            mTvChoiceWork.setTextColor(ContextCompat.getColor(App.getContext(), R.color.color_666666));
            occupation = userDataBean.getOccupationName();
            idOccupation = userDataBean.getOccupationId();
        }
        if (!StringUtil.isBlank(userDataBean.getProvinceName()) && !StringUtil.isBlank(userDataBean.getCityName())) {//设置地址
            mTvChoiceCity.setText(userDataBean.getProvinceName() + "-" + userDataBean.getCityName());
            mTvChoiceCity.setTextColor(ContextCompat.getColor(App.getContext(), R.color.color_666666));
            location = userDataBean.getProvinceName() + "-" + userDataBean.getCityName();
            idProvince = userDataBean.getProvinceId();
            idCity = userDataBean.getCityId();
        }
    }

    //获取填写信息
    private void getEditContent() {
        userName = edit_name.getText().toString().trim();
        idCardNum = edit_id_card.getText().toString().trim();
        phoneNum = tv_input_phone.getText().toString().trim();
        education = mTvChoiceEducation.getText().toString().trim();
        marital = mTvChoiceMaritalStatus.getText().toString().trim();
        occupation = mTvChoiceWork.getText().toString().trim();
        location = mTvChoiceCity.getText().toString().trim();

    }

    private void showSaveSuccessDialog() {
        View view = View.inflate(this, R.layout.dialog_sava_personal_data_success, null);
        DialogUtil.showAlertDialog(view);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                DialogUtil.dismiss();
                MainActivity.startMainActivity(mActivity, 3);
            }
        }, 1000);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
//            provinceSonInfo = (GetProvinceSonInfo) intent.getSerializableExtra("province");
//            cityInfo = (GetCitySonInfo) intent.getSerializableExtra("city");
            provinceName = intent.getStringExtra("province");
            idProvince = intent.getStringExtra("provinceId");
            cityName = intent.getStringExtra("city");
            idCity = intent.getStringExtra("cityId");
            mTvChoiceCity.setText(provinceName + "-" + cityName);
            mTvChoiceCity.setTextColor(ContextCompat.getColor(App.getContext(), R.color.color_666666));
            location = provinceName + "-" + cityName;
//            tv_city.setText(provinceName + "-" + cityName);
//            cityRange.setVisibility(View.GONE);

        }
    }

    private List<PersonalDataNewInfo> getOccupationInfor(String name) {
//        for (String string : occupationInfor) {
//            PersonalDataNewInfo personalDataNewInfo = new PersonalDataNewInfo();
//            personalDataNewInfo.setName(string);
//            if (name.equals(string)) {
//                personalDataNewInfo.setSelector(true);
//            }
//            personalDataNewInfos.add(personalDataNewInfo);
//        }
        int index = -1;
        try {
            index = Integer.parseInt(name);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < occupationInfor.length; i++) {
            PersonalDataNewInfo personalDataNewInfo = new PersonalDataNewInfo();
            personalDataNewInfo.setName(occupationInfor[i]);
            if (index != -1 && index - 1 == i) {
                personalDataNewInfo.setSelector(true);
            }
            personalDataNewInfos.add(personalDataNewInfo);
        }
        return personalDataNewInfos;
    }

    private List<PersonalDataNewInfo> getOccupationInfor2(String[] names) {
        for (int i = 0; i < occupationInfor2.length; i++) {
            PersonalDataNewInfo personalDataNewInfo = new PersonalDataNewInfo();
            personalDataNewInfo.setName(occupationInfor2[i]);
            for (int j = 0; j < names.length; j++) {
                int index = -1;
                try {
                    index = Integer.parseInt(names[j]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if (index != -1 && index - 1 == i) {
                    personalDataNewInfo.setSelector(true);
                    break;
                }
            }
            personalDataNewInfos2.add(personalDataNewInfo);
        }
        return personalDataNewInfos2;
    }


}