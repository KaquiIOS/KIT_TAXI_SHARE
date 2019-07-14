package com.example.taxishare.view.main.register.location

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.taxishare.R
import com.example.taxishare.app.Constant
import com.example.taxishare.data.model.Location
import com.example.taxishare.data.remote.apis.server.ServerClient
import com.example.taxishare.data.repo.ServerRepositoryImpl
import com.example.taxishare.view.main.register.location.history.LocationHistoryFragment
import com.example.taxishare.view.main.register.location.search.LocationSearchFragment
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
            ServerRepositoryImpl.getInstance(ServerClient.getInstance())
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_search)

        initView()
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

    override fun locationLongClicked(selectedLocation: Location) {
        // TODO : 추가 다이얼로그를 출력한다.
        // 다이얼로그를 통해 정보를 입력하고
        // 같은 이름이 존재하는 경우 -> warning 메시지 출력
        // 같은 이름이 존재하지 않는 경우 -> 그냥 등록
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
