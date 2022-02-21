package com.example.hilog

import android.app.Application
import com.example.hilibrary.log.HiConsolePrinter
import com.example.hilibrary.log.HiLogConfig
import com.example.hilibrary.log.HiLogManager
import com.google.gson.Gson

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        HiLogManager.init(object : HiLogConfig() {
            override fun injectJsonParser(): JsonParser {
                return JsonParser { src -> Gson().toJson(src) }
            }

            override fun getGlobalTag(): String {
                return "caojiajun"
            }

            override fun enabled(): Boolean {
                return true
            }
        },HiConsolePrinter())
    }
}