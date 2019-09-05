package com.meongbyeol.taxishare.view.splash

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieDrawable
import com.meongbyeol.taxishare.R
import com.meongbyeol.taxishare.app.Constant
import com.meongbyeol.taxishare.view.login.LoginActivity
import com.tedpark.tedpermission.rx2.TedRx2Permission
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity


class SplashActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        requestPermissions()
    }

    @SuppressWarnings("all")
    private fun requestPermissions() {
        TedRx2Permission.with(this)
            .setRationaleTitle(R.string.splash_permission_request_title)
            .setRationaleMessage(R.string.splash_permission_request_content)
            .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
            .request()
            .subscribe({ tedPermissionResult ->
                if (tedPermissionResult.isGranted) {
                    initView()
                    initAnimationSetting()
                    initListener()
                    startAnimation()
                } else {

                    AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setTitle(R.string.splash_permission_request_title)
                        .setMessage(R.string.splash_permission_request_content)
                        .setPositiveButton("확인", { dialogInterface, _ ->
                            dialogInterface.dismiss()
                            requestPermissions()
                        })
                        .setNegativeButton("앱 종료", { dialogInterface, _ ->
                            dialogInterface.dismiss()
                            finish()
                        })
                        .create()
                        .show()

                }
            }, { throwable -> throwable.printStackTrace() })
    }

    private fun initView() {
        ttv_splash.animateText(getString(com.meongbyeol.taxishare.R.string.taxi_share_splash))
    }

    private fun initAnimationSetting() {
        ttv_splash.typerSpeed = 100
        ttv_splash.charIncrease = 1

        splash_lottie_view.setAnimation("splash_car_asset.json")
        splash_lottie_view.repeatMode = LottieDrawable.RESTART

    }

    private fun initListener() {
        ttv_splash.setAnimationListener {

            when (Constant.APP_VERSION) {
                1.9 -> {
                    startActivity<LoginActivity>("pre" to "now")
                    finish()
                }
                else -> AlertDialog.Builder(this)
                    .setTitle("앱 버전 업데이트 필요")
                    .setMessage("최신 버전 업데이트가 필요합니다 !")
                    .setPositiveButton(R.string.ok, { _, _ ->
                        val appPackageName = packageName
                        try {
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$appPackageName")
                            ).apply {
                                startActivity(this)
                                finish()
                            }
                        } catch (anfe: android.content.ActivityNotFoundException) {
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                            ).apply {
                                startActivity(this)
                                finish()
                            }
                        }
                    })
                    .setNegativeButton(R.string.cancel, { _, _ -> finish() })
                    .setCancelable(false)
                    .create()
                    .show()
            }
        }
    }

    private fun startAnimation() {

        splash_lottie_view.playAnimation()

        ttv_splash.animate()
    }
}
