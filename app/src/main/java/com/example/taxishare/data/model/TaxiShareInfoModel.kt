/*
 * Created by WonJongSeong on 2019-07-16
 */

package com.example.taxishare.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

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
    @SerializedName("numOfPassenger")
    val limit: Int,
    @Expose
    @SerializedName("content")
    val description: String,
    @Expose
    @SerializedName("startLocation")
    val startLocation: Location,
    @Expose
    @SerializedName("endLocation")
    val endLocation: Location,
    @Expose
    @SerializedName("registerDate")
    val registerDate: Date
) {
}