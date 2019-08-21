package com.example.taxishare.view.splash

import android.animation.Animator
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieDrawable
import com.example.taxishare.R
import com.example.taxishare.view.login.LoginActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity


class SplashActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initView()
        initAnimationSetting()
        initListener()
        startAnimation()

    }

    private fun initView() {
        ttv_splash.animateText(getString(com.example.taxishare.R.string.taxi_share_splash))
    }

    private fun initAnimationSetting() {
        ttv_splash.typerSpeed = 100
        ttv_splash.charIncrease = 1

        splash_lottie_view.setAnimation("splash_car_asset.json")
        splash_lottie_view.repeatMode = LottieDrawable.RESTART

    }

    private fun initListener() {
        ttv_splash.setAnimationListener {
            startActivity<LoginActivity>("pre" to "now")
            finish()
        }
    }

    private fun startAnimation() {

        splash_lottie_view.playAnimation()

        ttv_splash.animate()
    }
}
