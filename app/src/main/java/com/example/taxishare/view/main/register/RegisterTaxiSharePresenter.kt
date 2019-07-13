/*
 * Created by WonJongSeong on 2019-05-13
 */

package com.example.taxishare.view.main.register

import com.example.taxishare.app.Constant
import com.example.taxishare.data.model.Location
import com.example.taxishare.data.repo.LocationRepository
import com.example.taxishare.data.repo.LocationRepositoryImpl
import com.example.taxishare.data.repo.ServerRepository
import com.example.taxishare.data.repo.ServerRepositoryImpl
import java.util.*


class RegisterTaxiSharePresenter(
    val view: RegisterTaxiShareView,
    val serverRepoImpl: ServerRepository,
    val localRepoImpl: LocationRepository
) {

    private lateinit var startDateTime: Date
    private lateinit var startLocation: Location
    private lateinit var endLocation: Location
    private var isTitleChecked: Boolean = false
    private var memberNum: Int = 2
    private var content: String = ""


    private fun isAllRequestDataValidated() =
        ::startDateTime.isInitialized && ::startLocation.isInitialized && ::endLocation.isInitialized && isTitleChecked


    fun checkTitleLength(strLength: Int) {
        isTitleChecked = strLength > Constant.REGISTER_TAXI_TITLE_MIN_LENGTH
        view.changeTitleEditTextState(isTitleChecked)
    }

    fun setStartDateTime(startDateTime: Date) {
        this.startDateTime = startDateTime
        view.changeStartDateTime(Constant.DATE_FORMATTER.format(startDateTime))
        view.changeSignUpButtonState(isAllRequestDataValidated())
    }

    fun setStartLocation(startLocation: Location) {
        this.startLocation = startLocation
        view.changeStartLocation(startLocation.locationName)
        view.changeSignUpButtonState(isAllRequestDataValidated())
    }

    fun setEndLocation(endLocation: Location) {
        this.endLocation = endLocation
        view.changeEndLocation(endLocation.locationName)
        view.changeSignUpButtonState(isAllRequestDataValidated())
    }

    fun setContent(content: String) {
        this.content = content
    }

    fun setMemberNum(memberNum: String) {
        this.memberNum = memberNum.toInt()
    }

    fun registerTaxiShare() {
        // TODO : 글 등록하기
    }
}