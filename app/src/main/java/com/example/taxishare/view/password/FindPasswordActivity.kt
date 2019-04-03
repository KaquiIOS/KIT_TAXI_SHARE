package com.example.taxishare.view.password

import android.os.Bundle
import android.view.WindowManager
import com.example.taxishare.R
import com.example.taxishare.view.BaseActivity

class FindPasswordActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_password)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    override fun getLayoutId(): Int = R.layout.activity_find_password
}
