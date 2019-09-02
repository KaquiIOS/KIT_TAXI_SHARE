/*
 * Created by WonJongSeong on 2019-03-26
 */

package com.meongbyeol.taxishare.view.signup

import android.util.Log
import com.meongbyeol.taxishare.data.model.ServerResponse
import com.meongbyeol.taxishare.data.remote.apis.server.request.DuplicateIdExistCheckRequest
import com.meongbyeol.taxishare.data.remote.apis.server.request.DuplicateNicknameCheckRequest
import com.meongbyeol.taxishare.data.remote.apis.server.request.SignUpRequest
import com.meongbyeol.taxishare.data.repo.ServerRepository
import com.meongbyeol.taxishare.util.RegularExpressionChecker
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.disposables.Disposable

class SignUpPresenter(
    private val signUpView: SignUpView,
    private val serverClient: ServerRepository
) {

    private val TAG: String = SignUpPresenter::class.java.simpleName

    private var isIdValidated: Boolean = false
    private var isPwValidated: Boolean = false
    private var isPwConfirmed: Boolean = false
    private var isNicknameValidated: Boolean = false
    private var isMajorSelected: Boolean = false

    private lateinit var preIdExistCheckDisposable: Disposable
    private lateinit var preSignUpRequestDisposable: Disposable
    private lateinit var preNicknameCheckDisposable: Disposable

    private fun isAllRequestDataValidated(): Boolean =
        isIdValidated && isPwValidated && isPwConfirmed && isNicknameValidated && isMajorSelected

    private fun changeSignUpButtonState() =
        signUpView.changeSignUpButtonState(isAllRequestDataValidated())

    private fun checkSameIdExist(stdId: String) {

        if(::preIdExistCheckDisposable.isInitialized && !preIdExistCheckDisposable.isDisposed)
            preIdExistCheckDisposable.dispose()

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

        if(::preNicknameCheckDisposable.isInitialized && !preNicknameCheckDisposable.isDisposed)
            preNicknameCheckDisposable.dispose()

        preNicknameCheckDisposable = serverClient.isSameNicknameExist(DuplicateNicknameCheckRequest(nickname))
            .subscribe({
                if (it.code == ServerResponse.SAME_NICKNAME_EXIST.code) {
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
        isIdValidated = RegularExpressionChecker.checkStudentIdValidation(stdId) && stdId.indexOf("@",0) == -1
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

        if(!::preSignUpRequestDisposable.isInitialized || preSignUpRequestDisposable.isDisposed) {

            signUpView.showSignUpRequestLoadingDialog()

            FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
                if (!it.isSuccessful) {
                    Log.e(TAG, it.exception?.message)
                    signUpView.signUpFail()
                    signUpView.dismissSignUpLoadingDialog()
                    return@addOnCompleteListener
                }

                preSignUpRequestDisposable =
                    serverClient.signUpRequest(SignUpRequest(id, pw, nickname, major, it.result?.token ?: ""))
                        .subscribe({
                            when (it.code) {
                                ServerResponse.SIGN_UP_REQUEST_SUCCESS.code -> signUpView.signUpSuccess()
                                ServerResponse.SIGN_UP_REQUEST_FAIL.code -> signUpView.signUpFail()
                                else -> signUpView.signUpFail()
                            }
                            signUpView.dismissSignUpLoadingDialog()
                        }, {
                            it.stackTrace[0]
                            signUpView.dismissSignUpLoadingDialog()
                        })
            }
        }
    }
}