package com.example.eshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eshop.databinding.ActivityMainBinding
import androidx.databinding.DataBindingUtil.setContentView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
}