package com.akashi.animelistdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

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
    }
}