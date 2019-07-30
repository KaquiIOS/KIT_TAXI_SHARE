package com.example.taxishare.view.main.taxisharelist

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
import com.example.taxishare.data.model.TaxiShareInfo
import com.example.taxishare.data.remote.apis.server.ServerClient
import com.example.taxishare.data.repo.ServerRepositoryImpl
import com.example.taxishare.view.main.taxisharelist.detail.TaxiShareInfoDetailActivity
import kotlinx.android.synthetic.main.fragment_taxi_share_list.*
import org.jetbrains.anko.support.v4.startActivity
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPresenter()
        initAdapter()
        initView()
        initListener()

        presenter.loadTaxiShareInfoList(true)
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
        taxiShareListAdapter.setTaxiShareInfoItemClickListener(object : TaxiShareInfoItemClickListener{
            override fun onTaxiShareInfoItemClicked(selectedTaxiShareInfo: TaxiShareInfo) {
                startActivity<TaxiShareInfoDetailActivity>(
                    getString(R.string.taxi_share_detail_info) to selectedTaxiShareInfo
                )
                // 상세 화면 보여주기
            }
        })
        taxiShareListAdapter.setTaxiShareInfoModifyClickListener(object : TaxiShareInfoModifyClickListener{
            override fun onTaxiShareInfoModifyClicked(selectedTaxiShareInfo: TaxiShareInfo, pos : Int) {
                // 등록 화면 보여주기
            }
        })
        taxiShareListAdapter.setTaxiShareInfoRemoveClickListener(object : TaxiShareInfoRemoveClickListener {
            override fun onTaxiShareInfoRemoveClicked(selectedTaxiShareInfo: TaxiShareInfo, pos : Int) {
                // 다이얼로그를 띄워주고 확인/취소에 따라 삭제
                // -> 몇번 데이터인지 확인
            }
        })
        taxiShareListAdapter.setTaxiShareParticipantsClickListener(object : TaxiShareParticipantBtnClickListener{
            override fun onParticipantsButtonClicked(postId: Int) {

            }
        })
    }
}
