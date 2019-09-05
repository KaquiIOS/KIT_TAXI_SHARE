/*
 * Created by WonJongSeong on 2019-07-13
 */

package com.meongbyeol.taxishare.data.repo

import com.meongbyeol.taxishare.data.model.*
import com.meongbyeol.taxishare.data.remote.apis.server.request.*
import com.meongbyeol.taxishare.data.remote.apis.server.response.DetailTaxiShareLoadResponse
import com.meongbyeol.taxishare.data.remote.apis.server.response.LoginRequestResponse
import com.meongbyeol.taxishare.data.remote.apis.server.response.TaxiShareRegisterResponse
import io.reactivex.Observable

interface ServerRepository {
    fun loginRequest(loginRequest : ServerRequest.PostRequest) : Observable<LoginRequestResponse>
    fun isSameIdExist(serverRequest: ServerRequest.PostRequest): Observable<ServerResponse>
    fun isSameNicknameExist(serverRequest: ServerRequest.PostRequest) : Observable<ServerResponse>
    fun signUpRequest(signUpRequest: SignUpRequest) : Observable<ServerResponse>
    fun getSearchPlacesInfo(searchPlacesRequest: SearchPlacesRequest) : Observable<MutableList<Location>>
    fun registerTaxiShare(registerTaxiShareRequest: RegisterTaxiShareRequest) : Observable<TaxiShareRegisterResponse>
    fun getTaxiShareList(taxiShareListGetRequest: TaxiShareListGetRequest) : Observable<MutableList<TaxiShareInfo>>
    fun registerComment(registerCommentRequest: RegisterCommentRequest) : Observable<Comment>
    fun loadComments(id : String, commentId : String) : Observable<MutableList<Comment>>
    fun participateTaxiShare(participateTaxiShareRequest: ParticipateTaxiShareRequest) : Observable<ServerResponse>
    fun removeComment(removeCommentRequest: RemoveCommentRequest) : Observable<ServerResponse>
    fun removeTaxiShare(removeTaxiShareRequest : TaxiShareRemoveRequest) : Observable<ServerResponse>
    fun updateTaxiShare(updateTaxiShareModifyRequest: TaxiShareModifyRequest) : Observable<ServerResponse>
    fun leaveTaxiShare(leaveTaxiShareRequest: LeaveTaxiShareRequest) : Observable<ServerResponse>
    fun loadDetailTaxiShareInfo(detailTaxiShareLoadRequest: DetailTaxiShareLoadRequest) : Observable<DetailTaxiShareLoadResponse>
    fun loadMyTaxiShareList() : Observable<MutableList<MyTaxiShareItem>>
    fun findPassword(findPasswordRequest: FindPasswordRequest) : Observable<ServerResponse>
    fun updateFCMToken(userId : String, token : String) : Observable<ServerResponse>
    fun getAppVersion() : Observable<Int>
}