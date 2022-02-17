package com.example.hilog

import android.app.Application
import com.example.hilibrary.log.HiLogConfig
import com.example.hilibrary.log.HiLogManager

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        HiLogManager.init(object :HiLogConfig(){
            override fun getGlobalTag(): String {
                return "caojiajun"
            }

            override fun enabled(): Boolean {
                return true
            }
        })
    }
}