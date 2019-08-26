/*
 * Created by WonJongSeong on 2019-07-14
 */

package com.meongbyeol.taxishare.view.main.register.location.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.meongbyeol.taxishare.R
import com.meongbyeol.taxishare.data.model.Location
import com.meongbyeol.taxishare.data.model.MyLocation
import com.meongbyeol.taxishare.view.main.register.location.LocationSelectionListener
import kotlinx.android.synthetic.main.item_my_location.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class LocationSavedAdapter :
    ListAdapter<MyLocation, RecyclerView.ViewHolder>(MyLocation.DIFF_UTIL) {

    private val myLocationList: MutableList<MyLocation> = mutableListOf()
    private lateinit var onSelectionListener: LocationSelectionListener
    private lateinit var onRemoveSelectionListener: LocationRemoveSelectionListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder = when (viewType) {
        1 -> MyLocationHistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_my_location, parent, false
            )
        )
        else -> NoMyLocationHistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_no_search_history, parent, false
            )
        )
    }


    override fun getItemViewType(position: Int): Int = when (myLocationList.size > 0) {
        true -> 1
        else -> 2
    }

    override fun getItemCount(): Int = when (myLocationList.size == 0) {
        true -> 1
        else -> myLocationList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(holder.itemViewType == 1) {
            (holder as MyLocationHistoryViewHolder).bindView(position)
            holder.itemView.onClick {
                if (::onSelectionListener.isInitialized)
                    with(myLocationList[position]) {
                        onSelectionListener.locationSelected(
                            Location(latitude, longitude, locationName, roadAddress, jibunAddress)
                        )
                    }
            }

            holder.itemView.btn_save_item_select.onClick {
                if (::onRemoveSelectionListener.isInitialized) {
                    with(myLocationList[position]) {
                        onRemoveSelectionListener.onRemoveItemSelect(saveName)
                    }
                }
            }
        }
    }

    inner class MyLocationHistoryViewHolder(private val viewHolder: View) : RecyclerView.ViewHolder(viewHolder) {

        fun bindView(position: Int) {
            with(myLocationList[position]) {
                viewHolder.tv_save_item_name.text = saveName
                viewHolder.tv_save_item_location_name.text = locationName
                viewHolder.tv_save_item_jibun_address.text = jibunAddress
                viewHolder.tv_save_item_road_address.text = roadAddress
            }
        }
    }

    inner class NoMyLocationHistoryViewHolder(viewHolder: View) : RecyclerView.ViewHolder(viewHolder)

    fun setOnSelectionListener(onSelectionListener: LocationSelectionListener) {
        this@LocationSavedAdapter.onSelectionListener = onSelectionListener
    }

    fun setOnLocationRemoveListener(onRemoveSelectionListener: LocationRemoveSelectionListener) {
        this@LocationSavedAdapter.onRemoveSelectionListener = onRemoveSelectionListener
    }

    fun setMyLocationList(myLocationList: MutableList<MyLocation>) {
        this.myLocationList.clear()
        this.myLocationList.addAll(myLocationList)
        submitList(myLocationList)
    }
}