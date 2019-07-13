package com.example.taxishare.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.taxishare.R
import com.example.taxishare.view.main.mypage.MyPageFragment
import com.example.taxishare.view.main.register.RegisterTaxiShareActivity
import com.example.taxishare.view.main.taxisharelist.TaxiShareListFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity(), MainView {

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

        bnv_main.setOnNavigationItemSelectedListener {
            if (it.itemId == R.id.nav_register_taxi_share) {
                startActivity<RegisterTaxiShareActivity>()
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
