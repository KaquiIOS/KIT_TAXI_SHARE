package com.meongbyeol.taxishare.view.login

interface LoginView {

    fun changeLoginButtonState(canActivate : Boolean)
    fun changeIdEditTextState(isMatched : Boolean)

    fun showLoginLoadingDialog()
    fun dismissLoginLoadingDialog()

    fun loginSuccess()
    fun loginFail()
    fun notValidatedUserMessage()

    fun writeSavedId(id : String)
    fun writeSavedPw(pw : String)
    fun checkAutoLogin()

    fun showPatchMessage()
}