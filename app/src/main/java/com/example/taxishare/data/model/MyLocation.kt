/*
 * Created by WonJongSeong on 2019-07-14
 */

package com.example.taxishare.data.model

import androidx.recyclerview.widget.DiffUtil

data class MyLocation(
    val saveName: String,
    val latitude: Double,
    val longitude: Double,
    val locationName: String,
    val roadAddress: String,
    val jibunAddress: String
) {
    companion object {
        val DIFF_UTIL: DiffUtil.ItemCallback<MyLocation> = object : DiffUtil.ItemCallback<MyLocation>() {
            override fun areItemsTheSame(oldItem: MyLocation, newItem: MyLocation): Boolean =
                (oldItem.saveName == newItem.saveName)

            override fun areContentsTheSame(oldItem: MyLocation, newItem: MyLocation): Boolean =
                (oldItem.latitude == newItem.latitude)
        }
    }
}