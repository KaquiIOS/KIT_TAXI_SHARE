/*
 * Created by WonJongSeong on 2019-07-31
 */

package com.meongbyeol.taxishare.data.remote.apis.server.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RemoveCommentResponse(
    @Expose
    @SerializedName("responseCode")
    val responseCode: Int
) {
}