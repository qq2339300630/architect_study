package com.example.hiui.tab.common;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

public interface IHiTab<D> extends IHiTabLayout.OnTabSelectedListener<D> {
    void setHiTabInfo(@NonNull D data);

    void resetHeight(@Px int height);
}
