package com.example.hiui.tab.bottom;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

public class HiTabBottomInfo<Color> {
    public enum TabType {
        BITMAP,ICON
    }
    public Class<? extends Fragment> fragment;
    public String name;
    public Bitmap defaultBitmap;
    public Bitmap selectedBitmap;
    public String iconFont;

    public String defaultIconName;
    public String selectedIconName;
    public Color defaultColor;
    public Color tintColor;
    public TabType tabType;

    public HiTabBottomInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.tabType = TabType.BITMAP;
    }

    public HiTabBottomInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap, String iconFont, Color defaultColor, Color tintColor) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.iconFont = iconFont;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabType.ICON;
    }
}