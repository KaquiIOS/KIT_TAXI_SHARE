/*
 * Created by WonJongSeong on 2019-07-25
 */

package com.meongbyeol.taxishare.data.remote.apis.server.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterCommentResponse(
    @Expose
    @SerializedName("responseCode")
    val responseCode: Int,
    @Expose
    @SerializedName("postId")
    val id: Int,
    @Expose
    @SerializedName("stdId")
    val uid: Int,
    @Expose
    @SerializedName("commentId")
    val commentId: Int,
    @Expose
    @SerializedName("commentDate")
    val commentDate: Long,
    @Expose
    @SerializedName("commentContent")
    val content: String,
    @Expose
    @SerializedName("nickname")
    val nickname : String
) {
}
