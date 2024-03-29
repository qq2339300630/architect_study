package com.example.hilibrary.log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HiLogManager {
    private HiLogConfig config;
    private static HiLogManager instance;
    public List<HiLogPrinter> printers = new ArrayList<>();


    private HiLogManager(HiLogConfig config,HiLogPrinter ... printers) {
        this.config = config;
        this.printers.addAll((Arrays.asList(printers)));
    }

    public void addPrinter(HiLogPrinter printer) {
        printers.add(printer);
    }

    public void removePrinter(HiLogPrinter printer) {
        printers.remove(printer);
    }

    public static HiLogManager getInstance() {
        return instance;
    }

    public static void init(@NonNull HiLogConfig config,HiLogPrinter ... printers) {
        instance = new HiLogManager(config,printers);
    }

    public HiLogConfig getConfig() {
        return config;
    }
}
