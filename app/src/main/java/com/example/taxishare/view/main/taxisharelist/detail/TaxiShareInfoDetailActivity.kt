package com.example.taxishare.view.main.taxisharelist.detail

import android.os.Bundle
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
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.activity_taxi_share_info_detail.*
import org.jetbrains.anko.sdk27.coroutines.onClick
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

        presenter.loadComments(currentTaxiShareInfo.id)
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

    private fun getDetailTaxiShareInfoFromIntent() {
        currentTaxiShareInfo = intent.getSerializableExtra(getString(R.string.taxi_share_detail_info)) as TaxiShareInfo
    }


    private fun initView() {

        with(currentTaxiShareInfo) {
            tv_taxi_share_detail_leader.text = String.format("%s (%s)", nickname, major)
            tv_taxi_share_detail_start_time.text = TypeMapper.dateToString(startDate)
            tv_taxi_share_detail_start_location.text = startLocation.locationName
            tv_taxi_share_detail_end_location.text = endLocation.locationName
            tv_taxi_share_detail_title.text = title

            if (Constant.USER_ID == uid) {
                btn_taxi_share_detail_participate.text = "내가 작성한 글입니다."
                btn_taxi_share_detail_participate.isEnabled = false
            } else if (isParticipated) {
                btn_taxi_share_detail_participate.text = "이미 참여중인 글입니다."
                btn_taxi_share_detail_participate.isEnabled = false
            } else {
                btn_taxi_share_detail_participate.text = String.format("현재 참여 %d 명 (%d)", participantsNum, limit)
            }
        }

        rcv_taxi_share_detail_comments.apply {
            adapter = this@TaxiShareInfoDetailActivity.adapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
    }

    private fun initPresenter() {
        presenter = TaxiShareInfoDetailPresenter(this, ServerRepositoryImpl.getInstance(ServerClient.getInstance()))
    }

    @SuppressWarnings("all")
    private fun initListener() {
        // RECYCLERVIEW 초기화
        et_taxi_share_detail_comment_input.textChanges().skipInitialValue().subscribe({
            presenter.changeButtonState(it.toString())
        }, {
            it.printStackTrace()
        })

//        rcv_taxi_share_detail_comments.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                if(!recyclerView.canScrollHorizontally(1)) {
//                    presenter.loadComments(currentTaxiShareInfo.id)
//                }
//            }
//        })

        adapter.setOnBottomReachedListener(object : OnBottomReachedListener {
            override fun onBottomReached() {
                presenter.loadComments(currentTaxiShareInfo.id)
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


    }

    private fun initAdapter() {
        adapter = TaxiShareInfoCommentListAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
