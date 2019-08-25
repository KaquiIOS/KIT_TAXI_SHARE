package com.example.taxishare.view.login

import com.example.taxishare.app.Constant
import com.example.taxishare.data.local.sharedpreference.SharedPreferenceManager
import com.example.taxishare.data.model.ServerResponse
import com.example.taxishare.data.model.User
import com.example.taxishare.data.remote.apis.server.request.LoginRequest
import com.example.taxishare.data.repo.ServerRepository
import com.example.taxishare.util.RegularExpressionChecker
import io.reactivex.disposables.Disposable

class LoginPresenter(
    private val loginView: LoginView,
    private val serverRepoImpl: ServerRepository,
    private val spManager: SharedPreferenceManager
) {

    private var isIdValidate: Boolean = false
    private var isPwValidate: Boolean = false

    private lateinit var preLoginRequestDisposable: Disposable

    private fun saveId(id: String) {
        spManager.setPreferenceId(id)
    }

    private fun savePw(pw: String) {
        spManager.setPreferencePassword(pw)
    }

    fun changeCheckBoxState(isCheck: Boolean) {
        spManager.setPreferenceIsAutoLogin(isCheck)
    }

    fun loginWithIdPw(id: String, pw: String) {
        login(id, pw)
    }

    /* 로그인 요청 */
    private fun login(id: String, pw: String) {

        // 이전에 로그인 요청이 있었으면 무시하고 다시 요청
        if (!::preLoginRequestDisposable.isInitialized || preLoginRequestDisposable.isDisposed) {
            loginView.showLoginLoadingDialog()
            preLoginRequestDisposable = serverRepoImpl.loginRequest(LoginRequest(id, pw))
                .subscribe({
                    when (it.responseCode) {
                        ServerResponse.LOGIN_SUCCESS.code -> {
                            Constant.CURRENT_USER = User(it.id, it.nickname, it.major)
                            saveId(id)
                            savePw(pw)
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
        isPwValidate = pw.isNotEmpty()
        changeLoginButtonState()
    }

    fun onCreate() {

        if (spManager.getPreferenceIsAutoLogin()) {

            val savedId = spManager.getPreferenceId("") ?: ""
            val savedPw = spManager.getPreferencePassword("") ?: ""

            loginView.writeSavedId(savedId)
            loginView.writeSavedPw(savedPw)
            loginView.checkAutoLogin()

            if(savedId != "" && savedPw != "" ){
                isIdValidate =  true
                isPwValidate = true
                changeLoginButtonState()
                loginWithIdPw(
                    spManager.getPreferenceId("")!!,
                    spManager.getPreferencePassword("")!!
                )
            }
        }
    }

    fun onDestroy() {

        if (::preLoginRequestDisposable.isInitialized && !preLoginRequestDisposable.isDisposed)
            preLoginRequestDisposable.dispose()

        loginView.dismissLoginLoadingDialog()
    }
}