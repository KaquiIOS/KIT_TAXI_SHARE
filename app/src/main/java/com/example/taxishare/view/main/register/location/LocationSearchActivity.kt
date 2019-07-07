package com.example.taxishare.view.main.register.location

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.example.taxishare.BuildConfig
import com.example.taxishare.R
import com.example.taxishare.app.Constant
import com.example.taxishare.data.model.Location
import com.example.taxishare.data.remote.apis.server.ServerClient
import com.example.taxishare.view.main.register.location.history.LocationHistoryFragment
import com.example.taxishare.view.main.register.location.search.LocationSearchFragment
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.libraries.places.api.Places
import kotlinx.android.synthetic.main.activity_location_search.*


class LocationSearchActivity : AppCompatActivity(), LocationSearchView, GoogleApiClient.OnConnectionFailedListener, LocationSelectionListener {

    private val locationHistoryFragment: LocationHistoryFragment by lazy {
        LocationHistoryFragment.newInstance().apply {
            setLocationSelectedListener(this@LocationSearchActivity)
        }
    }

    private val locationSearchFragment: LocationSearchFragment by lazy {
        LocationSearchFragment.newInstance().apply {
            setLocationSelectedListener(this@LocationSearchActivity)
        }
    }


    private val presenter: LocationSearchPresenter by lazy {
        LocationSearchPresenter(
            this,
            ServerClient.getInstance()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_search)

        if (!Places.isInitialized()) {
            Places.initialize(this@LocationSearchActivity, BuildConfig.maps_api_key)
        }

        initView()
        initListener()
    }

    override fun locationSelected(location: Location) {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra(resources.getString(R.string.intent_location), location)
        }).apply {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    @SuppressWarnings("all")
    private fun initView() {
        et_search_location.hint = intent.getStringExtra(Constant.LOCATION_SEARCH_HINT)

        et_search_location.doAfterTextChanged {

            // 검색창이 빈 경우에 기록 화면 보여주기
            if (et_search_location.text.isEmpty()) {
                changeFragment(locationHistoryFragment)
            }
            // 검색어가 입력되었을 때는 검색 화면 보여주기
            else if (!locationSearchFragment.isVisible) {
                changeFragment(locationSearchFragment)
            }

            if (locationSearchFragment.isVisible && et_search_location.length() > 1) {
                presenter.searchLocation(et_search_location.text.toString())
            }
        }

        changeFragment(locationHistoryFragment)
    }

    private fun initListener() {

    }

    private fun changeFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().replace(R.id.cl_location_search_fragment, fragment).commit()

    override fun onBackPressed() {
        super.onBackPressed()

        when (locationSearchFragment.isVisible) {
            true -> changeFragment(locationHistoryFragment)
            else -> finish()
        }
    }

    override fun displaySearchedLocationList(searchList: MutableList<Location>) {
        if (locationSearchFragment.isVisible) {
            locationSearchFragment.setLocationSearchList(searchList)
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
