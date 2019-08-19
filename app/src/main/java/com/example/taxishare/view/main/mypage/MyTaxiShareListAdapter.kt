package com.example.taxishare.view.main.mypage

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taxishare.data.model.Location
import com.example.taxishare.data.model.MyTaxiShareItem

class MyTaxiShareListAdapter : ListAdapter<MyTaxiShareItem, RecyclerView.ViewHolder>(MyTaxiShareItem.DIFF_UTIL){

    private val myTaxiShareList : MutableList<MyTaxiShareItem> = mutableListOf()

    private lateinit var onClickListener: MyTaxiShareItemClickListener


    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun setMyTaxiShareList(taxiShareList : MutableList<MyTaxiShareItem>) {

    }

    fun onMyTaxiShareClickListener(onClickListener : MyTaxiShareItemClickListener) {
        this@MyTaxiShareListAdapter.onClickListener = onClickListener
    }
}