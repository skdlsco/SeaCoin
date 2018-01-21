package com.seacoin.seacoin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logoAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_logo).apply {
            duration = 1000
            fillAfter = true
        }
        val buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_btn).apply {
            duration = 1000
            fillAfter = true
        }
        logoContainer.startAnimation(logoAnimation)
        startBtn.startAnimation(buttonAnimation)
        startBtn.setOnClickListener {
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }
    }
}
