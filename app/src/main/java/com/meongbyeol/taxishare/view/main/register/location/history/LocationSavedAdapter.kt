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

    private var isEmptyList : Boolean = false

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

    override fun getItemViewType(position: Int): Int = when (isEmptyList) {
        false -> 1
        else -> 2
    }

    override fun getItemCount(): Int = myLocationList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(holder.itemViewType == 1) {
            (holder as MyLocationHistoryViewHolder).bindView(holder.adapterPosition)
            holder.itemView.onClick {
                if (::onSelectionListener.isInitialized)
                    with(myLocationList[holder.adapterPosition]) {
                        onSelectionListener.locationSelected(
                            Location(latitude, longitude, locationName, roadAddress, jibunAddress)
                        )
                    }
            }

            holder.itemView.btn_save_item_select.onClick {
                if (::onRemoveSelectionListener.isInitialized) {
                    with(myLocationList[holder.adapterPosition]) {
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

    fun setMyLocationList(locationList: MutableList<MyLocation>) {
        myLocationList.clear()
        myLocationList.addAll(locationList)

        isEmptyList = false

        if(locationList.isEmpty()) {
            isEmptyList = true
            myLocationList.add(MyLocation())
            locationList.add(MyLocation())
        }

        submitList(locationList)
    }
}