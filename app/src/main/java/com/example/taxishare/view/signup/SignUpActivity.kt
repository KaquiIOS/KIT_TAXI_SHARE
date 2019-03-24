package com.example.taxishare.view.signup

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.taxishare.R
import com.example.taxishare.view.BaseActivity
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        iv_sign_up_profile_image.clipToOutline = true
    }

    override fun getLayoutId(): Int = R.layout.activity_sign_up
}
