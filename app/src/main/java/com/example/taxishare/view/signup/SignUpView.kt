/*
 * Created by WonJongSeong on 2019-03-26
 */

package com.example.taxishare.view.signup

import com.google.android.material.textfield.TextInputEditText

interface SignUpView {

    fun changeSignUpButtonState(canActivate : Boolean)

    fun changeIdEditTextState(isMatched : Boolean)
    fun changePwEditTextState(isMatched: Boolean)
    fun changePwConfirmEditTextState(isMatched: Boolean)
    fun changeNicknameEditTextState(isMatched: Boolean)
    // 전공 선택 변경

    fun changeEditTextColor(editText: TextInputEditText, isMatched: Boolean)
}