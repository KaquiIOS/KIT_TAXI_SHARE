package com.example.taxishare.view.main.register.location

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taxishare.R
import com.example.taxishare.app.Constant
import kotlinx.android.synthetic.main.activity_location_search.*

class LocationSearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_search)

        et_search_location.hint = intent.getStringExtra(Constant.LOCATION_SEARCH_HINT)

    }
}
