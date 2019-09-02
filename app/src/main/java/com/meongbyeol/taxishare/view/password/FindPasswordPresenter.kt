package com.meongbyeol.taxishare.view.password

import com.meongbyeol.taxishare.data.model.ServerResponse
import com.meongbyeol.taxishare.data.remote.apis.server.request.FindPasswordRequest
import com.meongbyeol.taxishare.data.repo.ServerRepository
import com.meongbyeol.taxishare.util.RegularExpressionChecker
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
        isIdValidate = RegularExpressionChecker.checkStudentIdValidation(id) && id.indexOf("@",0) == -1
        view.changeLoginButtonState(isIdValidate)
        view.changeIdEditTextState(isIdValidate)
    }


    fun onDestroy() {

        if (::findPasswordDisposable.isInitialized && !findPasswordDisposable.isDisposed)
            findPasswordDisposable.dispose()

        view.dismissLoginLoadingDialog()
    }
}
