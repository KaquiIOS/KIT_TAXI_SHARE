/*
 * Created by WonJongSeong on 2019-07-25
 */

package com.example.taxishare.data.remote.apis.server.request

class RegisterCommentRequest(private val id: String, private val uid: String, private val content: String) :
    ServerRequest.PostRequest {

    companion object {
        private const val ID: String = "id"
        private const val UID: String = "uid"
        private const val CONTENT : String = "content"
    }

    override fun getRequest(): Map<String, String> {
        val request: MutableMap<String, String> = HashMap()
        request[ID] = id
        request[UID] = uid
        request[CONTENT] = content
        return request
    }
}