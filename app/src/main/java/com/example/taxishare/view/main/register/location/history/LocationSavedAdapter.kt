/*
 * Created by WonJongSeong on 2019-07-14
 */

package com.example.taxishare.view.main.register.location.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taxishare.R
import com.example.taxishare.data.model.Location
import com.example.taxishare.data.model.MyLocation
import com.example.taxishare.view.main.register.location.LocationSelectionListener
import kotlinx.android.synthetic.main.item_my_location.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class LocationSavedAdapter :
    ListAdapter<MyLocation, LocationSavedAdapter.MyLocationHistoryViewHolder>(MyLocation.DIFF_UTIL) {

    private val myLocationList: MutableList<MyLocation> = mutableListOf()
    private lateinit var onSelectionListener: LocationSelectionListener
    private lateinit var onRemoveSelectionListener: LocationRemoveSelectionListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyLocationHistoryViewHolder =
        MyLocationHistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_my_location, parent, false
            )
        )

    override fun onBindViewHolder(holder: MyLocationHistoryViewHolder, position: Int) {
        holder.bindView(position)
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

    fun setOnSelectionListener(onSelectionListener: LocationSelectionListener) {
        this@LocationSavedAdapter.onSelectionListener = onSelectionListener
    }

    fun setOnLocationRemoveListener(onRemoveSelectionListener: LocationRemoveSelectionListener) {
        this@LocationSavedAdapter.onRemoveSelectionListener = onRemoveSelectionListener
    }

    fun setMyLocationList(myLocationList : MutableList<MyLocation>) {
        this.myLocationList.clear()
        this.myLocationList.addAll(myLocationList)
        submitList(myLocationList)
    }
}