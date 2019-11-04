package com.meongbyeol.taxishare.view.login

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.meongbyeol.taxishare.R
import com.meongbyeol.taxishare.customview.LoadingDialog
import com.meongbyeol.taxishare.data.local.sharedpreference.SharedPreferenceManager
import com.meongbyeol.taxishare.data.remote.apis.server.ServerClient
import com.meongbyeol.taxishare.data.repo.ServerRepositoryImpl
import com.meongbyeol.taxishare.view.BaseActivity
import com.meongbyeol.taxishare.view.main.MainActivity
import com.meongbyeol.taxishare.view.password.FindPasswordActivity
import com.meongbyeol.taxishare.view.signup.SignUpActivity
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : BaseActivity(), LoginView {

    private lateinit var presenter: LoginPresenter

    private lateinit var alertDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        alertDialog = LoadingDialog.newInstance(R.string.login_loading_text)

        initPresenter()
        initListener()

        presenter.onCreate()
    }

    override fun showPatchMessage() {
        AlertDialog.Builder(this)
            .setTitle(R.string.patch_title)
            .setMessage(R.string.patch_content)
            .setPositiveButton(R.string.ok, { _, _ -> finish() })
            .setNegativeButton(R.string.cancel, { _, _ -> finish() })
            .setCancelable(false)
            .create()
    }

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun writeSavedId(id: String) {
        text_input_login_id.setText(id)
    }

    override fun writeSavedPw(pw: String) {
        text_input_login_pw.setText(pw)
    }

    override fun loginSuccess() {
        startActivity<MainActivity>()
    }

    override fun loginFail() {
        toast(resources.getString(R.string.login_fail))
    }

    override fun notValidatedUserMessage() {
        toast(R.string.login_not_validated_user)
    }

    override fun changeLoginButtonState(canActivate: Boolean) {
        btn_login_request.isEnabled = canActivate
    }

    override fun changeIdEditTextState(isMatched: Boolean) {
        text_layout_login_id.error =
            if (isMatched) null
            else resources.getString(R.string.sign_up_student_id_error)
    }

    override fun checkAutoLogin() {
        cb_login_remember.isChecked = true
    }

    override fun okSign() {
        Log.d("Test", "FromPresenter")
    }

    /*
                 * Presenter 초기화  */
    private fun initPresenter() {
        presenter = LoginPresenter(
            this,
            ServerRepositoryImpl.getInstance(ServerClient.getInstance()),
            SharedPreferenceManager.getInstance(this)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    /*
         * View Listener 추가 */
    @SuppressWarnings("all")
    private fun initListener() {

        text_input_login_id.textChanges().skipInitialValue().subscribe({
            presenter.checkIdValidation(text_input_login_id.text.toString())
        }, {
            it.stackTrace[0]
        })

        text_input_login_pw.textChanges().skipInitialValue().subscribe({
            presenter.checkPwValidation(text_input_login_pw.text.toString())
        }, {
            it.stackTrace[0]
        })

        btn_login_request.clicks().subscribe({
            presenter.loginWithIdPw(
                text_input_login_id.text.toString(),
                text_input_login_pw.text.toString()
            )
        }, {
            it.stackTrace[0]
        })

        btn111.setOnClickListener {
            presenter.onCreate()
        }

        /* 자동 로그인 */
        text_login_remember.clicks().subscribe({
            cb_login_remember.isChecked = !cb_login_remember.isChecked
            presenter.changeCheckBoxState(cb_login_remember.isChecked)
        }, {
            it.stackTrace[0]
        })

        cb_login_remember.onClick {
            presenter.changeCheckBoxState(cb_login_remember.isChecked)
        }

        /* SignUp 요청 */
        btn_login_sign_up.onClick { startActivity<SignUpActivity>() }

        /* ForgetPassword 요청 */
        btn_login_find_pw.onClick { startActivity<FindPasswordActivity>() }
    }

    override fun showLoginLoadingDialog() {
        if (!alertDialog.isVisible)
            alertDialog.show(supportFragmentManager, "TEST")
    }

    override fun dismissLoginLoadingDialog() {
        if (alertDialog.isVisible)
            alertDialog.dismiss()
    }
}
