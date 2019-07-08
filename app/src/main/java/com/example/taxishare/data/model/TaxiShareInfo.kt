/*
 * Created by WonJongSeong on 2019-07-04
 */

package com.example.taxishare.data.model

import androidx.recyclerview.widget.DiffUtil

data class TaxiShareInfo(
    val id : String,
    val title: String, val limit: Int,
    val description: String, val startLocation: Location,
    val endLocation: Location
) {
    companion object {
        val DIFF_UTIL: DiffUtil.ItemCallback<TaxiShareInfo> = object : DiffUtil.ItemCallback<TaxiShareInfo>() {
            override fun areItemsTheSame(oldItem: TaxiShareInfo, newItem: TaxiShareInfo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TaxiShareInfo, newItem: TaxiShareInfo): Boolean =
                oldItem.id == newItem.id
        }
    }
}