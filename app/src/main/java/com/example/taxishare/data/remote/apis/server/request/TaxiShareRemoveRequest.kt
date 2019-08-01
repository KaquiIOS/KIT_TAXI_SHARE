/*
 * Created by WonJongSeong on 2019-08-01
 */

package com.example.taxishare.data.remote.apis.server.request

data class TaxiShareRemoveRequest(private val postId: String) : ServerRequest.PostRequest {
    companion object {
        private const val POST_ID: String = "id"
    }

    override fun getRequest(): Map<String, String> {
        val request: MutableMap<String, String> = HashMap()
        request[POST_ID] = postId
        return request
    }
}