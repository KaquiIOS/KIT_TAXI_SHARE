package com.meongbyeol.taxishare.view.main.taxisharelist.detail

import android.app.Activity
import android.app.AlarmManager
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meongbyeol.taxishare.R
import com.meongbyeol.taxishare.app.AlarmManagerImpl
import com.meongbyeol.taxishare.app.Constant
import com.meongbyeol.taxishare.data.mapper.TypeMapper
import com.meongbyeol.taxishare.data.model.Comment
import com.meongbyeol.taxishare.data.model.TaxiShareDetailInfo
import com.meongbyeol.taxishare.data.model.TaxiShareInfo
import com.meongbyeol.taxishare.data.remote.apis.server.ServerClient
import com.meongbyeol.taxishare.data.repo.ServerRepositoryImpl
import com.meongbyeol.taxishare.extension.observeBottomDetectionPublisher
import com.meongbyeol.taxishare.extension.setOnBottomDetection
import com.meongbyeol.taxishare.util.KeyboardHideUtil
import com.meongbyeol.taxishare.view.main.register.RegisterTaxiShareActivity
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.activity_taxi_share_info_detail.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.textColor
import org.jetbrains.anko.toast
import java.util.*

class TaxiShareInfoDetailActivity : AppCompatActivity(), TaxiShareInfoDetailView {

