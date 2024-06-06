package com.akashi.animelistdatabase.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.akashi.animelistdatabase.R
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.akashi.animelistdatabase.ui.search.SearchActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appNameText = findViewById<TextView>(R.id.app_name_text)
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_inf)
        appNameText.startAnimation(fadeInAnimation)

        val appLogoImage = findViewById<ImageView>(R.id.logo)
        val logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        appLogoImage.startAnimation(logoAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}