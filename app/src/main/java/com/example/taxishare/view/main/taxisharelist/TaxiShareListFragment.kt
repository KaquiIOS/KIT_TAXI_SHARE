package com.example.taxishare.view.main.taxisharelist

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taxishare.R
import com.example.taxishare.app.Constant
import com.example.taxishare.data.model.TaxiShareInfo
import com.example.taxishare.data.remote.apis.server.ServerClient
import com.example.taxishare.data.repo.ServerRepositoryImpl
import com.example.taxishare.view.main.register.RegisterTaxiShareActivity
import com.example.taxishare.view.main.taxisharelist.detail.TaxiShareInfoDetailActivity
import com.example.taxishare.view.main.taxisharelist.detail.TestInterface
import kotlinx.android.synthetic.main.fragment_taxi_share_list.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast


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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_taxi_share_list, container, false)

    override fun setTaxiShareList(taxiShareList: MutableList<TaxiShareInfo>) {
        taxiShareListAdapter.setTaxiShareInfoList(taxiShareList)
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
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.MODIFY_TAXI_SHARE && data != null) {
            taxiShareListAdapter.updateTaxiShareInfo(
                data.getSerializableExtra(Constant.MODIFY_TAXI_ASHARE_STR) as TaxiShareInfo
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    fun addTaxiShareInfo(taxiShareInfo: TaxiShareInfo) {
        taxiShareListAdapter.addTaxiShareInfo(taxiShareInfo, isVisible)
    }

    private fun initPresenter() {
        presenter = TaxiShareListPresenter(this, ServerRepositoryImpl.getInstance(ServerClient.getInstance()))
    }

    private fun initAdapter() {
        taxiShareListAdapter = TaxiShareListAdapter()
    }

    private fun initView() {
        with(rcv_taxi_list) {
            adapter = taxiShareListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
    }

    private fun initListener() {
        taxiShareListAdapter.setTaxiShareInfoItemClickListener(object : TaxiShareInfoItemClickListener {
            override fun onTaxiShareInfoItemClicked(selectedTaxiShareInfo: TaxiShareInfo) {
                startActivity<TaxiShareInfoDetailActivity>(
                    getString(R.string.taxi_share_detail_info) to selectedTaxiShareInfo
                )
                // 상세 화면 보여주기
            }
        })
        taxiShareListAdapter.setTaxiShareInfoModifyClickListener(object : TaxiShareInfoModifyClickListener {
            override fun onTaxiShareInfoModifyClicked(selectedTaxiShareInfo: TaxiShareInfo, pos: Int) {
                this@TaxiShareListFragment.startActivityForResult<RegisterTaxiShareActivity>(
                    Constant.MODIFY_TAXI_SHARE,
                    getString(R.string.taxi_share_detail_info) to selectedTaxiShareInfo
                )
            }
        })
        taxiShareListAdapter.setTaxiShareInfoRemoveClickListener(object : TaxiShareInfoRemoveClickListener {
            override fun onTaxiShareInfoRemoveClicked(postId: String) {
                presenter.removeTaxiShareInfo(postId)
            }
        })
        taxiShareListAdapter.setTaxiShareParticipantsClickListener(object : TaxiShareParticipantBtnClickListener {
            override fun onParticipantsButtonClicked(postId: String, isParticipating : Boolean) {
                if(isParticipating) {
                    AlertDialog.Builder(context)
                        .setTitle("택시 합승을 취소")
                        .setMessage("택시 합승을 취소하시겠습니까 ?")
                        .setPositiveButton("확인", ({ _, _ ->
                            presenter.leaveTaxiShare(postId)
                        }))
                        .setNegativeButton("취소",  null)
                        .setCancelable(false)
                        .show()


                } else {
                    presenter.participateTaxiShare(postId)
                }
            }
        })
    }
}
