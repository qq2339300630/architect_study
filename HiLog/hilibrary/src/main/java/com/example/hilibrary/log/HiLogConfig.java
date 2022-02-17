package com.example.hilibrary.log;

public abstract class HiLogConfig {
    public String getGlobalTag() {
        return "HiLog";
    }

    public boolean enabled() {
        return true;
    }
}
