package com.example.taxishare.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taxishare.R
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    val disposableManager : CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }


    override fun onDestroy() {
        super.onDestroy()
    }
}
