package com.example.taxishare.view.main.taxisharelist.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taxishare.R
import com.example.taxishare.data.mapper.TypeMapper
import com.example.taxishare.data.model.TaxiShareInfo
import kotlinx.android.synthetic.main.activity_taxi_share_info_detail.*

class TaxiShareInfoDetailActivity : AppCompatActivity(), TaxiShareInfoDetailView {

    private lateinit var currentTaxiShareInfo: TaxiShareInfo
    private lateinit var presenter: TaxiShareInfoDetailPresenter
    private lateinit var adapter : TaxiShareInfoCommentListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taxi_share_info_detail)

        getDetailTaxiShareInfoFromIntent()

        initView()
        initPresenter()
        initAdapter()
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
        }

        rcv_taxi_share_detail_comments.apply {
            //adapter = this@TaxiShareInfoDetailActivity.adapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun initPresenter() {
        presenter = TaxiShareInfoDetailPresenter(this)
    }

    private fun initListener() {
        // RECYCLERVIEW 초기화
    }

    private fun initAdapter() {
        adapter = TaxiShareInfoCommentListAdapter()
    }
}
