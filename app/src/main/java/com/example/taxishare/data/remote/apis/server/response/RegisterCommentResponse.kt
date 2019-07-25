/*
 * Created by WonJongSeong on 2019-07-25
 */

package com.example.taxishare.data.remote.apis.server.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterCommentResponse(
    @Expose
    @SerializedName("responseCode")
    val responseCode: Int
) {
}
