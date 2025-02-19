package com.example.film

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, systemBars.bottom)
            insets
        }

        val vp = findViewById<ViewPager2>(R.id.vp2)
        vp.isUserInputEnabled = false
        val fradapt = fragmentstateadapter(this)
        val btnv = findViewById<LinearLayout>(R.id.bottom)
        vp.adapter = fradapt
        val shopnav = findViewById<ImageView>(R.id.shopnav)
        val movienav = findViewById<ImageView>(R.id.movienav)
        val filmlerimnav = findViewById<ImageView>(R.id.filmlerimnav)
        val hesabimnav = findViewById<ImageView>(R.id.hesabimnav)
        val shopping = findViewById<ImageView>(R.id.imageView10)

        vp.offscreenPageLimit = 3

        shopnav.setOnClickListener {
            vp.currentItem = 0
        }

        movienav.setOnClickListener {
            vp.currentItem = 1
        }

        shopping.setOnClickListener {
            val siparisintent = Intent(this, siparis::class.java)
            startActivity(siparisintent)
        }

        filmlerimnav.setOnClickListener {
            vp.currentItem = 2
        }

        hesabimnav.setOnClickListener {
            vp.currentItem = 3
        }

    }

}