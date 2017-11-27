package com.rt.zgloan.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.rt.zgloan.R;
import com.rt.zgloan.util.Tool;

import java.util.List;

/**
 * 首页消息向上滚动view
 */

public class RollView extends View {
    private Paint mTextPaint;
    private Context mContext;
    private List<String> content;
    private int lineNumber = 1;
    private int viewHeight;
    private int viewWidth;
    private boolean textCenter;
    private float textDrawX;
    private float nextTextDrawX;

    private int position = 0;

    private float playHeight = 0;
    private float playCountHeight;
    private long sleepTime = 5000;

    private boolean isStop = false;

    private Thread thread;

    public int getLineNumber() {
        return lineNumber;
    }

    public RollView(Context context) {
        this(context, null);
    }

    public RollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        if (content != null && content.size() > 0) {
            this.content = content;
            play();
        }
    }

    private void init() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(Tool.sp2px(mContext, 12));//文字大小
        mTextPaint.setColor(ContextCompat.getColor(mContext, R.color.rollview));//文字颜色
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (content != null && content.size() > 0) {
            if (textCenter) {
                float textWidth = mTextPaint.measureText(content.get(position));
                textDrawX = (viewWidth - textWidth) / 2;
            } else {
                textDrawX = 0;
            }
            //画显示的文字
            int y = getTxtHeightContent(mTextPaint);
            canvas.drawText(content.get(position), textDrawX, y - playHeight, mTextPaint);
            float textHeight = mTextPaint.getFontMetrics().bottom - mTextPaint.getFontMetrics().top;

            playCountHeight = viewHeight + textHeight - y;
            int nextPosition;
            if (position < content.size() - 1) {
                nextPosition = position + 1;
            } else {
                nextPosition = 0;
            }
            if (textCenter) {
                float textWidth = mTextPaint.measureText(content.get(nextPosition));
                textDrawX = (viewWidth - textWidth) / 2;
            } else {
                textDrawX = 0;
            }
            canvas.drawText(content.get(nextPosition), textDrawX, viewHeight + textHeight - playHeight, mTextPaint);
        }
    }

    public void play() {
        if (null == content || content.size() == 0) {
            return;
        }
        if (isStop) {
            isStop = false;
        }
        if (thread == null || !thread.isAlive()) {
            thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    postInvalidate();
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    while (!isStop || playHeight != 0) {
                        try {
                            if (playHeight <= playCountHeight) {
                                playHeight++;
                                Thread.sleep(5);
                                postInvalidate();
                            } else {
                                playHeight = 0;
                                if (position < content.size() - 1) {
                                    position++;
                                } else {
                                    position = 0;
                                }
                                Thread.sleep(sleepTime);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            thread.start();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        viewHeight = getHeight();
        viewWidth = getWidth();
    }

    /**
     * 获取文字上下居中的Y轴坐标点
     *
     * @param mPaint
     * @return
     */
    public int getTxtHeightContent(Paint mPaint) {
        Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();
        //return getHeight()/2-fm.descent+(fm.bottom-fm.top)/2;//含英文特殊字符使用这种
        return getHeight() / 2 - fm.descent + (fm.descent - fm.ascent) / 2;//汉字数字使用这种
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isStop = true;
    }

    public void onStop() {
        isStop = true;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isTextCenter() {
        return textCenter;
    }

    public void setTextCenter(boolean textCenter) {
        this.textCenter = textCenter;
    }
}
