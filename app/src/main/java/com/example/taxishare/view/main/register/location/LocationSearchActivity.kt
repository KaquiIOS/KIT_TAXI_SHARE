package com.example.taxishare.view.main.register.location

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.example.taxishare.R
import com.example.taxishare.app.Constant
import com.example.taxishare.data.model.Location
import com.example.taxishare.data.remote.apis.server.ServerClient
import com.example.taxishare.view.main.register.location.history.LocationHistoryFragment
import com.example.taxishare.view.main.register.location.search.LocationSearchFragment
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.activity_location_search.*
import java.util.concurrent.TimeUnit


class LocationSearchActivity : AppCompatActivity(), LocationSearchView, GoogleApiClient.OnConnectionFailedListener,
    LocationSelectionListener {

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

        et_search_location.textChanges()
            .debounce(Constant.EDIT_TEXT_DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
            .subscribe({
                // 검색창이 빈 경우에 기록 화면 보여주기
                if (it.isEmpty()) {
                    Log.d("Test", "1\n$it")
                    changeFragment(locationHistoryFragment)
                }
                // 검색어가 입력되었을 때는 검색 화면 보여주기
                changeFragment(locationSearchFragment)
                presenter.searchLocation(it.toString())

            }, {
                it.printStackTrace()
            })

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
