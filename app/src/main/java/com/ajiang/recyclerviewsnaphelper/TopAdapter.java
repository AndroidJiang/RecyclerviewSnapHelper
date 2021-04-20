package com.ajiang.recyclerviewsnaphelper;

import android.content.Context;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

/**
 * Created by Solang on 2017/10/24.
 */

public class TopAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context mContext;
    public TopAdapter(Context mContext, int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.mContext=mContext;

    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {


    }

}
