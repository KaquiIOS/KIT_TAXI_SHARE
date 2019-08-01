/*
 * Created by WonJongSeong on 2019-07-13
 */

package com.example.taxishare.data.repo

import com.example.taxishare.data.model.Comment
import com.example.taxishare.data.model.Location
import com.example.taxishare.data.model.ServerResponse
import com.example.taxishare.data.model.TaxiShareInfo
import com.example.taxishare.data.remote.apis.server.request.*
import com.example.taxishare.data.remote.apis.server.response.LoginRequestResponse
import com.example.taxishare.data.remote.apis.server.response.RegisterCommentResponse
import io.reactivex.Observable

interface ServerRepository {
    fun loginRequest(loginRequest : ServerRequest.PostRequest) : Observable<LoginRequestResponse>
    fun isSameIdExist(serverRequest: ServerRequest.PostRequest): Observable<ServerResponse>
    fun isSameNicknameExist(serverRequest: ServerRequest.PostRequest) : Observable<ServerResponse>
    fun signUpRequest(signUpRequest: SignUpRequest) : Observable<ServerResponse>
    fun getSearchPlacesInfo(searchPlacesRequest: SearchPlacesRequest) : Observable<MutableList<Location>>
    fun registerTaxiShare(registerTaxiShareRequest: RegisterTaxiShareRequest) : Observable<ServerResponse>
    fun getTaxiShareList(taxiShareListGetRequest: TaxiShareListGetRequest) : Observable<MutableList<TaxiShareInfo>>
    fun registerComment(registerCommentRequest: RegisterCommentRequest) : Observable<Comment>
    fun loadComments(id : String, commentId : String) : Observable<MutableList<Comment>>
    fun participateTaxiShare(participateTaxiShareRequest: ParticipateTaxiShareRequest) : Observable<ServerResponse>
    fun removeComment(removeCommentRequest: RemoveCommentRequest) : Observable<ServerResponse>
    fun removeTaxiShare(removeTaxiShareRequest : TaxiShareRemoveRequest) : Observable<ServerResponse>
}