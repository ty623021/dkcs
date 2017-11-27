package com.rt.zgloan.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zcy on 2017/11/8 0008.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    int mSpace;
    int topSpace;
    int bottomSpace;
    int leftSpace;
    int rightSpace;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = leftSpace;
        outRect.right = rightSpace;
        outRect.bottom = bottomSpace;
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = topSpace;
        }

    }

    //    public SpaceItemDecoration(int space) {
//        this.mSpace = space;
//    }
    public SpaceItemDecoration(int topSpace, int bottomSpace, int leftSpace, int rightSpace) {
        this.topSpace = topSpace;
        this.bottomSpace = bottomSpace;
        this.leftSpace = leftSpace;
        this.rightSpace = rightSpace;
    }
}
