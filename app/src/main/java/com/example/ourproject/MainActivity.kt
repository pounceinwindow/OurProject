package com.example.ourproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ourproject.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }
}


