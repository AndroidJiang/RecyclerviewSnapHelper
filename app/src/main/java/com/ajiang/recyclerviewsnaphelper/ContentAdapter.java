package com.ajiang.recyclerviewsnaphelper;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

/**
 * Created by Solang on 2017/10/24.
 */

public class ContentAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context mContext;
    public ContentAdapter(Context mContext, int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.mContext=mContext;

    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (helper.getAdapterPosition() % 2 == 1)
            helper.itemView.setBackgroundColor(Color.RED);
        else helper.itemView.setBackgroundColor(Color.CYAN);
    }

}
