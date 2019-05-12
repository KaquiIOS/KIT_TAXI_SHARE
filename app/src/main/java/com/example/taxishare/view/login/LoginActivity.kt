package com.example.taxishare.view.login

import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.taxishare.R
import com.example.taxishare.data.remote.apis.server.ServerClient
import com.example.taxishare.view.BaseActivity
import com.example.taxishare.view.main.MainActivity
import com.example.taxishare.view.password.FindPasswordActivity
import com.example.taxishare.view.signup.SignUpActivity
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit

class LoginActivity : BaseActivity(), LoginView {

    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPresenter()
        initListener()
    }

    override fun getLayoutId(): Int = R.layout.activity_login

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
        if (canActivate) {
            btn_login_request.backgroundDrawable =
                ContextCompat.getDrawable(this, R.drawable.background_enable_btn_round_corner)
        } else {
            btn_login_request.backgroundDrawable =
                ContextCompat.getDrawable(this, R.drawable.background_disable_btn_round_corner)
        }
    }

    override fun changeIdEditTextState(isMatched: Boolean) {
        text_layout_login_id.error =
            if (isMatched) null
            else resources.getString(R.string.common_email_pattern_not_match)
    }

    /*
     *  */
    override fun changePwEditTextState(isMatched: Boolean) {
        text_layout_login_pw.error =
            if (isMatched) null
            else resources.getString(R.string.common_pw_pattern_not_match)
    }

    /*
     * Presenter 초기화  */
    private fun initPresenter() {
        presenter = LoginPresenter(this, ServerClient.getInstance())
    }

    /*
     * View Listener 추가 */
    @SuppressWarnings("all")
    private fun initListener() {

        text_input_login_id.textChanges().subscribe({
            presenter.checkIdValidation(text_input_login_id.text.toString())
        }, {
            it.stackTrace[0]
        })

        text_input_login_pw.textChanges().subscribe({
            presenter.checkPwValidation(text_input_login_pw.text.toString())
        }, {
            it.stackTrace[0]
        })

        btn_login_request.clicks().debounce(500, TimeUnit.MILLISECONDS).subscribe({
            Log.d("Test", "Test")
            presenter.login(text_input_login_id.text.toString(), text_input_login_pw.text.toString())
        }, {
            it.stackTrace[0]
        })

        /* 자동 로그인 */
        text_login_remember.clicks().subscribe({
            cb_login_remember.isChecked = !cb_login_remember.isChecked
        }, {
            it.stackTrace[0]
        })

        /* SignUp 요청 */
        btn_login_sign_up.onClick { startActivity<SignUpActivity>() }

        /* ForgetPassword 요청 */
        btn_login_find_pw.onClick { startActivity<FindPasswordActivity>() }
    }

    private fun isAutoLoginGranted(isGranted: Boolean) {

    }
}
