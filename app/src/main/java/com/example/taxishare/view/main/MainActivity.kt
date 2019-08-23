package com.example.taxishare.view.main

import android.app.DatePickerDialog


import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.taxishare.R
import com.example.taxishare.app.Constant
import com.example.taxishare.data.mapper.TypeMapper
import com.example.taxishare.data.model.Location
import com.example.taxishare.data.model.TaxiShareInfo
import com.example.taxishare.view.main.mypage.MyPageFragment
import com.example.taxishare.view.main.register.RegisterTaxiShareActivity
import com.example.taxishare.view.main.register.location.LocationSearchActivity
import com.example.taxishare.view.main.taxisharelist.TaxiShareListFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_taxi_share_list.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivityForResult
import java.util.*


class MainActivity : AppCompatActivity(), MainView,
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mainPresenter: MainPresenter
    private lateinit var taxiShareListFragment: TaxiShareListFragment
    private lateinit var myPageFragment: MyPageFragment

    private var menuSelection: MenuSelection = MenuSelection.NOT_SELECTED

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
                    R.id.nav_taxi_share_list -> {
                        btn_taxi_list_filter.visibility = View.VISIBLE
                        changeFragment(taxiShareListFragment)
                    }
                    else -> {
                        btn_taxi_list_filter.visibility = View.GONE
                        changeFragment(myPageFragment)
                    }
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
            taxiShareListFragment.reloadTaxiShareList()
            nav_view.menu[0].subMenu[0].title = location.locationName
        } else if (taxiShareListFragment.isVisible && requestCode == Constant.END_LOCATION_SEARCH_CODE && data != null) {
            val location = data.getSerializableExtra(Constant.LOCATION_SAVE_STR) as Location
            taxiShareListFragment.setEndLocation(location)
            taxiShareListFragment.reloadTaxiShareList()
            nav_view.menu[1].subMenu[0].title = location.locationName
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_start_location -> {
                menuSelection = MenuSelection.SET_START_LOCATION
            }
            R.id.nav_end_location -> {
                menuSelection = MenuSelection.SET_END_LOCATION
            }
            R.id.nav_start_time -> {
                menuSelection = MenuSelection.SET_START_TIME
            }
            R.id.nav_reset_filtering_options -> {
                menuSelection = MenuSelection.RESET_FILTERING_SETTING
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

    override fun openStartLocationSettingActivity() {
        startActivityForResult<LocationSearchActivity>(
            Constant.START_LOCATION_SEARCH_CODE,
            Constant.LOCATION_SEARCH_HINT to resources.getString(R.string.search_location_departure)
        )
    }

    override fun openEndLocationSettingActivity() {
        startActivityForResult<LocationSearchActivity>(
            Constant.END_LOCATION_SEARCH_CODE,
            Constant.LOCATION_SEARCH_HINT to resources.getString(R.string.search_location_arrive)
        )
    }

    override fun openStartTimeSettingActivity() {
        val calendar: Calendar = Calendar.getInstance()

        DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    Calendar.getInstance().apply {
                        set(year, month, dayOfMonth, hourOfDay, minute)
                        taxiShareListFragment.setStartTime(time)
                        taxiShareListFragment.reloadTaxiShareList()
                        nav_view.menu[2].subMenu[0].title = TypeMapper.dateToString(time)
                    }

                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    override fun resetFilteringSetting() {
        nav_view.menu[0].subMenu[0].title = getString(R.string.start_location_title)
        nav_view.menu[1].subMenu[0].title = getString(R.string.end_location_title)
        nav_view.menu[2].subMenu[0].title = getString(R.string.start_time_title)
        if (taxiShareListFragment.isVisible) {
            taxiShareListFragment.resetFilteringSetting()
        }
    }

    override fun changeToolbarNameAsTaxiList() {
        tv_tb_title.text = getString(R.string.taxi_list_toolbar_title)
    }

    override fun changeToolbarNameAsMyPage() {
        tv_tb_title.text = getString(R.string.taxi_list_my_page_title)
    }

    private fun changeFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        mainPresenter.changeToolbarName(fragment == taxiShareListFragment)

        fragmentTransaction.replace(R.id.ll_main, fragment).commitAllowingStateLoss()
    }

    private fun initPresenter() {
        mainPresenter = MainPresenter(this)
    }

    private fun initView() {
        taxiShareListFragment = TaxiShareListFragment.newInstance()
        myPageFragment = MyPageFragment.newInstance()

        changeFragment(taxiShareListFragment)

        drawer_layout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {}
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {
                menuSelection = MenuSelection.NOT_SELECTED
            }

            override fun onDrawerClosed(drawerView: View) {
                mainPresenter.setFilteringSetting(menuSelection)
            }
        })
    }
}
