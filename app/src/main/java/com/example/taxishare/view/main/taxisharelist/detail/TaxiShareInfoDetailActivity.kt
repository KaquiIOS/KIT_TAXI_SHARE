package com.example.taxishare.view.main.taxisharelist.detail

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taxishare.R
import com.example.taxishare.app.Constant
import com.example.taxishare.data.mapper.TypeMapper
import com.example.taxishare.data.model.Comment
import com.example.taxishare.data.model.TaxiShareInfo
import com.example.taxishare.data.remote.apis.server.ServerClient
import com.example.taxishare.data.repo.ServerRepositoryImpl
import com.example.taxishare.extension.observeBottomDetectionPublisher
import com.example.taxishare.extension.setOnBottomDetection
import com.example.taxishare.view.main.register.RegisterTaxiShareActivity
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.activity_taxi_share_info_detail.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.textColor
import org.jetbrains.anko.toast

class TaxiShareInfoDetailActivity : AppCompatActivity(), TaxiShareInfoDetailView {

    private lateinit var currentTaxiShareInfo: TaxiShareInfo
    private lateinit var presenter: TaxiShareInfoDetailPresenter
    private lateinit var adapter: TaxiShareInfoCommentListAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taxi_share_info_detail)

        getDetailTaxiShareInfoFromIntent()

        initPresenter()
        initAdapter()
        initView()
        initListener()

        presenter.loadDetailTaxiShareInfo(currentTaxiShareInfo.id, currentTaxiShareInfo.uid)
        presenter.loadComments(currentTaxiShareInfo.id, true)
    }

    override fun loadDetailInfoNotFinish() {
        toast("데이터를 불러오는 중입니다.")
    }

    override fun failLoadDetailInfo() {
        toast("데이터 불러오기를 실패하였습니다. 이전 데이터를 출력합니다")
        setViewItem()
    }

    override fun setDetailInfo(taxiShareInfo: TaxiShareInfo) {
        currentTaxiShareInfo = taxiShareInfo
        setViewItem()
    }

    override fun changeRegisterButtonState(isActivated: Boolean) {
        btn_taxi_share_detail_comment_send.isEnabled = isActivated
    }

    override fun addComments(commentList: MutableList<Comment>) {
        adapter.setComments(commentList)
    }

    override fun insertComment(comment: Comment) {
        adapter.insertComment(comment)
    }

    override fun registerCommentSuccess() {
        toast("댓글 등록이 완료되었습니다.")
    }

    override fun registerCommentFail() {
        toast("댓글 등록에 실패하였습니다.")
    }

    override fun registerCommentNotFinish() {
        toast("댓글 등록중입니다")
    }

    override fun noCommentExist() {
        toast("댓글이 더 존재하지 않습니다")
    }

    override fun loadCommentSuccess() {
        toast("댓글을 불러왔습니다.")
    }

    override fun loadCommentFail() {
        toast("댓글 불러오기에 실패하였습니다")
    }

    override fun loadCommentNotFinished() {
        toast("댓글을 불러오는 중입니다")
    }

    override fun removeCommentSuccess(commentId: Int) {
        adapter.removeComment(commentId)
        toast("댓글 삭제에 성공했습니다")
    }

    override fun removeCommentFail() {
        toast("댓글 삭제에 실패했습니다. 다시 시도하여 주십시오")
    }

    override fun removeCommentNotFinished() {
        toast("댓글 삭제중입니다")
    }

    override fun showParticipateTaxiShareSuccess() {
        currentTaxiShareInfo.isParticipated = true
        currentTaxiShareInfo.participantsNum += 1
        btn_taxi_share_detail_participate.setBackgroundResource(R.drawable.background_already_participate_color)
        btn_taxi_share_detail_participate.textColor = R.color.light_gray
        btn_taxi_share_detail_participate.text =
            String.format("이미 참여중인 글입니다.(%d)", currentTaxiShareInfo.participantsNum)
        toast("택시 합승에 참여하였습니다")
    }

    override fun showParticipateTaxiShareFail() {
        toast("택시 합승에 실패하였습니다")
    }

    override fun showParticipateTaxiShareNotFinish() {
        toast("택시 합승을 요청중입니다")
    }

    override fun detailInfoDeleted() {
        toast("삭제된 글입니다")
        setTaxiShareInfoDeletedFlag()
    }

    override fun showLeaveTaxiShareSuccess() {
        currentTaxiShareInfo.isParticipated = false
        currentTaxiShareInfo.participantsNum -= 1
        btn_taxi_share_detail_participate.setBackgroundResource(R.drawable.background_not_participate_color)
        btn_taxi_share_detail_participate.textColor = R.color.common_black
        btn_taxi_share_detail_participate.text =
            String.format("현재 참여 %d 명 (%d)", currentTaxiShareInfo.participantsNum, currentTaxiShareInfo.limit)
        toast("택시 합승을 취소했습니다")
    }

    override fun showLeaveTaxiShareFail() {
        toast("택시 합승 취소를 실패했습니다")
    }

    override fun showLeaveTaxiShareNotFinish() {
        toast("택시 합승 취소를 요청중입니다")
    }

    override fun showRemoveTaxiShareSuccess() {
        toast("택시 합승 삭제를 성공하였습니다.")
        setTaxiShareInfoDeletedFlag()
    }

    override fun showRemoveTaxiShareFail() {
        toast("택시 합승 삭제에 실패하였습니다.")
    }

    override fun showRemoveTaxiShareNotFinish() {
        toast("택시 합승 삭제를 요청중입니다.")
    }

    private fun setReturnTaxiShareInfo() {
        if (intent.getSerializableExtra(Constant.TAXISHARE_DETAIL_STR) as TaxiShareInfo != currentTaxiShareInfo) {
            setResult(Activity.RESULT_OK, Intent().putExtra(Constant.TAXISHARE_DETAIL_STR, currentTaxiShareInfo))
        }
        finish()
    }

    private fun setTaxiShareInfoDeletedFlag() {
        setResult(Constant.DATA_REMOVED, Intent().putExtra("postId", currentTaxiShareInfo.id))
        finish()
    }

    private fun getDetailTaxiShareInfoFromIntent() {
        currentTaxiShareInfo = intent.getSerializableExtra(Constant.TAXISHARE_DETAIL_STR) as TaxiShareInfo
    }

    private fun setViewItem() {
        with(currentTaxiShareInfo) {
            tv_taxi_share_detail_leader.text = String.format("%s (%s)", nickname, major)
            tv_taxi_share_detail_start_time.text = TypeMapper.dateToString(startDate)
            tv_taxi_share_detail_start_location.text = startLocation.locationName
            tv_taxi_share_detail_end_location.text = endLocation.locationName
            tv_taxi_share_detail_title.text = title

            if (Constant.USER_ID == uid) {
                btn_taxi_share_detail_participate.text = String.format("내가 작성한 글입니다. (%d)", participantsNum)
                btn_taxi_share_detail_participate.setBackgroundResource(R.drawable.background_already_participate_color)
                btn_taxi_share_detail_participate.textColor = R.color.light_gray
                btn_taxi_share_detail_participate.isEnabled = false
            } else if (isParticipated) {
                btn_taxi_share_detail_participate.text = String.format("이미 참여중인 글입니다. (%d)", participantsNum)
                btn_taxi_share_detail_participate.setBackgroundResource(R.drawable.background_already_participate_color)
                btn_taxi_share_detail_participate.textColor = R.color.light_gray
            } else {
                btn_taxi_share_detail_participate.text = String.format("현재 참여 %d 명 (%d)", participantsNum, limit)
                btn_taxi_share_detail_participate.setBackgroundResource(R.drawable.background_not_participate_color)
                btn_taxi_share_detail_participate.textColor = R.color.common_black
            }
        }
    }

    private fun initView() {

        rcv_taxi_share_detail_comments.apply {
            adapter = this@TaxiShareInfoDetailActivity.adapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            setOnBottomDetection()
        }
    }

    private fun initPresenter() {
        presenter = TaxiShareInfoDetailPresenter(this, ServerRepositoryImpl.getInstance(ServerClient.getInstance())).apply {
            setOnBottomDetectSubscriber(rcv_taxi_share_detail_comments.observeBottomDetectionPublisher())
        }
    }

    @SuppressWarnings("all")
    private fun initListener() {

        btn_taxi_share_detail_participate.onClick {
            if (currentTaxiShareInfo.isParticipated) {
                AlertDialog.Builder(this@TaxiShareInfoDetailActivity)
                    .setTitle("택시 합승을 취소")
                    .setMessage("택시 합승을 취소하시겠습니까 ?")
                    .setPositiveButton("확인", ({ _, _ ->
                        presenter.leaveTaxiShare(currentTaxiShareInfo.id)
                    }))
                    .setNegativeButton("취소", null)
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

        rcv_taxi_share_detail_comments.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!recyclerView.canScrollVertically(1)) {
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
                Constant.USER_ID,
                et_taxi_share_detail_comment_input.text.toString()
            )
        }

        tb_taxi_share_detail.setNavigationOnClickListener {
            setReturnTaxiShareInfo()
        }

        if (Constant.CURRENT_USER.studentId == currentTaxiShareInfo.uid.toInt()) {
            btn_taxi_share_detail_delete.visibility = View.VISIBLE
            btn_taxi_share_detail_delete.onClick {
                // 삭제 작업 수행
                AlertDialog.Builder(this@TaxiShareInfoDetailActivity)
                    .setTitle("합승 글 삭제")
                    .setMessage("글을 삭제하시겠습니까 ?")
                    .setPositiveButton("확인", ({ _, _ ->
                        presenter.removeTaxiShare(currentTaxiShareInfo.id)
                    }))
                    .setNegativeButton("취소", null)
                    .setCancelable(false)
                    .show()
            }

            btn_taxi_share_detail_edit.visibility = View.VISIBLE
            btn_taxi_share_detail_edit.onClick {

                Intent(this@TaxiShareInfoDetailActivity, RegisterTaxiShareActivity::class.java)
                    .apply {
                        putExtra(Constant.MODIFY_TAXI_SHARE_STR, currentTaxiShareInfo)
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
            currentTaxiShareInfo = (data.getSerializableExtra(Constant.MODIFY_TAXI_SHARE_STR) as TaxiShareInfo)
            setViewItem()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        setReturnTaxiShareInfo()
    }

    private fun initAdapter() {
        adapter = TaxiShareInfoCommentListAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
