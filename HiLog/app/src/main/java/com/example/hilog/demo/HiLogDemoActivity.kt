package com.example.hilog.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.example.hilibrary.log.HiLog
import com.example.hilog.R
import com.example.hilog.databinding.ActivityHiLogDemoBinding

class HiLogDemoActivity : AppCompatActivity() {

    lateinit var binding: ActivityHiLogDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHiLogDemoBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.btnPrint.setOnClickListener {
            HiLog.a("hello word","djkhdakh","dhdhkjah")
        }
    }
}