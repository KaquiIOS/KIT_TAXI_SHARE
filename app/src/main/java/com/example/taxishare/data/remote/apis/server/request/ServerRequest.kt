/*
 * Created by WonJongSeong on 2019-04-10
 */

package com.example.taxishare.data.remote.apis.server.request

interface ServerRequest {
    fun getRequest() : Map<String, String>
}