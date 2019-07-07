/*
 * Created by WonJongSeong on 2019-07-05
 */

package com.example.taxishare.view.main.register.location.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taxishare.R
import com.example.taxishare.data.model.Location
import com.example.taxishare.view.main.register.location.LocationSelectionListener
import kotlinx.android.synthetic.main.item_search_location.view.*

class LocationSearchAdapter : ListAdapter<Location, LocationSearchAdapter.LocationSearchViewHolder>(Location.DIFF_UTIL) {

    private val locationSearchResultList: MutableList<Location> = mutableListOf()
    private lateinit var locationSelectionListener : LocationSelectionListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationSearchViewHolder =
        LocationSearchViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_search_location,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = locationSearchResultList.size

    override fun onBindViewHolder(holder: LocationSearchViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            if(::locationSelectionListener.isInitialized)
                locationSelectionListener.locationSelected(locationSearchResultList[position])
        }

        holder.bindView(position)
    }

    fun setSearchResultList(resultList: MutableList<Location>) {
        locationSearchResultList.clear()
        locationSearchResultList.addAll(resultList)
        submitList(mutableListOf())
        submitList(resultList)
    }

    fun setLocationSelectionListener(locationSelectionListener: LocationSelectionListener) {
        this@LocationSearchAdapter.locationSelectionListener = locationSelectionListener
    }

    inner class LocationSearchViewHolder(private val viewHolder: View) : RecyclerView.ViewHolder(viewHolder) {

        fun bindView(position: Int) {

            val currentItem: Location = locationSearchResultList[position]

            viewHolder.tv_search_item_name.text = currentItem.locationName
            viewHolder.tv_search_item_road_address.text = currentItem.roadAddress
            viewHolder.tv_search_item_jibun_address.text = currentItem.jibunAddress
        }
    }
}