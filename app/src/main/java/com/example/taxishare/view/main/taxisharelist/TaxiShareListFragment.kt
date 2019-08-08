package com.example.taxishare.view.main.taxisharelist

import android.app.AlarmManager
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taxishare.R
import com.example.taxishare.app.AlarmManagerImpl
import com.example.taxishare.app.Constant
import com.example.taxishare.data.model.TaxiShareInfo
import com.example.taxishare.data.remote.apis.server.ServerClient
import com.example.taxishare.data.repo.ServerRepositoryImpl
import com.example.taxishare.extension.observeBottomDetectionPublisher
import com.example.taxishare.extension.setOnBottomDetection
import com.example.taxishare.view.main.register.RegisterTaxiShareActivity
import com.example.taxishare.view.main.taxisharelist.detail.TaxiShareInfoDetailActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_taxi_share_list.*
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast
import java.util.*


class TaxiShareListFragment : Fragment(), TaxiShareListView {

    companion object {
        fun newInstance() =
            TaxiShareListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private lateinit var presenter: TaxiShareListPresenter
    private lateinit var taxiShareListAdapter: TaxiShareListAdapter
    private lateinit var subscribe: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_taxi_share_list, container, false)

    override fun setTaxiShareList(taxiShareList: MutableList<TaxiShareInfo>, isRefresh: Boolean) {
        taxiShareListAdapter.setTaxiShareInfoList(taxiShareList, isRefresh)
        rcv_taxi_list.scheduleLayoutAnimation()
    }

    override fun insertTaxiShareInfo(taxiShareInfo: TaxiShareInfo) {
        Log.d("Test", "InsertTaxiShareInfo")
    }

    override fun removeTaxiShareInfo(pos: Int) {
        Log.d("Test", "removeTaxiShareInfo")
    }

    override fun modifyTaxiShareInfo(pos: Int) {
        Log.d("Test", "modifyTaxiShareInfo")
    }

    override fun showLoadTaxiShareListNotFinishedMessage() {
        toast("로드 안끝남")
    }

    override fun showLoadTaxiShareListFailMessage() {
        toast("로드 실패")
    }

    override fun showLastPageOfTaxiShareListMessage() {
        toast("마지막 페이지입니다")
    }

    override fun showParticipateTaxiShareSuccess(postId: String) {
        toast("택시 합승에 참여하였습니다")
        taxiShareListAdapter.changeTaxiShareParticipateInfo(postId, true)
    }

    override fun showParticipateTaxiShareFail() {
        toast("택시 합승에 실패하였습니다")
    }

    override fun showParticipateTaxiShareNotFinish() {
        toast("택시 합승을 요청중입니다")
    }

    override fun showRemoveTaxiShareSuccess(postId: Int) {
        taxiShareListAdapter.removeTaxiShare(postId.toString())
        toast("택시 합승 글이 삭제되었습니다")
    }

    override fun showRemoveTaxiShareFail() {
        toast("택시 합승 글 삭제가 되지 않았습니다")
    }

    override fun showRemoveTaxiShareNotFinish() {
        toast("택시 합승 글 삭제를 요청중입니다")
    }

    override fun showLeaveTaxiShareSuccess(postId: Int) {
        taxiShareListAdapter.changeTaxiShareParticipateInfo(postId.toString(), false)
        toast("택시 합승을 취소했습니다")
    }

    override fun showLeaveTaxiShareFail() {
        toast("택시 합승 취소를 실패했습니다")
    }

    override fun showLeaveTaxiShareNotFinish() {
        toast("택시 합승 취소를 요청중입니다")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPresenter()
        initAdapter()
        initView()
        initListener()

        presenter.loadTaxiShareInfoList(true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            if (resultCode == Constant.DATA_REMOVED) {
                taxiShareListAdapter.removeTaxiShare(data.getStringExtra("postId"))
            } else if (requestCode == Constant.MODIFY_TAXI_SHARE) {
                taxiShareListAdapter.updateTaxiShareInfo(
                    data.getSerializableExtra(Constant.MODIFY_TAXI_SHARE_STR) as TaxiShareInfo
                )
            } else if (requestCode == Constant.TAXISHARE_DETAIL) {
                taxiShareListAdapter.updateTaxiShareInfo(
                    data.getSerializableExtra(Constant.TAXISHARE_DETAIL_STR) as TaxiShareInfo
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
        subscribe.dispose()
    }

    fun addTaxiShareInfo(taxiShareInfo: TaxiShareInfo) {
        taxiShareListAdapter.addTaxiShareInfo(taxiShareInfo, isVisible)
    }

    private fun initPresenter() {
        presenter = TaxiShareListPresenter(
            this, ServerRepositoryImpl.getInstance(ServerClient.getInstance()),
            AlarmManagerImpl(context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager, context!!)
        )
    }

    private fun initAdapter() {
        taxiShareListAdapter = TaxiShareListAdapter()
    }

    private fun initView() {
        with(rcv_taxi_list) {
            adapter = taxiShareListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        }
    }

    private fun initListener() {

        nsc_taxi_list.setOnBottomDetection().apply {
            subscribe = nsc_taxi_list.observeBottomDetectionPublisher().subscribe {
                presenter.loadTaxiShareInfoList(false)
            }
        }

        taxiShareListAdapter.setTaxiShareInfoItemClickListener(object : TaxiShareInfoItemClickListener {
            override fun onTaxiShareInfoItemClicked(selectedTaxiShareInfo: TaxiShareInfo) {
                this@TaxiShareListFragment.startActivityForResult<TaxiShareInfoDetailActivity>(
                    Constant.TAXISHARE_DETAIL,
                    Constant.TAXISHARE_DETAIL_STR to selectedTaxiShareInfo
                )
            }
        })
        taxiShareListAdapter.setTaxiShareInfoModifyClickListener(object : TaxiShareInfoModifyClickListener {
            override fun onTaxiShareInfoModifyClicked(selectedTaxiShareInfo: TaxiShareInfo, pos: Int) {
                this@TaxiShareListFragment.startActivityForResult<RegisterTaxiShareActivity>(
                    Constant.MODIFY_TAXI_SHARE,
                    Constant.MODIFY_TAXI_SHARE_STR to selectedTaxiShareInfo
                )
            }
        })
        taxiShareListAdapter.setTaxiShareInfoRemoveClickListener(object : TaxiShareInfoRemoveClickListener {
            override fun onTaxiShareInfoRemoveClicked(postId: String) {
                AlertDialog.Builder(context)
                    .setTitle("합승 글 삭제")
                    .setMessage("글을 삭제하시겠습니까 ?")
                    .setPositiveButton("확인", ({ _, _ ->
                        presenter.removeTaxiShareInfo(postId)
                    }))
                    .setNegativeButton("취소", null)
                    .setCancelable(false)
                    .show()
            }
        })
        taxiShareListAdapter.setTaxiShareParticipantsClickListener(object : TaxiShareParticipantBtnClickListener {
            override fun onParticipantsButtonClicked(postId: String, isParticipating: Boolean) {
                if (isParticipating) {
                    AlertDialog.Builder(context)
                        .setTitle("택시 합승을 취소")
                        .setMessage("택시 합승을 취소하시겠습니까 ?")
                        .setPositiveButton("확인", ({ _, _ ->
                            presenter.leaveTaxiShare(postId)
                        }))
                        .setNegativeButton("취소", null)
                        .setCancelable(false)
                        .show()
                } else {
                    presenter.participateTaxiShare(postId, Date())
                }
            }
        })
    }
}
