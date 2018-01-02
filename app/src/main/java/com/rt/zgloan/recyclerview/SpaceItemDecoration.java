package com.rt.zgloan.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zcy on 2017/11/8 0008.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    int topSpace;
    int bottomSpace;
    int leftSpace;
    int rightSpace;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) manager;
            //竖直方向的
            if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                //最后一项需要 bottom
                if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                    outRect.bottom = topSpace;
                }
                outRect.top = topSpace;
                outRect.left = leftSpace;
                outRect.right = rightSpace;
            } else {
                //最后一项需要right
                if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                    outRect.right = rightSpace;
                }
                outRect.top = topSpace;
                outRect.left = leftSpace;
                outRect.bottom = bottomSpace;
            }
        } else if (manager instanceof GridLayoutManager) {
            GridLayoutManager layoutManager = (GridLayoutManager) manager;
            int totalCount = layoutManager.getItemCount();
            int surplusCount = totalCount % layoutManager.getSpanCount();
            int childPosition = parent.getChildAdapterPosition(view);
            if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL) {//竖直方向的
                if (surplusCount == 0 && childPosition > totalCount - layoutManager.getSpanCount() - 1) {
                    //后面几项需要bottom
                    outRect.bottom = bottomSpace;
                } else if (surplusCount != 0 && childPosition > totalCount - surplusCount - 1) {
                    outRect.bottom = bottomSpace;
                }
                if ((childPosition + 1) % layoutManager.getSpanCount() == 0) {//被整除的需要右边
                    outRect.right = rightSpace;
                }
                outRect.top = topSpace;
                outRect.left = leftSpace;
            } else {
                if (surplusCount == 0 && childPosition > totalCount - layoutManager.getSpanCount() - 1) {
                    //后面几项需要右边
                    outRect.right = rightSpace;
                } else if (surplusCount != 0 && childPosition > totalCount - surplusCount - 1) {
                    outRect.right = rightSpace;
                }
                if ((childPosition + 1) % layoutManager.getSpanCount() == 0) {//被整除的需要下边
                    outRect.bottom = bottomSpace;
                }
                outRect.top = topSpace;
                outRect.left = leftSpace;
            }
        }
    }

    public SpaceItemDecoration(int topSpace, int bottomSpace, int leftSpace, int rightSpace) {
        this.topSpace = topSpace;
        this.bottomSpace = bottomSpace;
        this.leftSpace = leftSpace;
        this.rightSpace = rightSpace;
    }
}
