package com.example.taxishare.view.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieDrawable
import com.example.taxishare.R
import com.example.taxishare.view.login.LoginActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splash_lottie_view.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                debug { println("SplashEnd") }
                //val intent = intentFor<MainActivity>("pre" to "splash").noHistory()
                startActivity<LoginActivity>("pre" to "now")
                finish()
            }
        })

        // REPEAT 설정
        splash_lottie_view.repeatCount = 1
        //splash_lottie_view.repeatMode = LottieDrawable.RESTART
        splash_lottie_view.playAnimation()
    }
}
