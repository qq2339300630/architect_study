package com.example.hilibrary.log;

public class HiStackTraceFormatter implements HiLogFormatter<StackTraceElement[]> {
    @Override
    public String format(StackTraceElement[] data) {
        StringBuilder sb = new StringBuilder();
        if (data == null || data.length == 0) {
            return null;
        } else if (data.length == 1) {
            return "\t-" + data[0].toString();
        } else {
            for (int i = 0; i < data.length; i++) {
                if (i == 0) {
                    sb.append("stackTrace: \n");
                } else if (i != data.length - 1) {
                    sb.append("\t|-");
                    sb.append(data[i].toString());
                    sb.append("\n");
                } else {
                    sb.append("\t[");
                    sb.append(data[i].toString());
                }
            }
        }
        return sb.toString();
    }
}
