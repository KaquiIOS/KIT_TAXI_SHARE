package com.example.taxishare.view.main.register

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.taxishare.R
import com.example.taxishare.app.Constant
import com.example.taxishare.view.main.register.location.LocationSearchActivity
import com.jakewharton.rxbinding3.appcompat.navigationClicks
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.itemSelections
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.activity_register_taxi_share.*
import org.jetbrains.anko.startActivityForResult
import java.util.*

//typealias Presenter  = RegisterTaxiSharePresenter

class RegisterTaxiShareActivity : AppCompatActivity(), RegisterTaxiShareView {

    lateinit var presenter: RegisterTaxiSharePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_taxi_share)

        initView()
        initListener()
        initPresenter()
    }

    override fun changeTitleEditTextState(isMatched: Boolean) {
        text_input_layout_taxi_register_title.error =
            if (isMatched) null
            else resources.getString(R.string.common_nickname_pattern_not_match)
    }

    override fun changeSignUpButtonState(canActivate: Boolean) {
        btn_taxi_register_post.isEnabled = canActivate
    }

    override fun changeStartDateTime(dateTime: String) {
        text_view_taxi_register_start_time.text = dateTime
    }

    override fun changeEndLocation(location: String) {
        text_view_taxi_register_end_location.text = location
    }

    override fun changeStartLocation(location: String) {
        text_view_register_start_location.text = location
    }

    override fun openDateTimePicker() {

        val calendar: Calendar = Calendar.getInstance()

        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                Calendar.getInstance().apply {
                    set(year, month, dayOfMonth, hourOfDay, minute)
                    presenter.setStartDateTime(time)
                }

            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REGISTER_START_LOCATION_CODE && data != null) {
            //presenter.setStartLocation()
        } else if (requestCode == Constant.REGISTER_END_LOCATION_CODE && data != null) {
            //presenter.setEndLocation()
        }
    }

    private fun initPresenter() {
        presenter = RegisterTaxiSharePresenter(this)
    }

    @SuppressWarnings("all")
    private fun initListener() {
        text_input_taxi_register_title.textChanges().skipInitialValue().subscribe {
            presenter.checkTitleLength(it.length)
        }

        text_view_taxi_register_start_time.clicks().subscribe {
            openDateTimePicker()
        }

        text_view_register_start_location.clicks().subscribe {
            startActivityForResult<LocationSearchActivity>(Constant.REGISTER_START_LOCATION_CODE,
                Constant.LOCATION_SEARCH_HINT to resources.getString(R.string.search_location_departure))
        }

        text_view_taxi_register_end_location.clicks().subscribe {
            startActivityForResult<LocationSearchActivity>(Constant.REGISTER_END_LOCATION_CODE,
                Constant.LOCATION_SEARCH_HINT to resources.getString(R.string.search_location_arrive))
        }

        text_view_taxi_register_content.doAfterTextChanged {
            presenter.setContent(it.toString())
        }

        spn_taxi_register_member_num.itemSelections().skipInitialValue().subscribe {
            presenter.setMemberNum(spn_taxi_register_member_num.selectedItem.toString())
        }

        btn_taxi_register_post.clicks().subscribe {
            presenter.registerTaxiShare()
        }

        tb_register_taxi.navigationClicks().subscribe {
            finish()
        }
    }

    private fun initView() {

    }
}
