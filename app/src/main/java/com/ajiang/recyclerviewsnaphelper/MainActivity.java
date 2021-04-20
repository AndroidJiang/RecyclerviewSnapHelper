package com.ajiang.recyclerviewsnaphelper;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.internal.CustomAdapt;

public class MainActivity extends AppCompatActivity implements CustomAdapt {

    private RecyclerView mRvTop;
    private RecyclerView mRvContent;
    private ContentAdapter contentAdapter;
    private TopAdapter topAdapter;
    private LinearLayoutManager topManager;
    private LinearLayoutManager contentManager;
    private int mTotalContentX;
    private int mTotalTopX;
    private int mGalleryMiddle;
    private int mTopMove;
    private int mGalleryWidth;
    private int mTopWidth;
    private int mGalleryMove;
    private float mGalleryScaleY = 0.3f;
    private int mGalleryTranslation;
    private int mTopTranslation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mGalleryWidth = ScreenUtils.dp2px(this,327);
        mTopWidth = ScreenUtils.dp2px(this,100);
        Globals.SCREEN_WIDTH = ScreenUtils.getRelScreenWidth();
        Globals.SCREEN_HEIGHT = ScreenUtils.getRelScreenHeight();
        mGalleryMiddle = Globals.SCREEN_WIDTH / 2;
        mTopMove = mTopWidth * 2;
        mGalleryMove = mGalleryWidth / 2;
        mTopTranslation = mTopMove / 3;
        mGalleryTranslation = (int) (mGalleryMove * mGalleryScaleY - ScreenUtils.dp2px(this,12));

        initView();
        initRvHead();
        initRvFoot();
    }

    private void initView() {
        mRvTop = (RecyclerView) findViewById(R.id.rv_top);
        mRvContent = (RecyclerView) findViewById(R.id.rv_content);
        contentManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        topManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        mRvTop.setLayoutManager(topManager);
        mRvContent.setLayoutManager(contentManager);
        mRvTop.addItemDecoration(new TopItemDecoration(this));
        mRvContent.addItemDecoration(new ContentItemDecoration(this));
        PagerSnapHelper snapHelperTop=new PagerSnapHelper();
        snapHelperTop.attachToRecyclerView(mRvTop);
        PagerSnapHelper snapHelperContent=new PagerSnapHelper();
        snapHelperContent.attachToRecyclerView(mRvContent);
        mRvTop.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return true;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        mRvTop.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mTotalTopX += dx;
                for (int i = 0; i < mRvTop.getChildCount(); i++) {
                    View view = mRvTop.getChildAt(i);
                    float rate;
                    int left = (view.getLeft() + view.getRight()) / 2;
                    view.setPivotY(0);
                    if (left <= mGalleryMiddle) {
                        if (left < mGalleryMiddle - mTopMove) {
                            rate = 1f;
                        } else {
                            rate = (mGalleryMiddle - left) * 1f / mTopMove;
                        }
                        if (rate > 0.5) {
                            view.setTranslationX((rate - 0.5f) * mTopTranslation);
                            view.setScaleY(0.8f - rate * 0.5f);
                            view.setScaleX(0.8f - rate * 0.5f);
                        } else {
                            view.setTranslationX(0);
                            view.setScaleY(1 - rate * 0.9f);
                            view.setScaleX(1 - rate * 0.9f);
                        }
                    } else {
                        if (left > mGalleryMiddle + mTopMove) {
                            rate = 0f;
                        } else {
                            rate = (mGalleryMiddle + mTopMove - left) * 1f / mTopMove;
                        }
                        if (rate < 0.5) {
                            view.setTranslationX((rate - 0.5f) * mTopTranslation);
                            view.setScaleY(0.3f + rate * 0.5f);
                            view.setScaleX(0.3f + rate * 0.5f);
                        } else {
                            view.setTranslationX(0);
                            view.setScaleY(0.1f + rate * 0.9f);
                            view.setScaleX(0.1f + rate * 0.9f);
                        }
                    }
                }
            }
        });

        mRvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mTotalContentX += dx;
                mRvTop.scrollBy(mTotalContentX * 100 / 327 - mTotalTopX, dy);
                if (mRvContent.getChildCount() == 1) {
                    View view = mRvContent.getChildAt(0);
                    view.setScaleY(1);
                    view.setScaleX(1);
                } else {
                    for (int i = 0; i < mRvContent.getChildCount(); i++) {
                        View view = mRvContent.getChildAt(i);
                        float rate;
                        int left = (view.getLeft() + view.getRight()) / 2;
                        if (left <= mGalleryMiddle) {
                            if (left < mGalleryMiddle - mGalleryMove) {
                                rate = 1f;
                            } else {
                                rate = (mGalleryMiddle - left) * 1f / mGalleryMove;

                            }
                            view.setScaleY(1 - rate * mGalleryScaleY);
                            view.setScaleX(1 - rate * mGalleryScaleY);
                            view.setTranslationX(rate * mGalleryTranslation);
                        } else {
                            if (left > mGalleryMiddle + mGalleryMove) {
                                rate = 0f;
                            } else {
                                rate = (mGalleryMiddle + mGalleryMove - left) * 1f / mGalleryMove;
                            }
                            view.setScaleY((1 - mGalleryScaleY) + rate * mGalleryScaleY);
                            view.setScaleX((1 - mGalleryScaleY) + rate * mGalleryScaleY);
                            view.setTranslationX((rate - 1) * mGalleryTranslation);
                        }
                    }
                }
            }
        });
    }
    private void initRvHead() {
        List list = new ArrayList();
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("4");
        list.add("4");
        list.add("4");
        list.add("4");
        list.add("4");

        topAdapter = new TopAdapter(this,R.layout.item_top, list);
        mRvTop.setAdapter(topAdapter);
    }

    private void initRvFoot() {
        ArrayList list = new ArrayList<>();
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("4");
        list.add("4");
        list.add("4");
        list.add("4");
        list.add("4");
        contentAdapter = new ContentAdapter(this,R.layout.item_content, list);
        mRvContent.setAdapter(contentAdapter);
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 0;
    }
}
