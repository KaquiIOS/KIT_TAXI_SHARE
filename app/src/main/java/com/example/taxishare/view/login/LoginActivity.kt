package com.example.taxishare.view.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.taxishare.R
import com.example.taxishare.util.RegularExpressionChecker
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.backgroundDrawable

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initPresenter()
        initListener()
    }

    /*
     * Presenter 초기화  */
    private fun initPresenter() {
        presenter = LoginPresenter()
    }

    /*
     * View Listener 추가 */
    private fun initListener() {
        text_input_login_id.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setLoginButtonClickable(text_input_login_pw.text!!.isNotEmpty() && text_input_login_id.text!!.isNotEmpty())
                if (!RegularExpressionChecker.checkEmailValidation(s.toString())) {
                    text_layout_login_id.helperText = resources.getString(R.string.login_email_not_match)
                }
            }
        })

        text_input_login_pw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setLoginButtonClickable(text_input_login_pw.text!!.isNotEmpty() && text_input_login_id.text!!.isNotEmpty())
            }
        })
    }

    /*
     * Login Button Enable setting */
    private fun setLoginButtonClickable(isClickable: Boolean) {
        btn_login_request.isEnabled = isClickable
        if (isClickable) {
            btn_login_request.backgroundDrawable = resources.getDrawable(R.drawable.background_enable_btn_round_corner)
        } else {
            btn_login_request.backgroundDrawable = resources.getDrawable(R.drawable.background_disable_btn_round_corner)
        }
    }
}
