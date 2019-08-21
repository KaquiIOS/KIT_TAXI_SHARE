package com.example.taxishare.view.signup

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.taxishare.R
import com.example.taxishare.customview.LoadingDialog
import com.example.taxishare.data.remote.apis.server.ServerClient
import com.example.taxishare.data.repo.ServerRepositoryImpl
import com.example.taxishare.view.BaseActivity
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.view.focusChanges
import com.jakewharton.rxbinding3.widget.itemSelections
import com.jakewharton.rxbinding3.widget.textChanges
import com.tedpark.tedpermission.rx2.TedRx2Permission
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.sdk27.coroutines.onTouch
import org.jetbrains.anko.toast

class SignUpActivity : BaseActivity(), SignUpView {

    private lateinit var presenter: SignUpPresenter

    private lateinit var alertDialog: AlertDialog

    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadingDialog = LoadingDialog.newInstance(R.string.sign_up_loading_message)

        alertDialog = with(AlertDialog.Builder(this)) {
            setCancelable(false)
            setView(R.layout.loading_dialog_layout)
            setMessage(R.string.login_loading_text)
        }.create()

        initPresenter()
        initView()
        initListener()
    }

    override fun getLayoutId(): Int = R.layout.activity_sign_up

    override fun sameIdExist() {
        text_input_layout_sign_up_std_id.error =
            resources.getString(R.string.sign_up_same_id_exist)
    }

    override fun showSignUpRequestLoadingDialog() {
        if (!loadingDialog.isVisible)
            loadingDialog.show(supportFragmentManager, "Test")
    }

    override fun dismissSignUpLoadingDialog() {
        if (loadingDialog.isVisible)
            loadingDialog.dismiss()
    }

    override fun showCheckEmailMessage() {
        AlertDialog.Builder(this)
            .setMessage(R.string.sign_up_check_email)
            .setPositiveButton(R.string.ok, null)
            .show()
    }

    override fun sameIdNotExist() {
        text_input_layout_sign_up_std_id.error = null
        text_input_layout_sign_up_std_id.helperText = resources.getString(R.string.sign_up_email_validation)
    }

    override fun sameNicknameExist() {
        text_input_layout_sign_up_nick_name.error = resources.getString(R.string.sign_up_same_nickname_exist)
    }

    override fun sameNicknameNotExist() {
        text_input_layout_sign_up_nick_name.error = null
        text_input_layout_sign_up_nick_name.helperText = resources.getString(R.string.sign_up_nickname_helper_text)
    }

    override fun changeSignUpButtonState(canActivate: Boolean) {
        btn_sign_up_finish.isEnabled = canActivate
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
        finish()
    }

    override fun signUpFail() {
        toast(resources.getString(R.string.sign_up_request_fail))
    }

    private fun initPresenter() {
        presenter = SignUpPresenter(this, ServerRepositoryImpl(ServerClient.getInstance()))
    }

    @SuppressWarnings("all")
    private fun imageChangeBtnClicked() {
        TedRx2Permission.with(this)
            .setDeniedMessage(R.string.sign_up_image_permission_require)
            .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
            .request()
            .subscribe({
                // TODO : 갤러리로 넘어가기
                slideBottomSheet()
            }, {
                // TODO : 오류 처리하기
                it.stackTrace[0]
            })
    }


    private fun slideBottomSheet() {
        val bottomSheetFragment = BottomSheetFragment()
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }

    private fun initView() {

    }

    /* Listener 작성 */
    @SuppressWarnings("all")
    private fun initListener() {

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
                presenter.checkNicknameValidation(text_input_sign_up_nick_name.text.toString())
            }, {
                it.stackTrace[0]
            })


        spn_sign_up_major.onTouch { v, _ -> v.requestFocusFromTouch() }

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
