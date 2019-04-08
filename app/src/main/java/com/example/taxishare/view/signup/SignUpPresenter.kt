/*
 * Created by WonJongSeong on 2019-03-26
 */

package com.example.taxishare.view.signup

import com.example.taxishare.util.RegularExpressionChecker

class SignUpPresenter(private val signUpView: SignUpView) {

    private val view: SignUpView = signUpView

    private var isIdValidated: Boolean = false
    private var isPwValidated: Boolean = false
    private var isPwConfirmed: Boolean = false
    private var isNicknameValidated: Boolean = false
    private var isMajorSelected: Boolean = false


    private fun isAllRequestDataValidated(): Boolean =
        isIdValidated && isPwValidated && isPwConfirmed && isNicknameValidated && isMajorSelected

    private fun changeSignUpButtonState() =
        view.changeSignUpButtonState(isAllRequestDataValidated())

    private fun changeInputTextState(validatedCheckFunction : (Boolean) -> Unit, isMatched : Boolean) {
        validatedCheckFunction(isMatched)
        changeSignUpButtonState()
    }

    fun checkStudentIdValidation(stdId: String) {
        isIdValidated = RegularExpressionChecker.checkStudentIdValidation(stdId)
        changeInputTextState ({ view.changeIdEditTextState(isIdValidated) }, isIdValidated)
    }

    fun checkPwValidation(pw: String) {
        isPwValidated = RegularExpressionChecker.checkPasswordValidation(pw)
        changeInputTextState ({ view.changePwEditTextState(isPwValidated) }, isPwValidated)
    }

    fun checkPwConfirmed(pw: String, confirmPw: String) {
        isPwConfirmed = (pw == confirmPw)
        changeInputTextState ({ view.changePwConfirmEditTextState(isPwConfirmed) }, isPwConfirmed)
    }

    fun checkNicknameValidation(nickname: String) {
        isNicknameValidated = RegularExpressionChecker.checkNicknameValidation(nickname)
        changeInputTextState ({ view.changeNicknameEditTextState(isNicknameValidated) }, isNicknameValidated)
    }

    fun checkMajorSelected(selectedIdx: Int) {
        isMajorSelected = (selectedIdx > 0)
        changeInputTextState({}, isMajorSelected)
    }
}