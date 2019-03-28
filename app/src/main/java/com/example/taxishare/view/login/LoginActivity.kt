package com.example.taxishare.view.login

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.example.taxishare.R
import com.example.taxishare.util.RegularExpressionChecker
import com.example.taxishare.view.BaseActivity
import com.example.taxishare.view.password.FindPasswordActivity
import com.example.taxishare.view.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

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

        /* 정규식을 이용한 이메일 형식 확인 */
        text_input_login_id.doOnTextChanged { _, _, _, _ ->
            setLoginButtonClickable(text_input_login_pw.text!!.isNotEmpty() && text_input_login_id.text!!.isNotEmpty())

            text_layout_login_id.error =
                if (RegularExpressionChecker.checkEmailValidation(text_input_login_id.text.toString())) null
                else resources.getString(R.string.common_email_pattern_not_match)
        }

        /* */
        text_input_login_pw.doOnTextChanged { _, _, _, _ ->
            setLoginButtonClickable(text_input_login_pw.text!!.isNotEmpty() && text_input_login_id.text!!.isNotEmpty())
        }

        /* 로그인 요청 */
        btn_login_request.onClick {
            presenter.login(
                text_input_login_id.text.toString(),
                text_input_login_pw.text.toString()
            )
        }

        /* 자동 로그인 */
        text_login_remember.onClick { cb_login_remember.isChecked = !cb_login_remember.isChecked }

        /* SignUp 요청 */
        btn_login_sign_up.onClick { startActivity<SignUpActivity>() }

        /* ForgetPassword 요청 */
        btn_login_find_pw.onClick { startActivity<FindPasswordActivity>() }
    }

    /*
     * Login Button Enable setting */
    private fun setLoginButtonClickable(isClickable: Boolean) {
        btn_login_request.isEnabled = isClickable
        if (isClickable) {
            btn_login_request.backgroundDrawable =
                ContextCompat.getDrawable(this, R.drawable.background_enable_btn_round_corner)
        } else {
            btn_login_request.backgroundDrawable =
                ContextCompat.getDrawable(this, R.drawable.background_disable_btn_round_corner)
        }
    }

    private fun isAutoLoginGranted(isGranted : Boolean) {

    }
}
