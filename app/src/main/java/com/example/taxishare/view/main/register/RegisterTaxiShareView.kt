/*
 * Created by WonJongSeong on 2019-05-13
 */

package com.example.taxishare.view.main.register

interface RegisterTaxiShareView {

    fun changeSignUpButtonState(canActivate : Boolean)

    fun changeTitleEditTextState(isMatched : Boolean)
    fun changeStartDateTime(dateTime : String)

    fun changeStartLocation(location : String)
    fun changeEndLocation(location: String)

    fun openDateTimePicker()

}