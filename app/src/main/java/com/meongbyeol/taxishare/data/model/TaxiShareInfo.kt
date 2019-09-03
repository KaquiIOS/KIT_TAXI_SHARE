/*
 * Created by WonJongSeong on 2019-07-16
 */

package com.meongbyeol.taxishare.data.model

import androidx.recyclerview.widget.DiffUtil
import java.io.Serializable
import java.util.*

data class TaxiShareInfo(
    val id: String = "",
    val uid: String = "",
    var title: String = "",
    var startDate: Date = Date(),
    var startLocation: Location = Location(),
    var endLocation: Location = Location(),
    var limit: Int = 0,
    val nickname: String = "",
    val major: String = "",
    var participantsNum: Int = 0,
    var isParticipated: Boolean = false
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