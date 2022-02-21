package com.example.hilog.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.example.hilibrary.log.*
import com.example.hilog.R
import com.example.hilog.databinding.ActivityHiLogDemoBinding

class HiLogDemoActivity : AppCompatActivity() {

    lateinit var binding: ActivityHiLogDemoBinding
    var viewPrinter: HiViewPrinter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHiLogDemoBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        viewPrinter = HiViewPrinter(this)
        viewPrinter!!.viewProvider.showFloatingView()
        binding.btnPrint.setOnClickListener {
            HiLogManager.getInstance().addPrinter(viewPrinter)
            HiLog.log(object : HiLogConfig() {
                override fun includeThread(): Boolean {
                    return true
                }

                override fun stackTraceDepth(): Int {
                    return 0;
                }

            }, HiLogType.E, "----", "5566")

            HiLog.a("hello word", "djkhdakh", "dhdhkjah")
        }
    }
}