/*
 * Created by WonJongSeong on 2019-07-15
 */

package com.meongbyeol.taxishare.data.remote.apis.server.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TaxiShareRegisterResponse(
    @Expose
    @SerializedName("responseCode")
    val responseCode: Int,
    @Expose
    @SerializedName("id")
    val id: String
) {
}