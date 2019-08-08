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
import org.jetbrains.anko.sdk27.coroutines.onClick
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

    override fun showLoadTaxiShareListNotFinishedMessage() {
        toast(getString(R.string.taxi_share_list_load_not_finish))
    }

    override fun showLoadTaxiShareListFailMessage() {
        toast(getString(R.string.taxi_share_list_load_fail))
    }

    override fun showLastPageOfTaxiShareListMessage() {
        toast(getString(R.string.taxi_share_list_last_page))
    }

    override fun showParticipateTaxiShareSuccess(postId: String) {
        taxiShareListAdapter.changeTaxiShareParticipateInfo(postId, true)
        toast(getString(R.string.participate_taxi_share_success))
    }

    override fun showParticipateTaxiShareFail() {
        toast(getString(R.string.participate_taxi_share_fail))
    }

    override fun showParticipateTaxiShareNotFinish() {
        toast(getString(R.string.participate_taxi_share_waiting))
    }

    override fun showRemoveTaxiShareSuccess(postId: Int) {
        taxiShareListAdapter.removeTaxiShare(postId.toString())
        toast(getString(R.string.remove_taxi_share_success))
    }

    override fun showRemoveTaxiShareFail() {
        toast(getString(R.string.remove_taxi_share_fail))
    }

    override fun showRemoveTaxiShareNotFinish() {
        toast(getString(R.string.remove_taxi_share_waiting))
    }

    override fun showLeaveTaxiShareSuccess(postId: Int) {
        taxiShareListAdapter.changeTaxiShareParticipateInfo(postId.toString(), false)
        toast(getString(R.string.leave_taxi_share_success))
    }

    override fun showLeaveTaxiShareFail() {
        toast(getString(R.string.leave_taxi_share_fail))
    }

    override fun showLeaveTaxiShareNotFinish() {
        toast(getString(R.string.leave_taxi_share_waiting))
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
                taxiShareListAdapter.removeTaxiShare(data.getStringExtra(Constant.POST_ID))
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

        btn_taxi_list_reload.onClick {
            presenter.loadTaxiShareInfoList(true)
        }

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
                    .setTitle(getString(R.string.taxi_share_remove_title))
                    .setMessage(getString(R.string.taxi_share_remove_content))
                    .setPositiveButton(getString(R.string.ok), ({ _, _ ->
                        presenter.removeTaxiShareInfo(postId)
                    }))
                    .setNegativeButton(getString(R.string.cancel), null)
                    .setCancelable(false)
                    .show()
            }
        })
        taxiShareListAdapter.setTaxiShareParticipantsClickListener(object : TaxiShareParticipantBtnClickListener {
            override fun onParticipantsButtonClicked(postId: String, isParticipating: Boolean) {
                if (isParticipating) {
                    AlertDialog.Builder(context)
                        .setTitle(getString(R.string.taxi_share_leave_title))
                        .setMessage(getString(R.string.taxi_share_leave_content))
                        .setPositiveButton(getString(R.string.ok), ({ _, _ ->
                            presenter.leaveTaxiShare(postId)
                        }))
                        .setNegativeButton(getString(R.string.cancel), null)
                        .setCancelable(false)
                        .show()
                } else {
                    presenter.participateTaxiShare(postId, Date())
                }
            }
        })
    }
}
