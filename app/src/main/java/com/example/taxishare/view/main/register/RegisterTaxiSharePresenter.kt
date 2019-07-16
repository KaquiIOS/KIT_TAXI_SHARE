/*
 * Created by WonJongSeong on 2019-05-13
 */

package com.example.taxishare.view.main.register

import android.util.Log
import com.example.taxishare.app.Constant
import com.example.taxishare.data.local.room.entity.LocationModel
import com.example.taxishare.data.mapper.TypeMapper
import com.example.taxishare.data.model.Location
import com.example.taxishare.data.model.ServerResponse
import com.example.taxishare.data.remote.apis.server.request.RegisterTaxiShareRequest
import com.example.taxishare.data.repo.LocationRepository
import com.example.taxishare.data.repo.ServerRepository
import io.reactivex.disposables.Disposable
import java.util.*


class RegisterTaxiSharePresenter(
    private val view: RegisterTaxiShareView,
    private val serverRepoImpl: ServerRepository,
    private val localRepoImpl: LocationRepository
) {

    private lateinit var taxiRegisterDispose: Disposable

    private lateinit var startDateTime: Date
    private lateinit var startLocation: Location
    private lateinit var endLocation: Location
    private var isTitleChecked: Boolean = false
    private var memberNum: Int = 2
    private var content: String = ""
    private var title = ""


    private fun isAllRequestDataValidated() =
        ::startDateTime.isInitialized && ::startLocation.isInitialized && ::endLocation.isInitialized && isTitleChecked


    fun checkTitleLength(title: String) {
        isTitleChecked = title.length > Constant.REGISTER_TAXI_TITLE_MIN_LENGTH
        this@RegisterTaxiSharePresenter.title = title
        view.changeTitleEditTextState(isTitleChecked)
    }

    fun setStartDateTime(startDateTime: Date) {
        this.startDateTime = startDateTime
        view.changeStartDateTime(TypeMapper.dateToString(startDateTime))
        view.changeSignUpButtonState(isAllRequestDataValidated())
    }

    fun setStartLocation(startLocation: Location) {
        this.startLocation = startLocation
        view.changeStartLocation(startLocation.locationName)
        view.changeSignUpButtonState(isAllRequestDataValidated())
        saveSelectedLocationToLocalDB(startLocation)
    }

    fun setEndLocation(endLocation: Location) {
        this.endLocation = endLocation
        view.changeEndLocation(endLocation.locationName)
        view.changeSignUpButtonState(isAllRequestDataValidated())
        saveSelectedLocationToLocalDB(endLocation)
    }

    fun setContent(content: String) {
        this.content = content
    }

    fun setMemberNum(memberNum: String) {
        this.memberNum = memberNum.toInt()
    }

    fun registerTaxiShare() {

        if (!::taxiRegisterDispose.isInitialized || taxiRegisterDispose.isDisposed) {
            taxiRegisterDispose = serverRepoImpl.registerTaxiShare(
                RegisterTaxiShareRequest(
                    title, content, startDateTime, memberNum, startLocation, endLocation, Date()
                )
            ).subscribe({
                when(it.code) {
                    1901 -> view.taxiRegisterTaskSuccess()
                    else -> view.taxiRegisterTaskFail()
                }
            }, {
                it.printStackTrace()
            })
        } else {
            view.taxiRegisterTaskNotOver()
        }
    }

    fun onDestroy() {
        disposeAllTask()
    }

    private fun saveSelectedLocationToLocalDB(location: Location) {
        with(location) {
            localRepoImpl.insertLocation(
                LocationModel(latitude, longitude, locationName, roadAddress, jibunAddress, Date())
            ).subscribe({
                Log.d("Test", "SaveSelectedLocationToDB")
            }, {
                it.printStackTrace()
            })
        }
    }

    private fun disposeAllTask() {
        if (::taxiRegisterDispose.isInitialized && !taxiRegisterDispose.isDisposed)
            taxiRegisterDispose.dispose()
    }
}