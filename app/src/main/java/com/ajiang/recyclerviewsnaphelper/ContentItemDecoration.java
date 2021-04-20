package com.ajiang.recyclerviewsnaphelper;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


public class ContentItemDecoration extends RecyclerView.ItemDecoration {
    private int mLeftMargin;

    public ContentItemDecoration(Context context) {
        mLeftMargin = (Globals.SCREEN_WIDTH - ScreenUtils.dp2px(context,327)) / 2;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();
        int leftMargin;
        int rightMargin;
        if (position == 0) {
            leftMargin = mLeftMargin;
        } else {
            leftMargin = 0;
        }
        if (position == itemCount - 1) {
            rightMargin = mLeftMargin;
        } else {
            rightMargin = 0;
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        layoutParams.setMargins(leftMargin, 0, rightMargin, 0);
        super.getItemOffsets(outRect, view, parent, state);
    }
}
