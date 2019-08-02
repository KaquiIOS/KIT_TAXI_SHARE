/*
 * Created by WonJongSeong on 2019-08-02
 */

package com.example.taxishare.data.remote.apis.server.request

import com.example.taxishare.app.Constant
import java.util.HashMap

data class LeaveTaxiShareRequest (
    private val postId : String
) : ServerRequest.PostRequest {

    companion object {
        private const val UID  = "uid"
        private const val POST_ID = "postId"
    }

    override fun getRequest(): Map<String, String> {
        val params: MutableMap<String, String> = HashMap()

        params[UID] = Constant.USER_ID
        params[POST_ID] = postId

        return params
    }
}