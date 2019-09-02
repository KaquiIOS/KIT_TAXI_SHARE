/*
 * Created by WonJongSeong on 2019-07-14
 */

package com.meongbyeol.taxishare.data.model

import androidx.recyclerview.widget.DiffUtil

data class MyLocation(
    val saveName: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val locationName: String = "",
    val roadAddress: String = "",
    val jibunAddress: String = ""
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