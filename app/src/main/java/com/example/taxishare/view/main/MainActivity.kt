package com.example.taxishare.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.taxishare.R
import com.example.taxishare.app.Constant
import com.example.taxishare.data.model.Location
import com.example.taxishare.data.model.TaxiShareInfo
import com.example.taxishare.view.main.mypage.MyPageFragment
import com.example.taxishare.view.main.register.RegisterTaxiShareActivity
import com.example.taxishare.view.main.register.location.LocationSearchActivity
import com.example.taxishare.view.main.taxisharelist.TaxiShareListFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivityForResult


class MainActivity : AppCompatActivity(), MainView, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mainPresenter: MainPresenter
    private lateinit var taxiShareListFragment: TaxiShareListFragment
    private lateinit var myPageFragment: MyPageFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListener()
        initPresenter()
        initView()
    }

    private fun initListener() {

        nav_view.setNavigationItemSelectedListener(this)

        btn_taxi_list_filter.onClick {
            if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
                drawer_layout.closeDrawer(GravityCompat.END)
            } else {
                drawer_layout.openDrawer(GravityCompat.END)
            }
        }

        btn_taxi_list_reload.onClick {
            if (taxiShareListFragment.isVisible) {
                taxiShareListFragment.reloadTaxiShareList()
            }
        }

        bnv_main.setOnNavigationItemSelectedListener {
            if (it.itemId == R.id.nav_register_taxi_share) {
                startActivityForResult<RegisterTaxiShareActivity>(Constant.REGISTER_TAXI_SHARE)
                false
            } else {
                when (it.itemId) {
                    R.id.nav_taxi_share_list -> changeFragment(taxiShareListFragment)
                    else -> changeFragment(myPageFragment)
                }
                true
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REGISTER_TAXI_SHARE && data != null) {
            val taxiShareInfo: TaxiShareInfo =
                data.getSerializableExtra(Constant.REGISTER_TAXI_SHARE_STR) as TaxiShareInfo
            taxiShareListFragment.addTaxiShareInfo(taxiShareInfo)
        } else if (taxiShareListFragment.isVisible && requestCode == Constant.START_LOCATION_SEARCH_CODE && data != null) {
            val location = data.getSerializableExtra(Constant.LOCATION_SAVE_STR) as Location
            taxiShareListFragment.setStartLocation(location)
            nav_view.menu[0].subMenu[0].title = location.locationName
        } else if (taxiShareListFragment.isVisible && requestCode == Constant.END_LOCATION_SEARCH_CODE && data != null) {
            val location = data.getSerializableExtra(Constant.LOCATION_SAVE_STR) as Location
            taxiShareListFragment.setEndLocation(location)
            nav_view.menu[1].subMenu[0].title = location.locationName
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_start_location -> {
                startActivityForResult<LocationSearchActivity>(Constant.START_LOCATION_SEARCH_CODE)
            }
            R.id.nav_end_location -> {
                startActivityForResult<LocationSearchActivity>(Constant.END_LOCATION_SEARCH_CODE)
            }
            R.id.nav_start_time -> {
                // 시간 정하는거 설정
            }
        }
        drawer_layout.closeDrawer(GravityCompat.END)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
            drawer_layout.closeDrawer(GravityCompat.END)
        } else {
            super.onBackPressed()
        }
    }


    private fun changeFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.ll_main, fragment).commitAllowingStateLoss()
    }

    private fun initPresenter() {
        mainPresenter = MainPresenter()
    }

    private fun initView() {
        taxiShareListFragment = TaxiShareListFragment.newInstance()
        myPageFragment = MyPageFragment.newInstance()

        changeFragment(taxiShareListFragment)
    }
}
