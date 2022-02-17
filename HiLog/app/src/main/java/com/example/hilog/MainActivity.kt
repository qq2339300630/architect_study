package com.example.hilog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.hilog.databinding.ActivityMainBinding
import com.example.hilog.demo.HiLogDemoActivity

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.btnJump.setOnClickListener {
            startActivity(Intent(this,HiLogDemoActivity::class.java))
        }
    }
}