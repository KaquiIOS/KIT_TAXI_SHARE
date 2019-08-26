package com.meongbyeol.taxishare.view.password

interface FindPasswordView {

    fun sendTemporaryPasswordSuccess()
    fun sendTemporaryPasswordFail()
    fun changeLoginButtonState(isValidate : Boolean)
    fun showLoginLoadingDialog()
    fun dismissLoginLoadingDialog()
    fun changeIdEditTextState(isMatched : Boolean)
}