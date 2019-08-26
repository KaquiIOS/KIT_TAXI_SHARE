/*
 * Created by WonJongSeong on 2019-08-06
 */

package com.meongbyeol.taxishare.data.model

import java.io.Serializable
import java.util.*

data class TaxiShareDetailInfo(
    val id: String,
    val uid: String,
    val title: String,
    val startDate: Date,
    val startLocation: Location,
    val endLocation: Location,
    val limit: Int,
    val nickname: String,
    val major: String,
    var participantsNum: Int,
    var isParticipated : Boolean,
    var participants : MutableList<String>
) : Serializable