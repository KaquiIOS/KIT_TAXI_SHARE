package com.example.taxishare.view.login

import com.example.taxishare.util.RegularExpressionChecker

class LoginPresenter(private val loginView: LoginView) {

    private val view: LoginView = loginView
    private var isIdValidate : Boolean = false
    private var isPwValidate : Boolean = false

    /* 로그인 요청 */
    fun login(id: String, pw: String) {

    }

    /* 로그인이 가능한 상태인지 확인 */
    private fun changeLoginButtonState() {
        view.changeLoginButtonState(isIdValidate && isPwValidate)
    }

    fun checkIdValidation(id: String) {
        isIdValidate = RegularExpressionChecker.checkEmailValidation(id)
        view.changeIdEditTextState(isIdValidate)
        changeLoginButtonState()
    }

    fun checkPwValidation(pw: String) {
        isPwValidate = RegularExpressionChecker.checkPasswordValidation(pw)
        view.changePwEditTextState(isPwValidate)
        changeLoginButtonState()
    }



}