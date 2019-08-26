/*
 * Created by WonJongSeong on 2019-07-30
 */

package com.meongbyeol.taxishare.data.remote.apis.server.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ParticipateTaxiShareResponse(
    @Expose
    @SerializedName("responseCode")
    val responseCode : Int
) {

}