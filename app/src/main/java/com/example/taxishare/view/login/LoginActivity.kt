package com.example.taxishare.view.login

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.taxishare.R
import com.example.taxishare.view.BaseActivity
import com.example.taxishare.view.password.FindPasswordActivity
import com.example.taxishare.view.signup.SignUpActivity
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import java.util.concurrent.TimeUnit

class LoginActivity : BaseActivity(), LoginView {

    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPresenter()
        initListener()
    }

    override fun getLayoutId(): Int = R.layout.activity_login

    /*
     * Presenter 초기화  */
    private fun initPresenter() {
        presenter = LoginPresenter(this)
    }

    /*
     * View Listener 추가 */
    private fun initListener() {
        compositeDisposable.add(text_input_login_id.textChanges().skipInitialValue().subscribe({
            presenter.checkIdValidation(text_input_login_id.text.toString())
        }, {
            it.stackTrace[0]
        }))

        compositeDisposable.add(text_input_login_pw.textChanges().skipInitialValue().subscribe({
            presenter.checkPwValidation(text_input_login_pw.text.toString())
        }, {
            it.stackTrace[0]
        }))

        compositeDisposable.add(btn_login_request.clicks().debounce(1, TimeUnit.SECONDS).subscribe({
            presenter.login(text_input_login_id.text.toString(), text_input_login_pw.text.toString())
        }, {
            it.stackTrace[0]
        }))

        /* 자동 로그인 */
        compositeDisposable.add(text_login_remember.clicks().subscribe({
            cb_login_remember.isChecked = !cb_login_remember.isChecked
        }, {
            it.stackTrace[0]
        }))

        /* SignUp 요청 */
        btn_login_sign_up.onClick { startActivity<SignUpActivity>() }

        /* ForgetPassword 요청 */
        btn_login_find_pw.onClick { startActivity<FindPasswordActivity>() }
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

    private fun isAutoLoginGranted(isGranted: Boolean) {

    }

    override fun changeIdInputState(isMatched: Boolean) {
        text_layout_login_id.error =
            if (isMatched) null
            else resources.getString(R.string.common_email_pattern_not_match)
    }

    override fun changePwInputState(isMatched: Boolean) {
        text_layout_login_pw.error =
            if (isMatched) null
            else resources.getString(R.string.common_pw_pattern_not_match)
    }
}
