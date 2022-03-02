package com.example.hiui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hilibrary.util.ScreenUtil;

public abstract class HiOverView extends FrameLayout {

    public enum HiRefreshState {
        STATE_INIT,
        STATE_VISIBLE,
        STATE_OVER,
        STATE_REFRESH,
        STATE_OVER_RELEASE
    }

    protected HiRefreshState mState = HiRefreshState.STATE_INIT;

    public int mPullRefreshHeight;

    public float minDamp = 1.6f;

    public float maxDamp = 2.2f;


    public HiOverView(@NonNull Context context) {
        super(context);
        preInit();
    }

    public HiOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        preInit();
    }

    public HiOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        preInit();
    }

    protected void preInit() {
        mPullRefreshHeight = ScreenUtil.dip2px(66);
        init();
    }

    protected abstract void init();

    protected abstract void onScroll(int scrollY,int pullRefreshHeight);

    protected abstract void onVisible();

    public abstract void onOver();

    public abstract void onRefresh();

    public abstract void OnFinish();

    public void setState(HiRefreshState state) {mState = state;}

    public HiRefreshState getState() {
        return mState;
    }
}
