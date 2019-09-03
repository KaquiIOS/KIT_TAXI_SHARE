/*
 * Created by WonJongSeong on 2019-07-25
 */

package com.meongbyeol.taxishare.data.model

import androidx.recyclerview.widget.DiffUtil
import java.io.Serializable

data class Comment(
    val uid: String = "",
    val commentId: Int = -1,
    val commentDate: String = "",
    val content: String = "",
    val nickname : String = ""
) : Serializable {
    companion object {
        val DIFF_UTIL: DiffUtil.ItemCallback<Comment> = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean =
                oldItem == newItem
        }
    }
}