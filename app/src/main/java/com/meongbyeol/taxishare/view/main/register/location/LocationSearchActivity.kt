package com.meongbyeol.taxishare.view.main.register.location

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.meongbyeol.taxishare.R
import com.meongbyeol.taxishare.app.Constant
import com.meongbyeol.taxishare.data.local.room.AppDatabase
import com.meongbyeol.taxishare.data.mapper.TypeMapper
import com.meongbyeol.taxishare.data.model.Location
import com.meongbyeol.taxishare.data.remote.apis.server.ServerClient
import com.meongbyeol.taxishare.data.repo.LocationRepositoryImpl
import com.meongbyeol.taxishare.data.repo.MyLocationRepositoryImpl
import com.meongbyeol.taxishare.data.repo.ServerRepositoryImpl
import com.meongbyeol.taxishare.view.main.register.location.dialog.MyLocationRegisterDialog
import com.meongbyeol.taxishare.view.main.register.location.history.LocationHistoryFragment
import com.meongbyeol.taxishare.view.main.register.location.search.LocationSearchFragment
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.activity_location_search.*
import java.util.concurrent.TimeUnit


class LocationSearchActivity : AppCompatActivity(), LocationSearchView, GoogleApiClient.OnConnectionFailedListener,
    LocationSelectionListener, LocationLongClickListener {

    private val locationHistoryFragment: LocationHistoryFragment by lazy {
        LocationHistoryFragment.newInstance().apply {
            setLocationSelectedListener(this@LocationSearchActivity)
            setLocationItemLongClickListener(this@LocationSearchActivity)
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
            ServerRepositoryImpl.getInstance(ServerClient.getInstance()),
            LocationRepositoryImpl.getInstance(AppDatabase.getInstance(applicationContext), TypeMapper)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_search)

        initView()
        initListener()
    }

    override fun locationSelected(location: Location) {
        presenter.saveSelectedLocation(location)
    }

    override fun locationSaveFinish(location: Location) {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra(Constant.LOCATION_SAVE_STR, location)
        }).apply {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun locationLongClicked(selectedLocation: Location) {
        MyLocationRegisterDialog.newInstance(
            MyLocationRepositoryImpl.getInstance(
                AppDatabase.getInstance(this),
                TypeMapper
            ), selectedLocation
        ).show(supportFragmentManager, "TAG")
    }

    @SuppressWarnings("all")
    private fun initView() {

        et_search_location.hint = intent.getStringExtra(Constant.LOCATION_SEARCH_HINT)

        et_search_location.textChanges().skipInitialValue()
            .debounce(Constant.EDIT_TEXT_DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
            .subscribe({
                // 검색창이 빈 경우에 기록 화면 보여주기
                if (it.isEmpty()) {
                    changeFragment(locationHistoryFragment)
                } else if (it.length > 1) {
                    changeFragment(locationSearchFragment)
                    presenter.searchLocation(it.toString())
                }
            }, {
                it.printStackTrace()
            })

        changeFragment(locationHistoryFragment)
    }

    private fun initListener() {
        tb_search_location.setNavigationOnClickListener {
            finish()
        }
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
