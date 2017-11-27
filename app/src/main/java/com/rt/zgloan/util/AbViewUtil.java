package com.rt.zgloan.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.rt.zgloan.R;
import com.rt.zgloan.globe.Constant;
import com.rt.zgloan.weight.EditTextWithDel;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

/**
 * Created by geek on 2016/6/21.
 */
public class AbViewUtil {

    /**
     * 无效值
     */
    public static final int INVALID = Integer.MIN_VALUE;

    /**
     * 描述：dip转换为px.
     *
     * @param context  the context
     * @param dipValue the dip value
     * @return px值
     */
    public static int dip2px(Context context, float dipValue) {
        DisplayMetrics mDisplayMetrics = AppUtil.getDisplayMetrics(context);
        return (int) applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, mDisplayMetrics);
    }

    /**
     * 描述：px转换为dip.
     *
     * @param context the context
     * @param pxValue the px value
     * @return dip值
     */
    public static int px2dip(Context context, float pxValue) {
        DisplayMetrics mDisplayMetrics = AppUtil.getDisplayMetrics(context);
        return (int) (pxValue / mDisplayMetrics.density);
    }


    /**
     * 设置PX padding.
     *
     * @param view   the view
     * @param left   the left padding in pixels
     * @param top    the top padding in pixels
     * @param right  the right padding in pixels
     * @param bottom the bottom padding in pixels
     */
    public static void setPadding(View view, int left,
                                  int top, int right, int bottom) {
        int scaledLeft = scaleValue(view.getContext(), left);
        int scaledTop = scaleValue(view.getContext(), top);
        int scaledRight = scaleValue(view.getContext(), right);
        int scaledBottom = scaleValue(view.getContext(), bottom);
        view.setPadding(scaledLeft, scaledTop, scaledRight, scaledBottom);
    }

    /**
     * 设置 PX margin.
     *
     * @param view   the view
     * @param left   the left margin in pixels
     * @param top    the top margin in pixels
     * @param right  the right margin in pixels
     * @param bottom the bottom margin in pixels
     */
    public static void setMargin(View view, int left, int top,
                                 int right, int bottom) {
        int scaledLeft = scaleValue(view.getContext(), left);
        int scaledTop = scaleValue(view.getContext(), top);
        int scaledRight = scaleValue(view.getContext(), right);
        int scaledBottom = scaleValue(view.getContext(), bottom);

        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams mMarginLayoutParams = (ViewGroup.MarginLayoutParams) view
                    .getLayoutParams();
            if (mMarginLayoutParams != null) {
                if (left != INVALID) {
                    mMarginLayoutParams.leftMargin = scaledLeft;
                }
                if (right != INVALID) {
                    mMarginLayoutParams.rightMargin = scaledRight;
                }
                if (top != INVALID) {
                    mMarginLayoutParams.topMargin = scaledTop;
                }
                if (bottom != INVALID) {
                    mMarginLayoutParams.bottomMargin = scaledBottom;
                }
                view.setLayoutParams(mMarginLayoutParams);
            }
        }

    }

    /**
     * 描述：根据屏幕大小缩放.
     *
     * @param context the context
     * @param value   the px value
     * @return the int
     */
    public static int scaleValue(Context context, float value) {
        DisplayMetrics mDisplayMetrics = AppUtil.getDisplayMetrics(context);
        //为了兼容尺寸小密度大的情况
        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;
        //解决横屏比例问题
        if (width > height) {
            width = mDisplayMetrics.heightPixels;
            height = mDisplayMetrics.widthPixels;
        }
        if (mDisplayMetrics.scaledDensity >= Constant.UI_DENSITY) {
            //密度
            if (width > Constant.UI_WIDTH) {
                value = value * (1.3f - 1.0f / mDisplayMetrics.scaledDensity);
            } else if (width < Constant.UI_WIDTH) {
                value = value * (1.0f - 1.0f / mDisplayMetrics.scaledDensity);
            }
        } else {
            //密度小屏幕大:缩小比例
            float offset = Constant.UI_DENSITY - mDisplayMetrics.scaledDensity;
            if (offset > 0.5f) {
                value = value * 0.9f;
            } else {
                value = value * 0.95f;
            }

        }
        return scale(mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels, value);
    }

    /**
     * 缩放文字大小,这样设置的好处是文字的大小不和密度有关，
     * 能够使文字大小在不同的屏幕上显示比例正确
     *
     * @param textView   button
     * @param sizePixels px值
     * @return
     */
    public static void setTextSize(TextView textView, float sizePixels) {
        float scaledSize = scaleTextValue(textView.getContext(), sizePixels);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaledSize);
    }

    /**
     * 缩放文字大小
     *
     * @param context
     * @param textPaint
     * @param sizePixels px值
     * @return
     */
    public static void setTextSize(Context context, TextPaint textPaint, float sizePixels) {
        float scaledSize = scaleTextValue(context, sizePixels);
        textPaint.setTextSize(scaledSize);
    }

    /**
     * 描述：根据屏幕大小缩放文本.
     *
     * @param context the context
     * @param value   the px value
     * @return the int
     */
    public static int scaleTextValue(Context context, float value) {
        return scaleValue(context, value);
    }

    /**
     * 描述：根据屏幕大小缩放.
     *
     * @param displayWidth  the display width
     * @param displayHeight the display height
     * @param pxValue       the px value
     * @return the int
     */
    public static int scale(int displayWidth, int displayHeight, float pxValue) {
        if (pxValue == 0) {
            return 0;
        }
        float scale = 1;
        try {
            int width = displayWidth;
            int height = displayHeight;
            //解决横屏比例问题
            if (width > height) {
                width = displayHeight;
                height = displayWidth;
            }
            float scaleWidth = (float) width / Constant.UI_WIDTH;
            float scaleHeight = (float) height / Constant.UI_HEIGHT;
            scale = Math.min(scaleWidth, scaleHeight);
        } catch (Exception e) {
        }
        return Math.round(pxValue * scale + 0.5f);
    }

    /**
     * 获得这个View的宽度
     * 测量这个view，最后通过getMeasuredWidth()获取宽度.
     *
     * @param view 要测量的view
     * @return 测量过的view的宽度
     */
    public static int getViewWidth(View view) {
        measureView(view);
        return view.getMeasuredWidth();
    }

    /**
     * 获得这个View的高度
     * 测量这个view，最后通过getMeasuredHeight()获取高度.
     *
     * @param view 要测量的view
     * @return 测量过的view的高度
     */
    public static int getViewHeight(View view) {
        measureView(view);
        return view.getMeasuredHeight();
    }


    /**
     * 测量这个view
     * 最后通过getMeasuredWidth()获取宽度和高度.
     *
     * @param view 要测量的view
     * @return 测量过的view
     */
    public static void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight,
                    View.MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * TypedValue官方源码中的算法，任意单位转换为PX单位
     *
     * @param unit    TypedValue.COMPLEX_UNIT_DIP
     * @param value   对应单位的值
     * @param metrics 密度
     * @return px值
     */
    public static float applyDimension(int unit, float value,
                                       DisplayMetrics metrics) {
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;
            case TypedValue.COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case TypedValue.COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case TypedValue.COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case TypedValue.COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }


    /**
     * 设置文本的显示隐藏 并把光标设置在最后
     *
     * @param isHidden 判断输入框的状态
     * @param editText 输入框的控件对象
     * @param img_set  被点击的图片对象
     */
    public static void setEditTextHidden(boolean isHidden, EditTextWithDel editText, ImageView img_set) {
        if (isHidden) {
            // 设置EditText文本为可见的
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            img_set.setBackgroundResource(R.mipmap.open_eyes);
        } else {
            // 设置EditText文本为隐藏的
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            img_set.setBackgroundResource(R.mipmap.close_eyes);
        }
        editText.postInvalidate();
        // 切换后将EditText光标置于末尾
        CharSequence charSequence = editText.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }


    /**
     * 把文本的光标设置在最后
     *
     * @param editText 输入框的控件对象
     */
    public static void setEditTextSelection(EditText editText) {
        editText.postInvalidate();
        // 切换后将EditText光标置于末尾
        CharSequence charSequence = editText.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }


    /**
     * 从父亲布局中移除自己
     *
     * @param v
     */
    public static void removeSelfFromParent(View v) {
        ViewParent parent = v.getParent();
        if (parent != null) {
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(v);
            }
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
//        totalHeight += 10;
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 获取本地图片
     *
     * @param context
     * @return
     */
    public static String getImage(Context context) {
        File mTempDir = new File(Environment.getExternalStorageDirectory(), "xjcs");
        if (!mTempDir.exists()) {
            mTempDir.mkdirs();
        }
        String fileName = "/logo.png";

        String path = mTempDir.getPath();//Environment.getExternalStorageDirectory().getPath();//获取手机本地存//储路径，这个位置可以根据需要自己改。

        InputStream in = context.getResources().openRawResource(R.raw.logo);//读取程序中的图片

        File file = new File(path + fileName);//创建文件
        if (!file.exists()) {
            try {//如果文件不存在就创建文件，写入图片
                file.createNewFile();
                FileOutputStream fo = new FileOutputStream(file);
                int read = in.read();
                while (read != -1) {
                    fo.write(read);
                    read = in.read();
                }
                //关闭流
                fo.flush();
                fo.close();
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return file.getAbsolutePath();
    }


//    /**
//     * 生成2维码 要转换的地址或字符串,可以是中文
//     *
//     * @param sweepIV
//     * 显示的图片控件
//     * @param url
//     * 地址
//     */
//    private static int QR_WIDTH = 700;
//    private static int QR_HEIGHT = 700;
//
//    public static void createQRImage(ImageView sweepIV, String url) {
//        try {
//            // 判断URL合法性
//            if (url == null || "".equals(url) || url.length() < 1) {
//                return;
//            }
//            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
//            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//            // 图像数据转换，使用了矩阵转换
//            BitMatrix bitMatrix = new QRCodeWriter().encode(url,
//                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
//            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
//            // 下面这里按照二维码的算法，逐个生成二维码的图片，
//            // 两个for循环是图片横列扫描的结果
//            for (int y = 0; y < QR_HEIGHT; y++) {
//                for (int x = 0; x < QR_WIDTH; x++) {
//                    if (bitMatrix.get(x, y)) {
//                        pixels[y * QR_WIDTH + x] = 0xff000000;
//                    } else {
//                        pixels[y * QR_WIDTH + x] = 0xffffffff;
//                    }
//                }
//            }
//            // 生成二维码图片的格式，使用ARGB_8888
//            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
//            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
//            // 显示到一个ImageView上面
//            sweepIV.setImageBitmap(bitmap);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//    }

}
