package com.rt.zgloan.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.rt.zgloan.R;

/**
 * Created by Administrator on 2017/9/1.
 */

public class RadiusView extends View {
    private Paint paint;

    public RadiusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF();
        rectF.left = 0;
        rectF.right = this.getWidth();
        rectF.top = 0;
        rectF.bottom = this.getHeight();
        canvas.drawRoundRect(rectF, 25, 25, paint);
    }

    public void setBackgroundColor(String colorCode) {
        paint.setColor(Color.parseColor("#" + colorCode));
        invalidate();
    }
}
