package com.example.taxishare.view.main.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taxishare.R
import com.example.taxishare.app.Constant
import com.example.taxishare.data.model.MyTaxiShareItem
import com.example.taxishare.data.model.TaxiShareInfo
import com.example.taxishare.data.remote.apis.server.ServerClient
import com.example.taxishare.data.repo.ServerRepositoryImpl
import com.example.taxishare.extension.observeBottomDetectionPublisher
import com.example.taxishare.view.main.taxisharelist.detail.TaxiShareInfoDetailActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_my_page.*
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast

class MyPageFragment : Fragment(), MyPageView {

    private lateinit var myListAdapter: MyTaxiShareListAdapter
    private lateinit var presenter : MyPagePresenter
    private lateinit var subscribe: Disposable

    companion object {
        fun newInstance(): MyPageFragment =
            MyPageFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPresenter()
        initAdapter()

        presenter.loadMyTaxiShareList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?   = inflater.inflate(R.layout.fragment_my_page, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Constant.LEAVE_TAXI_PARTY && data != null) {
            myListAdapter.removeMyItem((data.getSerializableExtra(Constant.TAXISHARE_DETAIL_STR) as TaxiShareInfo).id)
        } else if(resultCode == Constant.DATA_REMOVED && data != null) {
            myListAdapter.removeMyItem(data.getStringExtra(Constant.POST_ID))
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        subscribe.dispose()
        presenter.onDestroy()
    }

    override fun openDetailTaxiSharePage(taxiShareInfo : TaxiShareInfo) {
        this@MyPageFragment.startActivityForResult<TaxiShareInfoDetailActivity>(
            Constant.TAXISHARE_DETAIL,
            Constant.TAXISHARE_DETAIL_STR to taxiShareInfo
        )
    }

    override fun setMyList(myList: MutableList<MyTaxiShareItem>) {
        myListAdapter.setMyTaxiShareList(myList)
    }

    override fun loadMyListFail() {
        toast(R.string.fail_load_my_taxi_share_list)
    }

    private fun initPresenter() {
        presenter = MyPagePresenter(ServerRepositoryImpl.getInstance(ServerClient.getInstance()), this)
    }

    private fun initAdapter() {
        myListAdapter = MyTaxiShareListAdapter()
    }

    private fun initView() {
        with(rcv_my_taxi_share) {
            adapter = myListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            itemAnimator = null
        }
    }

    private fun initListener() {
        myListAdapter.setOnMyTaxiShareClickListener(object : MyTaxiShareItemClickListener {
            override fun onClick(id: String, uid: String) {
                presenter.openDetailTaxiShareInfoPage(id, uid)
            }
        })

        nsc_my_taxi_list.observeBottomDetectionPublisher().apply {
            subscribe = nsc_my_taxi_list.observeBottomDetectionPublisher().subscribe {
                //setDialogMessage(R.string.loading_taxi_list)
                //presenter.loadTaxiShareInfoList(false)
            }
        }
    }
}
