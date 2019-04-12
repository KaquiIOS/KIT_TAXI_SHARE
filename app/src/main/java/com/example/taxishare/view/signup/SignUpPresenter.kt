/*
 * Created by WonJongSeong on 2019-03-26
 */

package com.example.taxishare.view.signup

import com.example.taxishare.data.remote.apis.server.ServerClient
import com.example.taxishare.data.remote.apis.server.request.DuplicateIdExistCheckRequest
import com.example.taxishare.data.remote.apis.server.request.SignUpRequest
import com.example.taxishare.util.RegularExpressionChecker
import com.gun0912.tedpermission.TedPermission
import io.reactivex.Single
import io.reactivex.disposables.Disposable

class SignUpPresenter(
    private val signUpView: SignUpView,
    private val serverClient: ServerClient
) {

    private var isIdValidated: Boolean = false
    private var isPwValidated: Boolean = false
    private var isPwConfirmed: Boolean = false
    private var isNicknameValidated: Boolean = false
    private var isMajorSelected: Boolean = false

    private var preIdExistCheckDisposable: Disposable? = null
    private var preSignUpRequestDisposable: Disposable? = null

    private fun isAllRequestDataValidated(): Boolean =
        isIdValidated && isPwValidated && isPwConfirmed && isNicknameValidated && isMajorSelected

    private fun changeSignUpButtonState() =
        signUpView.changeSignUpButtonState(isAllRequestDataValidated())

    private fun checkSameIdExist(stdId: String) {

        if (preIdExistCheckDisposable != null && preIdExistCheckDisposable!!.isDisposed) {
            preIdExistCheckDisposable?.dispose()
        }

        preIdExistCheckDisposable = serverClient.isSameIdExist(DuplicateIdExistCheckRequest(stdId))
            .subscribe({
                isIdValidated = !it.sameIdExist
                if (it.sameIdExist)
                    signUpView.sameIdExist()
                else
                    signUpView.sameIdNotExist()
                changeSignUpButtonState()
            }, {
                it.stackTrace[0]
            })
    }

    private fun checkStudentIdPatternValidate(stdId: String) {
        isIdValidated = RegularExpressionChecker.checkStudentIdValidation(stdId)
        signUpView.changeIdEditTextState(isIdValidated)
    }

    fun checkIsIdValidate(stdId: String) {
        checkStudentIdPatternValidate(stdId)
        if (isIdValidated) {
            checkSameIdExist(stdId)
        }
    }

    fun checkPwValidation(pw: String) {
        isPwValidated = RegularExpressionChecker.checkPasswordValidation(pw)
        signUpView.changePwEditTextState(isPwValidated)
        changeSignUpButtonState()
    }

    fun checkPwConfirmed(pw: String, confirmPw: String) {
        isPwConfirmed = (pw == confirmPw)
        signUpView.changePwConfirmEditTextState(isPwConfirmed)
        changeSignUpButtonState()
    }

    fun checkNicknameValidation(nickname: String) {
        isNicknameValidated = RegularExpressionChecker.checkNicknameValidation(nickname)
        signUpView.changeNicknameEditTextState(isNicknameValidated)
        changeSignUpButtonState()
    }

    fun checkMajorSelected(selectedIdx: Int) {
        isMajorSelected = (selectedIdx > 0)
        changeSignUpButtonState()
    }

    fun signUpRequest(id: String, pw: String, nickname: String, major: String) {
        if (preSignUpRequestDisposable != null && preSignUpRequestDisposable!!.isDisposed) {
            preSignUpRequestDisposable?.dispose()
        }

        preSignUpRequestDisposable = serverClient.signUpRequest(SignUpRequest(id, pw, nickname, major))
            .subscribe({
                if (it.result) {
                    signUpView.signUpSuccess()
                } else {
                    signUpView.signUpFail()
                }
            }, {
                it.stackTrace[0]
            })
    }
}