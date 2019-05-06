/*
 * Created by WonJongSeong on 2019-04-09
 */

package com.example.taxishare.data.remote.apis.server.request

class DuplicateIdExistCheckRequest(private val id: String) : ServerRequest {

    companion object {
        private const val ID: String = "ID"
    }

    // public fun getSameIdE
    override fun getRequest(): Map<String, String> {
        val params: MutableMap<String, String> = HashMap()
        params[ID] = id
        return params
    }
}