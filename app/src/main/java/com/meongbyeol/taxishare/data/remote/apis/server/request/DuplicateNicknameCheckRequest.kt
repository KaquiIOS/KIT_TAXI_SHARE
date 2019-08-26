/*
 * Created by WonJongSeong on 2019-05-01
 */

package com.meongbyeol.taxishare.data.remote.apis.server.request

class DuplicateNicknameCheckRequest(val nickname : String) : ServerRequest.PostRequest {

    companion object {
        private const val NICKNAME: String = "NICKNAME"
    }


    override fun getRequest(): Map<String, String> {
        val params: MutableMap<String, String> = HashMap()
        params[NICKNAME] = nickname
        return params
    }
}