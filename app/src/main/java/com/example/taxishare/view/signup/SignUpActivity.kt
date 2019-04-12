package com.example.taxishare.view.signup

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.taxishare.R
import com.example.taxishare.app.Constant
import com.example.taxishare.data.remote.apis.server.ServerClient
import com.example.taxishare.view.BaseActivity
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.view.focusChanges
import com.jakewharton.rxbinding3.widget.itemSelections
import com.jakewharton.rxbinding3.widget.textChanges
import com.tedpark.tedpermission.rx2.TedRx2Permission
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.toast

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

    override fun sameIdExist() {
        text_input_layout_sign_up_std_id.error =
            resources.getString(R.string.sign_up_same_id_exist)
    }

    override fun sameIdNotExist() {
        text_input_layout_sign_up_std_id.error = null
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

    override fun changeIdEditTextState(isMatched: Boolean) {
        text_input_layout_sign_up_std_id.error =
            if (isMatched) null
            else resources.getString(R.string.common_student_id_pattern_not_match)
    }

    override fun changePwEditTextState(isMatched: Boolean) {
        text_input_layout_sign_up_pw.error =
            if (isMatched) null
            else resources.getString(R.string.common_pw_pattern_not_match)
    }

    override fun changePwConfirmEditTextState(isMatched: Boolean) {
        text_input_layout_sign_up_confirm_pw.error =
            if (isMatched) null
            else resources.getString(R.string.common_pw_not_same)
    }

    override fun changeNicknameEditTextState(isMatched: Boolean) {
        text_input_layout_sign_up_nick_name.error =
            if (isMatched) null
            else resources.getString(R.string.common_nickname_pattern_not_match)
    }

    override fun signUpSuccess() {
        toast(resources.getString(R.string.sign_up_request_success))
    }

    override fun signUpFail() {
        toast(resources.getString(R.string.sign_up_request_fail))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constant.GALLERY_REQUEST_CODE) {
            Log.d("Test", "Test")
        }
    }

    private fun initPresenter() {
        presenter = SignUpPresenter(this, ServerClient.getInstance())
    }

    @SuppressWarnings("all")
    private fun imageChangeBtnClicked() {
        TedRx2Permission.with(this)
            .setDeniedMessage(R.string.sign_up_image_permission_require)
            .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
            .request()
            .subscribe({
                // TODO : 갤러리로 넘어가기
                openGallery()
            },{
                // TODO : 오류 처리하기
                it.stackTrace[0]
            })
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constant.GALLERY_REQUEST_CODE)
    }

    private fun initView() {

    }

    /* Listener 작성 */
    @SuppressWarnings("all")
    private fun initListener() {

        // 이미지 선택 버튼 클릭
        iv_sign_up_profile_image_selection.clicks().subscribe({
            imageChangeBtnClicked()
        }, {
            it.stackTrace[0]
        })


        // 이미지 프로필 버튼 클릭
        iv_sign_up_profile_image.clicks().subscribe({
            imageChangeBtnClicked()
        }, {
            it.stackTrace[0]
        })


        // 아이디 양식 확인
        text_input_sign_up_std_id.focusChanges().skipInitialValue()
            .filter { !it }
            .subscribe({
                presenter.checkIsIdValidate(text_input_sign_up_std_id.text.toString())
            }, {
                it.stackTrace[0]
            })

        // 비밀번호 양식 확인
        text_input_sign_up_pw.focusChanges().skipInitialValue()
            .filter { !it }
            .subscribe({
                presenter.checkPwValidation(text_input_sign_up_pw.text.toString())
            }, {
                it.stackTrace[0]
            })


        // 비밀번호 동일 확인
        text_input_sign_up_confirm_pw.textChanges().skipInitialValue()
            .subscribe({
                presenter.checkPwConfirmed(
                    it.toString(),
                    text_input_sign_up_pw.text.toString()
                )
            }, {
                it.stackTrace[0]
            })


        // 닉네임 확인
        text_input_sign_up_nick_name.focusChanges().skipInitialValue()
            .filter { !it }
            .subscribe({
                presenter.checkNicknameValidation(it.toString())
            }, {
                it.stackTrace[0]
            })


        // 전공선택 확인
        spn_sign_up_major.itemSelections().skipInitialValue()
            .subscribe({
                presenter.checkMajorSelected(it)
            }, {
                it.stackTrace[0]
            })

        btn_sign_up_finish.clicks().subscribe({
            presenter.signUpRequest(
                text_input_sign_up_std_id.text.toString(),
                text_input_sign_up_pw.text.toString(),
                text_input_sign_up_nick_name.text.toString(),
                spn_sign_up_major.selectedItem.toString()
            )
        }, {
            it.stackTrace[0]
        })
    }
}
