package com.example.taxishare.view.signup

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.taxishare.R
import com.example.taxishare.view.BaseActivity
import com.google.android.material.textfield.TextInputEditText
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.itemSelections
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.textColor

class SignUpActivity : BaseActivity(), SignUpView {

    private lateinit var presenter: SignUpPresenter

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

        // 이미지 선택 버튼 클릭
        compositeDisposable.add(
            iv_sign_up_profile_image_selection.clicks().subscribe({

            }, {
                it.stackTrace[0]
            })
        )

        // 이미지 프로필 버튼 클릭
        compositeDisposable.add(
            iv_sign_up_profile_image.clicks().subscribe({

            }, {
                it.stackTrace[0]
            })
        )

        // 아이디 양식 확인
        compositeDisposable.add(
            text_input_sign_up_std_id.textChanges().skipInitialValue()
                .subscribe({
                    presenter.checkStudentIdValidation(it.toString())
                }, {
                    it.stackTrace[0]
                })
        )

        // 비밀번호 양식 확인
        compositeDisposable.add(
            text_input_sign_up_pw.textChanges().skipInitialValue()
                .subscribe({
                    presenter.checkPwValidation(it.toString())
                }, {
                    it.stackTrace[0]
                })
        )

        // 비밀번호 동일 확인
        compositeDisposable.add(
            text_input_sign_up_confirm_pw.textChanges().skipInitialValue()
                .subscribe({
                    presenter.checkPwConfirmed(it.toString(), text_input_sign_up_pw.text.toString())
                }, {
                    it.stackTrace[0]
                })
        )

        // 닉네임 확인
        compositeDisposable.add(
            text_input_sign_up_nick_name.textChanges().skipInitialValue()
                .subscribe({
                    presenter.checkNicknameValidation(it.toString())
                }, {
                    it.stackTrace[0]
                })
        )

        // 전공선택 확인
        compositeDisposable.add(
            spn_sign_up_major.itemSelections().skipInitialValue()
                .subscribe({
                    presenter.checkMajorSelected(it)
                }, {
                    it.stackTrace[0]
                })
        )
    }

    private fun imageChangeBtnClicked() {
        // TODO :
    }

    override fun changeSignUpButtonState(canActivate: Boolean) {
        btn_sign_up_finish.isEnabled = canActivate
        if (canActivate) {
            btn_sign_up_finish.backgroundDrawable =
                ContextCompat.getDrawable(this, R.drawable.background_enable_btn_round_corner)
        } else {
            btn_sign_up_finish.backgroundDrawable =
                ContextCompat.getDrawable(this, R.drawable.background_disable_btn_round_corner)
        }
    }

    override fun changeEditTextColor(editText: TextInputEditText, isMatched: Boolean) {
        editText.textColor = ContextCompat.getColor(this, if (isMatched) R.color.common_blue else R.color.warning_red)
    }

    override fun changeIdEditTextState(isMatched: Boolean) {
        changeEditTextColor(text_input_sign_up_std_id, isMatched)
    }

    override fun changePwEditTextState(isMatched: Boolean) {
        changeEditTextColor(text_input_sign_up_pw, isMatched)
    }

    override fun changePwConfirmEditTextState(isMatched: Boolean) {
        changeEditTextColor(text_input_sign_up_confirm_pw, isMatched)
    }

    override fun changeNicknameEditTextState(isMatched: Boolean) {
        changeEditTextColor(text_input_sign_up_nick_name, isMatched)
    }
}
