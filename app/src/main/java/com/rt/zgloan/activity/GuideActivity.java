package com.rt.zgloan.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.util.ConvertUtil;
import com.rt.zgloan.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;

/**
 * Created by Administrator on 2017/8/31.
 */

public class GuideActivity extends BaseActivity {
    GuidePagerAdapter pagerAdapter;
    boolean isTransparent = false;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.guidelayout)
    LinearLayout mGuidelayout;
    private int curPosition;
    int[] images = new int[]{R.mipmap.icon_guide1, R.mipmap.icon_guide2, R.mipmap.icon_guide3, R.mipmap.icon_guide4};

    @Override
    public Observable<BaseResponse> initObservable() {
        return null;
    }

    @Override
    public boolean isFirstLoad() {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        initGuideLayout();
    }

    private void initGuideLayout() {
        for (int i = 0; i < images.length; i++) {
            ImageView view = new ImageView(GuideActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i != 0) {
                params.setMargins(ConvertUtil.dip2px(this, 8), 0, 0, 0);
            }
            view.setLayoutParams(params);
            view.setPadding(ConvertUtil.px2dip(this, 5), 0, ConvertUtil.px2dip(this, 5), 0);
            view.setImageResource(R.drawable.shape_aboutdot);
            view.setId(10000 + i);
            mGuidelayout.addView(view);
        }
        mGuidelayout.getChildAt(0).setEnabled(false);
        pagerAdapter = new GuidePagerAdapter();
        mViewpager.setAdapter(pagerAdapter);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == images.length - 1 && positionOffset == 0
                        && positionOffsetPixels == 0) {
                    if (isTransparent) {
                        SpUtil.putInt(SpUtil.CACHE_IS_FIRST_LOGIN, SpUtil.NOT_FIRST_LOGIN);
                        startActivity(MainActivity.class);
                        finish();
                        isTransparent = false;
                    }

                }
            }

            @Override
            public void onPageSelected(int position) {
                curPosition = position;
                for (int i = 0; i < images.length; i++) {
                    if (i == position) {
                        mGuidelayout.getChildAt(i).setEnabled(false);
                    } else {
                        mGuidelayout.getChildAt(i).setEnabled(true);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING
                        && curPosition == images.length - 1) {
                    isTransparent = true;
                } else {
                    isTransparent = false;
                }
            }
        });
    }

    @Override
    public void showLoading(String content) {

    }

    @Override
    public void showErrorMsg(String msg, String type) {

    }

    @Override
    public void recordSuccess(Object o) {

    }


    class GuidePagerAdapter extends PagerAdapter {
        List<View> list;

        public GuidePagerAdapter() {
            list = new ArrayList<>();
            for (int i = 0; i < images.length; i++) {
                if (i < images.length - 1) {
                    ImageView view = new ImageView(GuideActivity.this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    view.setLayoutParams(params);
                    view.setScaleType(ImageView.ScaleType.FIT_XY);
                    view.setImageResource(images[i]);
                    list.add(view);
                } else {
                    View view = LayoutInflater.from(mContext).inflate(R.layout.guide_last_layout, null);
                    ImageView imageView = (ImageView) view.findViewById(R.id.iv_img);
                    TextView button = (TextView) view.findViewById(R.id.btn_next);
                    imageView.setImageResource(images[i]);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SpUtil.putInt(SpUtil.CACHE_IS_FIRST_LOGIN, SpUtil.NOT_FIRST_LOGIN);
                            Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    list.add(view);
                }

            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position), 0);
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }

    }
}
