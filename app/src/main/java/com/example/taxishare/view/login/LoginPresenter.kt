package com.example.taxishare.view.login

import com.example.taxishare.app.Constant
import com.example.taxishare.data.model.ServerResponse
import com.example.taxishare.data.model.User
import com.example.taxishare.data.remote.apis.server.request.LoginRequest
import com.example.taxishare.data.repo.ServerRepository
import com.example.taxishare.util.RegularExpressionChecker
import io.reactivex.disposables.Disposable

class LoginPresenter(
    private val loginView: LoginView,
    private val serverRepoImpl: ServerRepository
) {

    private var isIdValidate: Boolean = false
    private var isPwValidate: Boolean = false

    private lateinit var preLoginRequestDisposable: Disposable

    /* 로그인 요청 */
    fun login(id: String, pw: String) {

        // 이전에 로그인 요청이 있었으면 무시하고 다시 요청
        if (!::preLoginRequestDisposable.isInitialized || preLoginRequestDisposable.isDisposed) {
            loginView.showLoginLoadingDialog()
            preLoginRequestDisposable = serverRepoImpl.loginRequest(LoginRequest(id, pw))
                .subscribe({
                    when (it.responseCode) {
                        ServerResponse.LOGIN_SUCCESS.code -> {
                            Constant.CURRENT_USER = User(it.id, it.nickname, it.major)
                            loginView.loginSuccess()
                        }
                        ServerResponse.NOT_VALIDATED_USER.code -> loginView.notValidatedUserMessage()
                        ServerResponse.LOGIN_FAIL.code -> loginView.loginFail()
                        else -> loginView.loginFail()
                    }
                    loginView.dismissLoginLoadingDialog()
                }, {
                    it.stackTrace[0]
                    loginView.dismissLoginLoadingDialog()
                })
        }
    }

    /* 로그인이 가능한 상태인지 확인 */
    private fun changeLoginButtonState() {
        loginView.changeLoginButtonState(isIdValidate && isPwValidate)
    }

    fun checkIdValidation(id: String) {
        isIdValidate = RegularExpressionChecker.checkStudentIdValidation(id)
        loginView.changeIdEditTextState(isIdValidate)
        changeLoginButtonState()
    }

    fun checkPwValidation(pw: String) {
        isPwValidate = RegularExpressionChecker.checkPasswordValidation(pw)
        loginView.changePwEditTextState(isPwValidate)
        changeLoginButtonState()
    }

    fun onDestroy() {

        if (::preLoginRequestDisposable.isInitialized && !preLoginRequestDisposable.isDisposed)
            preLoginRequestDisposable.dispose()

        loginView.dismissLoginLoadingDialog()
    }
}