/*
 * Created by WonJongSeong on 2019-07-31
 */

package com.meongbyeol.taxishare.data.remote.apis.server.request

import java.util.*

data class RemoveCommentRequest(val commentId : Int) : ServerRequest.PostRequest {

    companion object {
        private const val COMMENT_ID  = "commentId"
    }

    override fun getRequest(): Map<String, String> {
        val params: MutableMap<String, String> = HashMap()

        params[COMMENT_ID] = commentId.toString()

        return params
    }
}