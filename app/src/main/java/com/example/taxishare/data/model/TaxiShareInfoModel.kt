/*
 * Created by WonJongSeong on 2019-07-19
 */

package com.example.taxishare.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TaxiShareInfoModel(
    @Expose
    @SerializedName("id")
    val id: String,
    @Expose
    @SerializedName("uid")
    val uid: String,
    @Expose
    @SerializedName("title")
    val title: String,
    @Expose
    @SerializedName("startDate")
    val startDate: Long,
    @Expose
    @SerializedName("startLocation")
    val startLocation: Location,
    @Expose
    @SerializedName("endLocation")
    val endLocation: Location,
    @Expose
    @SerializedName("maxNum")
    val limit: Int,
    @Expose
    @SerializedName("nickname")
    val nickname: String,
    @Expose
    @SerializedName("major")
    val major: String,
    @Expose
    @SerializedName("participantsNum")
    val participantsNum: Int
)