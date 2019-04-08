package com.example.taxishare.view.login

interface LoginView {

    fun changeLoginButtonState(canActivate : Boolean)
    fun changeIdEditTextState(isMatched : Boolean)
    fun changePwEditTextState(isMatched: Boolean)
}