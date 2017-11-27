package com.rt.zgloan.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.rt.zgloan.R;


/**
 * EditText自带删除
 *
 * @author Administrator
 */
public class EditTextWithDel extends AppCompatEditText {

    private Context mContext;
    private Drawable imgInable;

    public EditTextWithDel(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    @SuppressLint("Instantiatable")
    public EditTextWithDel(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        imgInable = mContext.getResources().getDrawable(R.mipmap.et_clear);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }

        });
        setDrawable();
    }

    /**
     * 判断edittext 是否有文字输入设置图片
     */
    private void setDrawable() {
        if (length() > 0) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, imgInable, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgInable != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - imgInable.getIntrinsicWidth() - 40;
            if (rect.left < eventX && eventX < rect.right) {
                setText("");
            } else {
                if (eventX > rect.left && eventX > rect.right) {
                    setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }
}
