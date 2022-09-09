package com.faa1192.weatherforecast

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.multidex.MultiDex
import com.faa1192.weatherforecast.databinding.ActivityIntroBinding
import com.faa1192.weatherforecast.preferred.PrefCitiesActivity

open class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MultiDex.install(this)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.introLayout)
        val image = binding.introImage
        val animation = AnimationUtils.loadAnimation(this, R.anim.intro)
        image.startAnimation(animation)
        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed({
            val intent = Intent(this, PrefCitiesActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            overridePendingTransition(R.anim.alpha_on, R.anim.alpha_off)
        }, 2000)
    }
}