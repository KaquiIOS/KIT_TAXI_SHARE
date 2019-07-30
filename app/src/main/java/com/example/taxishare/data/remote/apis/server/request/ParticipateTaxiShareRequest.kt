/*
 * Created by WonJongSeong on 2019-07-30
 */

package com.example.taxishare.data.remote.apis.server.request

import com.example.taxishare.app.Constant
import java.util.HashMap

class ParticipateTaxiShareRequest(
    private val postId : Int
) : ServerRequest.PostRequest {

    companion object {
        private const val UID  = "userId"
        private const val POST_ID = "id"
    }

    override fun getRequest(): Map<String, String> {
        val params: MutableMap<String, String> = HashMap()

        params[UID] = Constant.USER_ID
        params[POST_ID] = postId.toString()

        return params
    }
}