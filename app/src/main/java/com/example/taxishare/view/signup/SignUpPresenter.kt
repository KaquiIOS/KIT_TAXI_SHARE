/*
 * Created by WonJongSeong on 2019-03-26
 */

package com.example.taxishare.view.signup

import com.example.taxishare.data.model.ServerResponse
import com.example.taxishare.data.remote.apis.server.ServerClient
import com.example.taxishare.data.remote.apis.server.request.DuplicateIdExistCheckRequest
import com.example.taxishare.data.remote.apis.server.request.DuplicateNicknameCheckRequest
import com.example.taxishare.data.remote.apis.server.request.SignUpRequest
import com.example.taxishare.data.repo.ServerRepository
import com.example.taxishare.util.RegularExpressionChecker
import io.reactivex.disposables.Disposable

class SignUpPresenter(
    private val signUpView: SignUpView,
    private val serverClient: ServerRepository
) {

    private var isIdValidated: Boolean = false
    private var isPwValidated: Boolean = false
    private var isPwConfirmed: Boolean = false
    private var isNicknameValidated: Boolean = false
    private var isMajorSelected: Boolean = false

    private var preIdExistCheckDisposable: Disposable? = null
    private var preSignUpRequestDisposable: Disposable? = null
    private var preNicknameCheckDisposable : Disposable? = null

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
                if (it.code == ServerResponse.SAME_ID_EXIST.code) {
                    isIdValidated = false
                    signUpView.sameIdExist()
                } else if (it.code == ServerResponse.SAME_ID_NOT_EXIST.code) {
                    isIdValidated = true
                    signUpView.sameIdNotExist()
                }
                changeSignUpButtonState()
            }, {
                it.stackTrace[0]
            })
    }

    private fun checkSameNicknameExist(nickname: String) {

        if (preNicknameCheckDisposable != null && preNicknameCheckDisposable!!.isDisposed) {
            preNicknameCheckDisposable?.dispose()
        }

        preNicknameCheckDisposable = serverClient.isSameNicknameExist(DuplicateNicknameCheckRequest(nickname))
            .subscribe({
                if(it.code == ServerResponse.SAME_NICKNAME_EXIST.code) {
                    isNicknameValidated = false
                    signUpView.sameNicknameExist()
                } else {
                    isNicknameValidated = true
                    signUpView.sameNicknameNotExist()
                }
                changeSignUpButtonState()
            }, {
                it.stackTrace[0]
            })
    }


    fun checkIsIdValidate(stdId: String) {
        isIdValidated = RegularExpressionChecker.checkStudentIdValidation(stdId)
        signUpView.changeIdEditTextState(isIdValidated)
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
        if (isNicknameValidated) {
            checkSameNicknameExist(nickname)
        }
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

                when (it.code) {
                    ServerResponse.SIGN_UP_REQUEST_SUCCESS.code -> signUpView.signUpSuccess()
                    ServerResponse.SIGN_UP_REQUEST_FAIL.code -> signUpView.signUpFail()
                    else -> signUpView.signUpFail()
                }
            }, {
                it.stackTrace[0]
            })
    }
}