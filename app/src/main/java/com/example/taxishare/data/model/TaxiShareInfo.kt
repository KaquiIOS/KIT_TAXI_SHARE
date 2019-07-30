/*
 * Created by WonJongSeong on 2019-07-16
 */

package com.example.taxishare.data.model

import androidx.recyclerview.widget.DiffUtil
import java.io.Serializable
import java.util.*

data class TaxiShareInfo(
    val id: String,
    val uid: String,
    val title: String,
    val startDate: Date,
    val startLocation: Location,
    val endLocation: Location,
    val limit: Int,
    val nickname: String,
    val major: String,
    val participantsNum: Int,
    val isParticipated : Boolean
) : Serializable {
    companion object {
        val DIFF_UTIL: DiffUtil.ItemCallback<TaxiShareInfo> = object : DiffUtil.ItemCallback<TaxiShareInfo>() {
            override fun areItemsTheSame(oldItem: TaxiShareInfo, newItem: TaxiShareInfo): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: TaxiShareInfo, newItem: TaxiShareInfo): Boolean =
                oldItem == newItem
        }
    }
}