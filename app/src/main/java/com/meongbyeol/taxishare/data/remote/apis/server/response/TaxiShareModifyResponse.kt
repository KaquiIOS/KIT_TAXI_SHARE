/*
 * Created by WonJongSeong on 2019-08-01
 */

package com.meongbyeol.taxishare.data.remote.apis.server.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TaxiShareModifyResponse(
    @Expose
    @SerializedName("responseCode")
    val responseCode: Int
) {
}