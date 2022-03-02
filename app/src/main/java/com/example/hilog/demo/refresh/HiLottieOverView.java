package com.example.hilog.demo.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hiui.refresh.HiOverView;

public class HiLottieOverView extends HiOverView {
    public HiLottieOverView(@NonNull Context context) {
        super(context);
    }

    public HiLottieOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HiLottieOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        LayoutInflater.from(getContext()).inflate()
    }

    @Override
    protected void onScroll(int scrollY, int pullRefreshHeight) {

    }

    @Override
    protected void onVisible() {

    }

    @Override
    public void onOver() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void OnFinish() {

    }
}
