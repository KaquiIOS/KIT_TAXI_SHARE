/*
 * Created by WonJongSeong on 2019-03-24
 */

package com.example.taxishare.view

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity : AppCompatActivity() {

    protected val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    abstract fun getLayoutId() : Int
}