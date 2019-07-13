/*
 * Created by WonJongSeong on 2019-07-05
 */

package com.example.taxishare.view.main.register.location.search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taxishare.R
import com.example.taxishare.data.model.Location
import com.example.taxishare.view.main.register.location.LocationSelectionListener
import kotlinx.android.synthetic.main.item_search_location.view.*

class LocationSearchAdapter :
    ListAdapter<Location, RecyclerView.ViewHolder>(Location.DIFF_UTIL) {

    private val locationSearchResultList: MutableList<Location> = mutableListOf()
    private lateinit var locationSelectionListener: LocationSelectionListener

    private var flag : Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when(viewType) {
            1 -> LocationSearchViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_search_location,
                    parent,
                    false
                )
            )
            else -> LocationNotFoundViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_no_search_place,
                    parent,
                    false
                )
            )
        }


    override fun getItemViewType(position: Int): Int =
        when (flag) {
            true -> 2
            else -> 1
        }


    override fun getItemCount(): Int = locationSearchResultList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.itemViewType == 1) {
            (holder as LocationSearchViewHolder).let {
                it.itemView.setOnClickListener {
                    if (::locationSelectionListener.isInitialized)
                        locationSelectionListener.locationSelected(locationSearchResultList[position])
                }
                it.bindView(position)
            }
        } else {
            Log.d("Test", "NotFoundViewHolder")
        }
    }

    fun setSearchResultList(resultList: MutableList<Location>) {
        locationSearchResultList.clear()

        flag = resultList.isEmpty()

        if(flag) {
            locationSearchResultList.add(Location(0.0,0.0,"","",""))
        }

        locationSearchResultList.addAll(resultList)
        submitList(mutableListOf())
        submitList(locationSearchResultList)
    }

    fun setLocationSelectionListener(locationSelectionListener: LocationSelectionListener) {
        this@LocationSearchAdapter.locationSelectionListener = locationSelectionListener
    }

    inner class LocationSearchViewHolder(private val viewHolder: View) : RecyclerView.ViewHolder(viewHolder) {

        fun bindView(position: Int) {

            locationSearchResultList[position].apply {
                viewHolder.tv_search_item_name.text = this.locationName
                viewHolder.tv_search_item_road_address.text = this.roadAddress
                viewHolder.tv_search_item_jibun_address.text = this.jibunAddress
            }
        }
    }

    inner class LocationNotFoundViewHolder(viewHolder : View)  : RecyclerView.ViewHolder(viewHolder)
}