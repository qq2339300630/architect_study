package com.example.hiui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hilibrary.log.HiLog;

public class HiRefreshLayout extends FrameLayout implements HiRefresh {

    private static final String TAG = HiRefreshLayout.class.getSimpleName();
    private HiOverView.HiRefreshState mState;
    private GestureDetector mGestureDetector;
    private AutoScroller mAutoScroller;
    private HiRefresh.HiRefreshListen mHiRefreshListen;
    protected HiOverView mHiOverView;
    private int mLastY;
    private boolean disableRefreshScroll;


    public HiRefreshLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public HiRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HiRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mGestureDetector = new GestureDetector(getContext(), hiGestureDetector);
        mAutoScroller = new AutoScroller();
    }

    @Override
    public void setDisableRefreshScroll(boolean disableRefreshScroll) {
        this.disableRefreshScroll = disableRefreshScroll;
    }

    @Override
    public void refreshFinished() {
        final View head = getChildAt(0);
        HiLog.i(this.getClass().getSimpleName(), "refreshFinished head-bottom: " + head.getBottom());
        mHiOverView.OnFinish();
        mHiOverView.setState(HiOverView.HiRefreshState.STATE_INIT);
        final int bottom = head.getBottom();
        if (bottom > 0) {
            recover(bottom);
        }
        mState = HiOverView.HiRefreshState.STATE_INIT;
    }

    @Override
    public void setRefreshListener(HiRefreshListen hiRefreshListen) {
        mHiRefreshListen = hiRefreshListen;
    }

    @Override
    public void setRefreshOverView(HiOverView hiOverView) {
        if (this.mHiOverView != null) {
            removeView(mHiOverView);
        }
        this.mHiOverView = mHiOverView;
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mHiOverView, 0, params);
    }

    HiGestureDetector hiGestureDetector = new HiGestureDetector() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (Math.abs(distanceX) > Math.abs(distanceY) || mHiRefreshListen != null && !mHiRefreshListen.enableRefresh()) {
                return false;
            }
            if (disableRefreshScroll && mState == HiOverView.HiRefreshState.STATE_REFRESH) {
                return true;
            }
            View head = getChildAt(0);
            View child = HiScrollUtil.findScrollableChild(HiRefreshLayout.this);
            if (HiScrollUtil.childScrolled(child)) {
                return false;
            }
            if ((mState != HiOverView.HiRefreshState.STATE_REFRESH || head.getBottom() <= mHiOverView.mPullRefreshHeight) && (head.getBottom() > 0 || distanceY <= 0.0f)) {

                if (mState != HiOverView.HiRefreshState.STATE_OVER_RELEASE) {
                    int speed;
                    if (child.getTop() < mHiOverView.mPullRefreshHeight) {
                        speed = (int) (mLastY / mHiOverView.minDamp);
                    } else {
                        speed = (int) (mLastY / mHiOverView.maxDamp);
                    }
                    boolean bool = moveDown(speed, true);
                    mLastY = (int) (-distanceY);
                    return bool;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!mAutoScroller.mIsFinished) {
            return false;
        }

        View head = getChildAt(0);
        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL || ev.getAction() == MotionEvent.ACTION_POINTER_INDEX_MASK) {
            if (head.getBottom() > 0) {
                if (mState != HiOverView.HiRefreshState.STATE_REFRESH) {
                    recover(head.getBottom());
                    return false;
                }
            }
            mLastY = 0;
        }
        boolean consumed = mGestureDetector.onTouchEvent(ev);
        HiLog.i(TAG, "gesture consumed:" + consumed);
        if ((consumed || (mState != HiOverView.HiRefreshState.STATE_INIT && mState != HiOverView.HiRefreshState.STATE_REFRESH)) && head.getBottom() != 0) {
            ev.setAction(MotionEvent.ACTION_DOWN);
            return super.dispatchTouchEvent(ev);
        }

        if (consumed) {
            return true;
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        View head = getChildAt(0);
        View child = getChildAt(1);

        if (head != null && child != null) {
            HiLog.i(TAG, "onLayout head-height:" + head.getMeasuredHeight());
            int childTop = child.getTop();
            if (mState == HiOverView.HiRefreshState.STATE_REFRESH) {
                head.layout(0, mHiOverView.mPullRefreshHeight - head.getMeasuredHeight(), right, mHiOverView.mPullRefreshHeight);
                child.layout(0, mHiOverView.mPullRefreshHeight, right, mHiOverView.mPullRefreshHeight + child.getMeasuredHeight());
            } else {
                head.layout(0, childTop - head.getMeasuredHeight(), right, mHiOverView.mPullRefreshHeight);
                child.layout(0, childTop, right, childTop + child.getMeasuredHeight());
            }

            View other;
            for (int i = 2; i < getChildCount(); i++) {
                other = getChildAt(i);
                other.layout(0, top, right, bottom);
            }
            HiLog.i(TAG, "onLayout head-bottom:" + head.getBottom());
        }
    }

    private void recover(int dis) {
        if (mHiRefreshListen != null && dis > mHiOverView.mPullRefreshHeight) {
            mAutoScroller.recover(dis - mHiOverView.mPullRefreshHeight);
            mState = HiOverView.HiRefreshState.STATE_OVER_RELEASE;
        } else {
            mAutoScroller.recover(dis);
        }
    }

    private boolean moveDown(int offsetY, boolean nonAuto) {
        HiLog.i("111", "changeState:" + nonAuto);
        View head = getChildAt(0);
        View child = getChildAt(1);
        int childTop = child.getTop() + offsetY;

        HiLog.i("-----", "moveDown head-bottom:" + head.getBottom() + ",child.getTop():" + child.getTop() + ",offsetY:" + offsetY);
        if (childTop <= 0) {
            HiLog.i(TAG, "childTop<=0,mState" + mState);
            offsetY = -child.getTop();
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
            if (mState != HiOverView.HiRefreshState.STATE_REFRESH) {
                mState = HiOverView.HiRefreshState.STATE_INIT;
            }
        } else if (mState == HiOverView.HiRefreshState.STATE_REFRESH && childTop > mHiOverView.mPullRefreshHeight) {
            return false;
        } else if (childTop <= mHiOverView.mPullRefreshHeight) {
             if (mHiOverView.getState() != HiOverView.HiRefreshState.STATE_VISIBLE && nonAuto) {
                 mHiOverView.onVisible();
                 mHiOverView.setState(HiOverView.HiRefreshState.STATE_VISIBLE);
                 mState = HiOverView.HiRefreshState.STATE_VISIBLE;
             }
             head.offsetTopAndBottom(offsetY);
             child.offsetTopAndBottom(offsetY);
             if (childTop == mHiOverView.mPullRefreshHeight && mState == HiOverView.HiRefreshState.STATE_OVER_RELEASE) {
                 HiLog.i(TAG,"refresh,childTop: " + childTop);
                 refreh();
             }
        } else {
            if (mHiOverView.getState() != HiOverView.HiRefreshState.STATE_OVER && nonAuto) {
                mHiOverView.onOver();
                mHiOverView.setState(HiOverView.HiRefreshState.STATE_OVER);
            }
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
        }
        if (mHiOverView != null) {
            mHiOverView.onScroll(head.getBottom(),mHiOverView.mPullRefreshHeight);
        }
        return true;
    }


    private void refreh() {
        if (mHiRefreshListen != null) {
            mState = HiOverView.HiRefreshState.STATE_REFRESH;
            mHiOverView.onRefresh();
            mHiOverView.setState(HiOverView.HiRefreshState.STATE_REFRESH);
            mHiRefreshListen.onRefresh();
        }
    }

    private class AutoScroller implements Runnable {

        private Scroller mScroller;
        private int mLastY;
        private boolean mIsFinished;

        public AutoScroller() {
            mScroller = new Scroller(getContext(), new LinearInterpolator());
            mIsFinished = true;
        }

        @Override
        public void run() {
            if (mScroller.computeScrollOffset()) {
                moveDown(mLastY - mScroller.getCurrX(),false);
                mLastY = mScroller.getCurrY();
                post(this);
            } else {
                removeCallbacks(this);
                mIsFinished = true;
            }
        }

        void recover(int dis) {
            if (dis <= 0) {
                return;
            }
            removeCallbacks(this);
            mLastY = 0;
            mIsFinished = false;
            mScroller.startScroll(0, 0, 0, dis, 300);
            post(this);
        }
    }
}
