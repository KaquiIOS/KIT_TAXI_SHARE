/*
 * Created by WonJongSeong on 2019-07-13
 */

package com.example.taxishare.data.repo

import com.example.taxishare.data.model.Location
import com.example.taxishare.data.model.ServerResponse
import com.example.taxishare.data.remote.apis.server.request.RegisterTaxiShareRequest
import com.example.taxishare.data.remote.apis.server.request.SearchPlacesRequest
import com.example.taxishare.data.remote.apis.server.request.ServerRequest
import com.example.taxishare.data.remote.apis.server.request.SignUpRequest
import io.reactivex.Observable

interface ServerRepository {
    fun loginRequest(loginRequest : ServerRequest.PostRequest) : Observable<ServerResponse>
    fun isSameIdExist(serverRequest: ServerRequest.PostRequest): Observable<ServerResponse>
    fun isSameNicknameExist(serverRequest: ServerRequest.PostRequest) : Observable<ServerResponse>
    fun signUpRequest(signUpRequest: SignUpRequest) : Observable<ServerResponse>
    fun getSearchPlacesInfo(searchPlacesRequest: SearchPlacesRequest) : Observable<MutableList<Location>>
    fun registerTaxiShare(registerTaxiShareRequest: RegisterTaxiShareRequest) : Observable<ServerResponse>
}