    private lateinit var currentTaxiShareInfo: TaxiShareDetailInfo
    private lateinit var presenter: TaxiShareInfoDetailPresenter
    private lateinit var adapter: TaxiShareInfoCommentListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taxi_share_info_detail)

        initPresenter()
        initAdapter()
        initView()

        with(intent.getSerializableExtra(Constant.TAXISHARE_DETAIL_STR) as TaxiShareInfo) {
            presenter.loadDetailTaxiShareInfo(this.id, this.uid)
            presenter.loadComments(this.id, true)
        }
    }

    override fun disableSendBtn() {
        btn_taxi_share_detail_comment_send.visibility = View.GONE
        if(!pb_taxi_share_detail_comment_send.isVisible)
            pb_taxi_share_detail_comment_send.visibility = View.VISIBLE
    }

    override fun enableSendBtn() {
        btn_taxi_share_detail_comment_send.visibility = View.VISIBLE
        if(pb_taxi_share_detail_comment_send.isVisible)
            pb_taxi_share_detail_comment_send.visibility = View.GONE
    }

    override fun loadDetailInfoNotFinish() {
        toast(getString(R.string.load_detail_taxi_info_waiting))
    }

    override fun failLoadDetailInfo() {
        toast(getString(R.string.load_detail_taxi_info_fail))
        finish()
        //setViewItem()
    }

    override fun setDetailInfo(taxiShareInfo: TaxiShareDetailInfo) {
        currentTaxiShareInfo = taxiShareInfo
        setViewItem()
        initListener()
    }

    override fun changeRegisterButtonState(isActivated: Boolean) {
        btn_taxi_share_detail_comment_send.isEnabled = isActivated
    }

    override fun addComments(commentList: MutableList<Comment>) {
        adapter.setComments(commentList)
    }

    override fun insertComment(comment: Comment) {
        adapter.insertComment(comment)
        rcv_taxi_share_detail_comments.scrollToPosition(0)
    }

    override fun registerCommentSuccess() {
        toast(getString(R.string.register_comment_success))
    }

    override fun registerCommentFail() {
        toast(getString(R.string.register_comment_fail))
    }

    override fun registerCommentNotFinish() {
        toast(getString(R.string.register_comment_waiting))
    }

    override fun noCommentExist() {
        toast(getString(R.string.no_comment_exist))
    }

    override fun loadCommentSuccess() {
        toast(getString(R.string.load_comment_success))
    }

    override fun loadCommentFail() {
        toast(getString(R.string.load_comment_fail))
    }

    override fun loadCommentNotFinished() {
        toast(getString(R.string.load_comment_waiting))
    }

    override fun clearText() {
        et_taxi_share_detail_comment_input.text.clear()
    }

    override fun removeCommentSuccess(commentId: Int) {
        adapter.removeComment(commentId)
        toast(getString(R.string.remove_comment_success))
    }

    override fun disableAllComponents() {
        btn_taxi_share_detail_participate.isEnabled = false
        btn_taxi_share_detail_participate.text = getString(R.string.already_taxi_share_started)
        et_taxi_share_detail_comment_input.isEnabled = false
        et_taxi_share_detail_comment_input.setText(getString(R.string.taxi_share_closed))
        btn_taxi_share_detail_comment_send.isEnabled = false
    }

    private fun enableAllComponents() {
        btn_taxi_share_detail_comment_send.isEnabled = true
        et_taxi_share_detail_comment_input.isEnabled = true
        et_taxi_share_detail_comment_input.setText("")
        setViewItem()
    }

    override fun removeCommentFail() {
        toast(getString(R.string.remove_comment_fail))
    }

    override fun removeCommentNotFinished() {
        toast(getString(R.string.remove_comment_waiting))
    }

    override fun saveCurrentTaxiShareInfo() {
        setResult(
            when (currentTaxiShareInfo.isParticipated) {
                true -> Activity.RESULT_OK
                else -> Constant.LEAVE_TAXI_PARTY
            }, Intent().putExtra(
                Constant.TAXISHARE_DETAIL_STR, TaxiShareInfo(
                    currentTaxiShareInfo.id,
                    currentTaxiShareInfo.uid,
                    currentTaxiShareInfo.title,
                    currentTaxiShareInfo.startDate,
                    currentTaxiShareInfo.startLocation,
                    currentTaxiShareInfo.endLocation,
                    currentTaxiShareInfo.limit,
                    currentTaxiShareInfo.nickname,
                    currentTaxiShareInfo.major,
                    currentTaxiShareInfo.participantsNum,
                    currentTaxiShareInfo.isParticipated
                )
            )
        )
    }

    override fun showParticipateTaxiShareSuccess() {
        currentTaxiShareInfo.isParticipated = true
        currentTaxiShareInfo.participantsNum += 1
        currentTaxiShareInfo.participants.add(currentTaxiShareInfo.uid)
        tv_taxi_share_detail_participants.text =
            String.format(
                getString(R.string.taxi_share_participants_member),
                currentTaxiShareInfo.participants.toString()
            )
        btn_taxi_share_detail_participate.setBackgroundResource(R.drawable.background_already_participate_color)
        btn_taxi_share_detail_participate.textColor = R.color.light_gray
        btn_taxi_share_detail_participate.text =
            String.format(
                getString(R.string.already_participate_taxi_share_title)
            )
        toast(getString(R.string.participate_taxi_share_success))
    }

    override fun showParticipateTaxiShareFail() {
        toast(getString(R.string.participate_taxi_share_fail))
    }

    override fun showParticipateTaxiShareNotFinish() {
        toast(getString(R.string.participate_taxi_share_waiting))
    }

    override fun detailInfoDeleted() {
        toast(getString(R.string.already_removed_taxi_share))
        setTaxiShareInfoDeletedFlag()
    }

    override fun showLeaveTaxiShareSuccess() {
        currentTaxiShareInfo.isParticipated = false
        currentTaxiShareInfo.participantsNum -= 1
        currentTaxiShareInfo.participants.remove(currentTaxiShareInfo.uid)

        tv_taxi_share_detail_participants.text =
            String.format(
                getString(R.string.taxi_share_participants_member),
                currentTaxiShareInfo.participants.toString()
            )
        btn_taxi_share_detail_participate.setBackgroundResource(R.drawable.background_not_participate_color)
        btn_taxi_share_detail_participate.textColor = R.color.common_black
        btn_taxi_share_detail_participate.text =
            String.format(
                getString(R.string.taxi_share_participants_num)
            )
        toast(getString(R.string.leave_taxi_share_success))
    }

    override fun showLeaveTaxiShareFail() {
        toast(getString(R.string.leave_taxi_share_fail))
    }

    override fun showLeaveTaxiShareNotFinish() {
        toast(getString(R.string.leave_taxi_share_waiting))
    }

    override fun showRemoveTaxiShareSuccess() {
        setTaxiShareInfoDeletedFlag()
        toast(getString(R.string.remove_taxi_share_success))
    }

    override fun showRemoveTaxiShareFail() {
        toast(getString(R.string.remove_taxi_share_fail))
    }

    override fun showRemoveTaxiShareNotFinish() {
        toast(getString(R.string.remove_taxi_share_waiting))
    }

    override fun hideKeyboard() {
        KeyboardHideUtil.keyboardStateChange(this, true)
    }

    private fun setTaxiShareInfoDeletedFlag() {
        setResult(
            Constant.DATA_REMOVED,
            Intent().putExtra(Constant.POST_ID, currentTaxiShareInfo.id)
        )
        finish()
    }

    private fun setViewItem() {
        with(currentTaxiShareInfo) {
            tv_taxi_share_detail_leader.text =
                String.format(getString(R.string.nickname_format), nickname, major)
            tv_taxi_share_detail_start_time.text =
                String.format(
                    getString(R.string.taxi_list_start_time),
                    TypeMapper.dateToString(startDate)
                )
            tv_taxi_share_detail_start_location.text = startLocation.locationName
            tv_taxi_share_detail_end_location.text = endLocation.locationName
            tv_taxi_share_detail_title.text = title

            tv_taxi_share_detail_participants_title.text =
                String.format(
                    getString(R.string.taxi_share_participants_member_title),
                    participantsNum
                )

            tv_taxi_share_detail_participants.text =
                String.format(
                    getString(R.string.taxi_share_participants_member),
                    participants.toString()
                )

            Calendar.getInstance().apply {
                time = currentTaxiShareInfo.startDate
                add(Calendar.MINUTE, 30)
            }.apply {
                if (after(this)) {
                    et_taxi_share_detail_comment_input.setText(getString(R.string.taxi_share_closed))
                    btn_taxi_share_detail_comment_send.isEnabled = false
                }
            }

            if (Constant.CURRENT_USER.studentId.toString() == uid) {
                btn_taxi_share_detail_participate.text =
                    String.format(resources.getString(R.string.my_taxi_share_title))
                btn_taxi_share_detail_participate.setBackgroundResource(R.drawable.background_already_participate_color)
                btn_taxi_share_detail_participate.textColor = R.color.light_gray
                btn_taxi_share_detail_participate.isEnabled = false
            } else if (isParticipated) {
                btn_taxi_share_detail_participate.text =
                    String.format(resources.getString(R.string.already_participate_taxi_share_title))
                btn_taxi_share_detail_participate.setBackgroundResource(R.drawable.background_already_participate_color)
                btn_taxi_share_detail_participate.textColor = R.color.light_gray
            } else {
                btn_taxi_share_detail_participate.text =
                    String.format(resources.getString(R.string.taxi_share_participants_num))
                btn_taxi_share_detail_participate.setBackgroundResource(R.drawable.background_not_participate_color)
                btn_taxi_share_detail_participate.textColor = R.color.common_black
            }
        }
    }

    private fun initView() {

        rcv_taxi_share_detail_comments.apply {
            adapter = this@TaxiShareInfoDetailActivity.adapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            //addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            itemAnimator = null
            setOnBottomDetection()
        }
    }

    private fun initPresenter() {
        presenter =
            TaxiShareInfoDetailPresenter(
                this, ServerRepositoryImpl.getInstance(ServerClient.getInstance()),
                AlarmManagerImpl(
                    getSystemService(Context.ALARM_SERVICE) as AlarmManager,
                    this@TaxiShareInfoDetailActivity
                )
            ).apply {
                setOnBottomDetectSubscriber(rcv_taxi_share_detail_comments.observeBottomDetectionPublisher())
            }
    }

    @SuppressWarnings("all")
    private fun initListener() {

        btn_taxi_share_detail_participate.onClick {
            if (currentTaxiShareInfo.isParticipated) {
                AlertDialog.Builder(this@TaxiShareInfoDetailActivity)
                    .setTitle(getString(R.string.taxi_share_leave_title))
                    .setMessage(getString(R.string.taxi_share_leave_content))
                    .setPositiveButton(getString(R.string.ok), ({ _, _ ->
                        presenter.leaveTaxiShare(currentTaxiShareInfo.id)
                    }))
                    .setNegativeButton(getString(R.string.cancel), null)
                    .setCancelable(false)
                    .show()
            } else {
                presenter.participateTaxiShare(currentTaxiShareInfo.id)
            }
        }

        et_taxi_share_detail_comment_input.textChanges().skipInitialValue().subscribe({
            presenter.changeButtonState(it.toString())
        }, {
            it.printStackTrace()
        })

        rcv_taxi_share_detail_comments.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    presenter.loadComments(currentTaxiShareInfo.id, false)
                }
            }
        })

        adapter.setCommentRemoveClickListener(object : CommentItemRemoveClickListener {
            override fun onClick(commentId: Int) {
                presenter.removeComment(commentId)
            }
        })

        btn_taxi_share_detail_comment_send.onClick {
            presenter.registerComment(
                currentTaxiShareInfo.id,
                Constant.CURRENT_USER.studentId.toString(),
                et_taxi_share_detail_comment_input.text.toString()
            )
        }

        tb_taxi_share_detail.setNavigationOnClickListener {
            finish()
        }

        if (Constant.CURRENT_USER.studentId == currentTaxiShareInfo.uid.toInt()) {
            btn_taxi_share_detail_delete.visibility = View.VISIBLE
            btn_taxi_share_detail_delete.onClick {
                // 삭제 작업 수행
                AlertDialog.Builder(this@TaxiShareInfoDetailActivity)
                    .setTitle(getString(R.string.taxi_share_remove_title))
                    .setMessage(getString(R.string.taxi_share_remove_content))
                    .setPositiveButton(getString(R.string.ok), ({ _, _ ->
                        presenter.removeTaxiShare(currentTaxiShareInfo.id)
                    }))
                    .setNegativeButton(getString(R.string.cancel), null)
                    .setCancelable(false)
                    .show()
            }

            btn_taxi_share_detail_edit.visibility = View.VISIBLE
            btn_taxi_share_detail_edit.onClick {

                Intent(this@TaxiShareInfoDetailActivity, RegisterTaxiShareActivity::class.java)
                    .apply {
                        putExtra(Constant.MODIFY_TAXI_SHARE_STR, with(currentTaxiShareInfo) {
                            TaxiShareInfo(
                                id, uid, title, startDate, startLocation, endLocation,
                                limit, nickname, major, participantsNum, isParticipated
                            )
                        })
                        startActivityForResult(this, Constant.MODIFY_TAXI_SHARE)
                    }
            }
        }

        btn_taxi_share_detail_refresh.onClick {
            adapter.clear()
            presenter.loadComments(currentTaxiShareInfo.id, true)
            presenter.loadDetailTaxiShareInfo(currentTaxiShareInfo.id, currentTaxiShareInfo.uid)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.MODIFY_TAXI_SHARE && data != null) {
            val temp = (data.getSerializableExtra(Constant.MODIFY_TAXI_SHARE_STR) as TaxiShareInfo)
            currentTaxiShareInfo = TaxiShareDetailInfo(
                currentTaxiShareInfo.id,
                currentTaxiShareInfo.uid,
                temp.title,
                temp.startDate,
                temp.startLocation,
                temp.endLocation,
                temp.limit,
                currentTaxiShareInfo.nickname,
                currentTaxiShareInfo.major,
                currentTaxiShareInfo.participantsNum,
                currentTaxiShareInfo.isParticipated,
                currentTaxiShareInfo.participants
            )
            setViewItem()

            if (System.currentTimeMillis() > temp.startDate.time + Constant.ALARM_NOTIFY_TIME) {
                disableAllComponents()
            } else {
                enableAllComponents()
            }

            saveCurrentTaxiShareInfo()
        }
    }

    private fun initAdapter() {
        adapter = TaxiShareInfoCommentListAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
