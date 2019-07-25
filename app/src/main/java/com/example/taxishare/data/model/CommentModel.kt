/*
 * Created by WonJongSeong on 2019-07-25
 */

package com.example.taxishare.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CommentModel(
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("uid")
    val uid: Int,
    @Expose
    @SerializedName("commentId")
    val commentId: Int,
    @Expose
    @SerializedName("commentDate")
    val commentDate: Long,
    @Expose
    @SerializedName("content")
    val content: String
) {
}