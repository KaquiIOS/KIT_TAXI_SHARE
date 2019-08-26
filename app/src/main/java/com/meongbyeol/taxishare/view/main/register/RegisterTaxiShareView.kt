/*
 * Created by WonJongSeong on 2019-05-13
 */

package com.meongbyeol.taxishare.view.main.register

import com.meongbyeol.taxishare.data.model.TaxiShareInfo

interface RegisterTaxiShareView {

    fun changeSignUpButtonState(canActivate : Boolean)

    fun changeTitleEditTextState(isMatched : Boolean)
    fun changeStartDateTime(dateTime : String)

    fun changeStartLocation(location : String)
    fun changeEndLocation(location: String)
    fun setTitle(title : String)

    fun openDateTimePicker()
    fun taxiRegisterTaskNotOver()
    fun taxiRegisterTaskSuccess(taxiShareInfo : TaxiShareInfo)
    fun taxiRegisterTaskFail()

    fun taxiModifyTaskNotOver()
    fun taxiModifyTaskSuccess(taxiShareInfo : TaxiShareInfo)
    fun taxiModifyTaskFail()
}