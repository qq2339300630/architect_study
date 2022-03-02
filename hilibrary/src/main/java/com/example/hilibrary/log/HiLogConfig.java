package com.example.hilibrary.log;

public abstract class HiLogConfig {

    static int MAX_LEN = 512;
    static HiStackTraceFormatter Hi_Stack_Trace_Formatter = new HiStackTraceFormatter();
    static HiThreadFormatter Hi_Thread_Formatter = new HiThreadFormatter();

    public JsonParser injectJsonParser() {
        return null;
    }

    public String getGlobalTag() {
        return "HiLog";
    }

    public boolean enabled() {
        return true;
    }

    public interface JsonParser {
        String toJson(Object src);
    }

    public int stackTraceDepth() {
        return 10;
    }

    public HiLogPrinter printers() {
        return null;
    }

    public boolean includeThread() {
        return false;
    }
}
