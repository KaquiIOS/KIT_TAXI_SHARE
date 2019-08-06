/*
 * Created by WonJongSeong on 2019-08-02
 */

package com.example.taxishare.data.remote.apis.server.response

import com.example.taxishare.data.model.Location
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DetailTaxiShareLoadResponse(
    @Expose
    @SerializedName("responseCode")
    val responseCode: Int,
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
    @SerializedName("isParticipate")
    val isParticipate: Int,
    @Expose
    @SerializedName("participants")
    val participants: MutableList<String>
) {
}