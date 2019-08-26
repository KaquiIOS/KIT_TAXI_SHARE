package com.meongbyeol.taxishare.view.main.register

import android.app.Activity
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.meongbyeol.taxishare.R
import com.meongbyeol.taxishare.app.AlarmManagerImpl
import com.meongbyeol.taxishare.app.Constant
import com.meongbyeol.taxishare.data.model.Location
import com.meongbyeol.taxishare.data.model.TaxiShareInfo
import com.meongbyeol.taxishare.data.remote.apis.server.ServerClient
import com.meongbyeol.taxishare.data.repo.ServerRepositoryImpl
import com.meongbyeol.taxishare.view.main.register.location.LocationSearchActivity
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.itemSelections
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.activity_register_taxi_share.*
import org.jetbrains.anko.sdk27.coroutines.onTouch
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import java.util.*
import java.util.concurrent.TimeUnit

class RegisterTaxiShareActivity : AppCompatActivity(), RegisterTaxiShareView {

    private val presenter: RegisterTaxiSharePresenter by lazy {
        RegisterTaxiSharePresenter(
            this, ServerRepositoryImpl(ServerClient.getInstance()),
            AlarmManagerImpl(getSystemService(Context.ALARM_SERVICE) as AlarmManager, this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_taxi_share)

        initView()
        initListener()

        presenter.setPreviousInfo(intent.getSerializableExtra(Constant.MODIFY_TAXI_SHARE_STR) as TaxiShareInfo?)
    }

    override fun setTitle(title: String) {
        text_input_taxi_register_title.setText(title)
    }

    override fun changeTitleEditTextState(isMatched: Boolean) {
        text_input_layout_taxi_register_title.error =
            if (isMatched) null
            else resources.getString(R.string.taxi_share_content_requirement_not_match)
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

    override fun taxiRegisterTaskNotOver() {
        toast(resources.getString(R.string.register_taxi_share_info_waiting))
    }

    override fun taxiRegisterTaskSuccess(taxiShareInfo: TaxiShareInfo) {
        toast(resources.getString(R.string.register_taxi_share_info_success))
        setResult(
            Activity.RESULT_OK,
            Intent().apply { putExtra(Constant.REGISTER_TAXI_SHARE_STR, taxiShareInfo) })
        finish()
    }

    override fun taxiRegisterTaskFail() {
        toast(resources.getString(R.string.register_taxi_share_info_fail))
    }

    override fun taxiModifyTaskNotOver() {
        toast(getString(R.string.modify_taxi_share_waiting))
    }

    override fun taxiModifyTaskSuccess(taxiShareInfo: TaxiShareInfo) {
        toast(getString(R.string.modify_taxi_share_success))
        setResult(
            Activity.RESULT_OK,
            Intent().apply { putExtra(Constant.MODIFY_TAXI_SHARE_STR, taxiShareInfo) })
        finish()
    }

    override fun taxiModifyTaskFail() {
        toast(getString(R.string.modify_taxi_share_fail))
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun openDateTimePicker() {

        val calendar: Calendar = Calendar.getInstance()

        DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    Calendar.getInstance().apply {
                        set(year, month, dayOfMonth, hourOfDay, minute)
                        presenter.setStartDateTime(time)
                    }

                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REGISTER_START_LOCATION_CODE && data != null) {
            presenter.setStartLocation(data.getSerializableExtra(resources.getString(R.string.intent_location)) as Location)
        } else if (requestCode == Constant.REGISTER_END_LOCATION_CODE && data != null) {
            presenter.setEndLocation(data.getSerializableExtra(resources.getString(R.string.intent_location)) as Location)
        }
    }

    @SuppressWarnings("all")
    private fun initListener() {
        text_input_taxi_register_title.textChanges().skipInitialValue().subscribe {
            presenter.checkTitleLength(it.toString())
        }

        text_view_taxi_register_start_time.clicks().subscribe {
            openDateTimePicker()
        }

        text_view_register_start_location.clicks().subscribe {
            startActivityForResult<LocationSearchActivity>(
                Constant.REGISTER_START_LOCATION_CODE,
                Constant.LOCATION_SEARCH_HINT to resources.getString(R.string.search_location_departure)
            )
                .apply {
                    intent = Intent().apply {
                        putExtra(
                            resources.getString(R.string.intent_request_code),
                            Constant.REGISTER_END_LOCATION_CODE
                        )
                    }
                }
        }

        text_view_taxi_register_end_location.clicks().subscribe {
            startActivityForResult<LocationSearchActivity>(
                Constant.REGISTER_END_LOCATION_CODE,
                Constant.LOCATION_SEARCH_HINT to resources.getString(R.string.search_location_arrive)
            )
                .apply {
                    intent = Intent().apply {
                        putExtra(
                            resources.getString(R.string.intent_request_code),
                            Constant.REGISTER_END_LOCATION_CODE
                        )
                    }
                }
        }

        spn_taxi_register_member_num.onTouch { view, _ -> view.requestFocusFromTouch() }

        spn_taxi_register_member_num.itemSelections().skipInitialValue().subscribe {
            presenter.setMemberNum(spn_taxi_register_member_num.selectedItem.toString())
        }

        btn_taxi_register_post.clicks()
            .debounce(250, TimeUnit.MILLISECONDS)
            .subscribe {
                presenter.registerTaxiShare()
            }

        tb_register_taxi.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initView() {

    }
}
