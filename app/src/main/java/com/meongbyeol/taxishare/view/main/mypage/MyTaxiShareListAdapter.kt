package com.meongbyeol.taxishare.view.main.mypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.meongbyeol.taxishare.R
import com.meongbyeol.taxishare.data.mapper.TypeMapper
import com.meongbyeol.taxishare.data.model.MyTaxiShareItem
import kotlinx.android.synthetic.main.item_my_taxishare.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.*
import kotlin.collections.ArrayList

class MyTaxiShareListAdapter : ListAdapter<MyTaxiShareItem, RecyclerView.ViewHolder>(MyTaxiShareItem.DIFF_UTIL) {

    private val myTaxiShareList: MutableList<MyTaxiShareItem> = mutableListOf()

    private lateinit var onClickListener: MyTaxiShareItemClickListener

    private var isEmptyList : Boolean = true

    override fun getItemCount(): Int = myTaxiShareList.size

    override fun getItemViewType(position: Int): Int = when (isEmptyList) {
        false -> 1
        else -> 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        1 -> MyTaxiShareItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_my_taxishare, parent, false
            )
        )
        else -> NoMyTaxiShareViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_no_my_list, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 1) {
            (holder as MyTaxiShareItemViewHolder).bind(myTaxiShareList[holder.adapterPosition])

            if (::onClickListener.isInitialized) {
                holder.itemView.onClick {
                    onClickListener.onClick(
                        myTaxiShareList[holder.adapterPosition].id,
                        myTaxiShareList[holder.adapterPosition].uid
                    )
                }
            }
        }
    }

    fun setMyTaxiShareList(taxiShareList: MutableList<MyTaxiShareItem>) {
        myTaxiShareList.clear()
        myTaxiShareList.addAll(taxiShareList)

        isEmptyList = false

        if(myTaxiShareList.isEmpty()) {
            isEmptyList = true
            myTaxiShareList.add(MyTaxiShareItem())
            taxiShareList.add(MyTaxiShareItem())
        }

        submitList(ArrayList(myTaxiShareList))
    }

    fun setOnMyTaxiShareClickListener(onClickListener: MyTaxiShareItemClickListener) {
        this@MyTaxiShareListAdapter.onClickListener = onClickListener
    }

    fun removeMyItem(id : String) {

        val iter = myTaxiShareList.iterator()

        while(iter.hasNext()) {
            if(iter.next().id == id) {
                iter.remove()
                break
            }
        }

        submitList(ArrayList(myTaxiShareList))
    }


    inner class MyTaxiShareItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(myTaxiShareItem: MyTaxiShareItem) {
            with(view) {
                tv_my_item_party_num.text = myTaxiShareItem.partyNum.toString()
                tv_my_item_end_location.text = myTaxiShareItem.endLocation.locationName
                tv_my_item_start_location.text = myTaxiShareItem.startLocation.locationName
                tv_my_item_start_time.text = TypeMapper.dateToString(Date(myTaxiShareItem.startDate))
            }
        }
    }

    inner class NoMyTaxiShareViewHolder(view: View) : RecyclerView.ViewHolder(view)

}