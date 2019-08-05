package com.example.taxishare.view.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.taxishare.R
import com.example.taxishare.view.login.LoginActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        ttv_splash.setAnimationListener {
            startActivity<LoginActivity>("pre" to "now")
            finish()
        }
        ttv_splash.animateText("This is Test Sentence")
        ttv_splash.animate()
    }
}
