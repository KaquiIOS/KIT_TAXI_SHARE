/*
 * Created by WonJongSeong on 2019-03-26
 */

package com.example.taxishare.view.signup

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignUpPresenter(private val signUpView: SignUpView) {

    private val view: SignUpView = signUpView


    fun requestSignUp(): Completable = Completable.fromAction { /* Request */ }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


    fun findSameEmailExist(email : String) = Completable.fromAction { /* Request */ }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}