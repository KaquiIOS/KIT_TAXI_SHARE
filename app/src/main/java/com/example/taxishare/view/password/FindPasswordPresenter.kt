package com.example.taxishare.view.password

import com.example.taxishare.data.model.ServerResponse
import com.example.taxishare.data.remote.apis.server.request.FindPasswordRequest
import com.example.taxishare.data.repo.ServerRepository
import com.example.taxishare.util.RegularExpressionChecker
import io.reactivex.disposables.Disposable

class FindPasswordPresenter(
    private val serverRepo: ServerRepository,
    private val view: FindPasswordView
) {

    private lateinit var findPasswordDisposable: Disposable

    private var isIdValidate: Boolean = false

    fun findPassword(id: String) {
        if (!::findPasswordDisposable.isInitialized || findPasswordDisposable.isDisposed) {
            view.showLoginLoadingDialog()
            findPasswordDisposable = serverRepo.findPassword(FindPasswordRequest(id))
                .subscribe({
                    if (it == ServerResponse.FIND_PASSWORD_SUCCESS) {
                        view.sendTemporaryPasswordSuccess()
                    } else {
                        view.sendTemporaryPasswordFail()
                    }
                    view.dismissLoginLoadingDialog()
                }, {
                    it.printStackTrace()
                })
        }

    }

    fun checkIdValidation(id: String) {
        isIdValidate = RegularExpressionChecker.checkStudentIdValidation(id)
        view.changeLoginButtonState(isIdValidate)
        view.changeIdEditTextState(isIdValidate)
    }


    fun onDestroy() {

        if (::findPasswordDisposable.isInitialized && !findPasswordDisposable.isDisposed)
            findPasswordDisposable.dispose()

        view.dismissLoginLoadingDialog()
    }
}
