package com.example.taxishare.view.signup

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.example.taxishare.R
import com.example.taxishare.util.RegularExpressionChecker
import com.example.taxishare.view.BaseActivity
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.disposables.Disposables
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.textColor

class SignUpActivity : BaseActivity(), SignUpView {

    companion object {
        private const val ALL_REQUEST_CHECKED = 62
    }

    private lateinit var presenter: SignUpPresenter
    private var isAllRequestChecked: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        iv_sign_up_profile_image.clipToOutline = true

        initPresenter()
        initView()
        initListener()
    }

    override fun getLayoutId(): Int = R.layout.activity_sign_up

    private fun initPresenter() {
        presenter = SignUpPresenter(this)
    }

    private fun initView() {
        // TODO : View 처리할 내용들 설정
    }

    /* Listener 작성 */
    private fun initListener() {
        // TODO : Listener 동작 설정
        iv_sign_up_profile_image_selection.onClick { imageChangeBtnClicked() }


        with(text_input_sign_up_std_id) {
            doAfterTextChanged {
                isPatternMatched(
                    text_input_sign_up_std_id,
                    RegularExpressionChecker.checkStudentIdValidation(text_input_sign_up_std_id.text.toString()),
                    1
                )
            }
        }

        with(text_input_sign_up_pw) {
            doAfterTextChanged {
                isPatternMatched(
                    this,
                    RegularExpressionChecker.checkPasswordValidation(text.toString()),
                    2
                )
            }
        }

        with(text_input_sign_up_confirm_pw) {
            doAfterTextChanged {
                isPatternMatched(
                    this,
                    text.toString() == text_input_sign_up_pw.text.toString(),
                    3
                )
            }
        }

        with(text_input_sign_up_nick_name) {
            doAfterTextChanged {
                isPatternMatched(
                    this,
                    RegularExpressionChecker.checkNicknameValidation(text.toString()),
                    4
                )
            }
        }

        spn_sign_up_major.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) =
                isPatternMatched(
                    null,
                    position != 0,
                    5
                )
        }
    }

    private fun imageChangeBtnClicked() {
        // TODO :
    }

    private fun isPatternMatched(editText: TextInputEditText?, isMatched: Boolean, attrNum: Int) {
        editText?.textColor = ContextCompat.getColor(this, if (isMatched) R.color.common_blue else R.color.warning_red)
        isAllRequestChecked = when (isMatched) {
            true -> isAllRequestChecked or (1 shl attrNum)
            false -> isAllRequestChecked and (Int.MAX_VALUE - (1 shl attrNum))
        }
        Log.d("Test", "$isAllRequestChecked")
        setLoginButtonClickable(isAllRequestChecked == ALL_REQUEST_CHECKED)
    }


    private fun setLoginButtonClickable(isClickable: Boolean) {
        btn_sign_up_finish.isEnabled = isClickable
        if (isClickable) {
            btn_sign_up_finish.backgroundDrawable =
                ContextCompat.getDrawable(this, R.drawable.background_enable_btn_round_corner)
        } else {
            btn_sign_up_finish.backgroundDrawable =
                ContextCompat.getDrawable(this, R.drawable.background_disable_btn_round_corner)
        }
    }
}
