package com.example.hilog.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.example.hilibrary.log.HiLog
import com.example.hilibrary.log.HiLogConfig
import com.example.hilibrary.log.HiLogType
import com.example.hilog.R
import com.example.hilog.databinding.ActivityHiLogDemoBinding

class HiLogDemoActivity : AppCompatActivity() {

    lateinit var binding: ActivityHiLogDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHiLogDemoBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.btnPrint.setOnClickListener {
            HiLog.log(object :HiLogConfig(){
                override fun includeThread(): Boolean {
                    return true
                }

                override fun stackTraceDepth(): Int {
                    return 0;
                }

            },HiLogType.E,"----","5566")

            HiLog.a("hello word","djkhdakh","dhdhkjah")
        }
    }
}