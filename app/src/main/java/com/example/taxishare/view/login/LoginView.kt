package com.example.taxishare.view.login

interface LoginView {

    fun changeLoginButtonState(canActivate : Boolean)
    fun changeIdInputState(isMatched : Boolean)
    fun changePwInputState(isMatched: Boolean)
}