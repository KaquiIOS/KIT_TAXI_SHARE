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
import com.example.taxishare.data.model.TaxiShareInfo
import com.example.taxishare.data.remote.apis.server.request.RegisterTaxiShareRequest
import com.example.taxishare.data.remote.apis.server.request.TaxiShareModifyRequest
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
    private var memberNum: Int = 3
    private var title = ""

    private var isModify: Boolean = false
    private var preTaxiShareInfo: TaxiShareInfo? = null

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

    fun setMemberNum(memberNum: String) {
        this.memberNum = memberNum.toInt()
    }

    fun setPreviousInfo(taxiShareInfo: TaxiShareInfo?) {

        if (taxiShareInfo != null) {

            preTaxiShareInfo = taxiShareInfo

            isModify = true
            with(taxiShareInfo) {
                setStartDateTime(this.startDate)
                setStartLocation(this.startLocation)
                setEndLocation(this.endLocation)
                setMemberNum(this.limit.toString())
                this@RegisterTaxiSharePresenter.title = this.title
                view.setTitle(this.title)
                isAllRequestDataValidated()
            }
        }
    }

    fun registerTaxiShare() {

        if (isModify) {
            modifyTaxiShareInfo()
        } else {
            registerNewTaxiShareInfo()
        }
    }

    fun onDestroy() {
        disposeAllTask()
    }

    private fun modifyTaxiShareInfo() {
        if (!::taxiRegisterDispose.isInitialized || taxiRegisterDispose.isDisposed) {
            taxiRegisterDispose = serverRepoImpl.updateTaxiShare(
                TaxiShareModifyRequest(
                    preTaxiShareInfo!!.id,
                    title,
                    startDateTime,
                    startLocation,
                    endLocation,
                    memberNum
                )
            ).subscribe({
                if(it == ServerResponse.TAXISHARE_MODIFY_SUCCESS) {
                    view.taxiModifyTaskSuccess(
                        TaxiShareInfo(
                            preTaxiShareInfo!!.id,
                            Constant.CURRENT_USER.studentId.toString(),
                            title,
                            startDateTime,
                            startLocation,
                            endLocation,
                            memberNum,
                            Constant.CURRENT_USER.nickname,
                            Constant.CURRENT_USER.major,
                            preTaxiShareInfo!!.participantsNum,
                            true
                    ))
                } else {
                    view.taxiModifyTaskFail()
                }
            }, {
                it.printStackTrace()
                view.taxiModifyTaskFail()
            })
        } else {
            view.taxiModifyTaskNotOver()
        }
    }

    private fun registerNewTaxiShareInfo() {
        if (!::taxiRegisterDispose.isInitialized || taxiRegisterDispose.isDisposed) {
            taxiRegisterDispose = serverRepoImpl.registerTaxiShare(
                RegisterTaxiShareRequest(
                    title, startDateTime, memberNum, startLocation, endLocation, Date()
                )
            ).subscribe({
                when (it.responseCode) {
                    ServerResponse.TAXI_SHARE_REGISTER_REQUEST_SUCCESS.code -> view.taxiRegisterTaskSuccess(
                        TaxiShareInfo(
                            it.id,
                            Constant.CURRENT_USER.studentId.toString(),
                            title,
                            startDateTime,
                            startLocation,
                            endLocation,
                            memberNum,
                            Constant.CURRENT_USER.nickname,
                            Constant.CURRENT_USER.major,
                            0,
                            true
                        )
                    )
                    else -> view.taxiRegisterTaskFail()
                }
            }, {
                it.printStackTrace()
            })
        } else {
            view.taxiRegisterTaskNotOver()
        }
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