/*
 * Created by WonJongSeong on 2019-08-02
 */

package com.example.taxishare.data.remote.apis.server.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LeaveTaxiShareResponse(
    @Expose
    @SerializedName("responseCode")
    val responseCode: Int
) {

}