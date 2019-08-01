/*
 * Created by WonJongSeong on 2019-07-13
 */

package com.example.taxishare.data.repo

import com.example.taxishare.data.mapper.TypeMapper
import com.example.taxishare.data.model.Comment
import com.example.taxishare.data.model.Location
import com.example.taxishare.data.model.ServerResponse
import com.example.taxishare.data.model.TaxiShareInfo
import com.example.taxishare.data.remote.apis.server.ServerClient
import com.example.taxishare.data.remote.apis.server.request.*
import com.example.taxishare.data.remote.apis.server.response.LoginRequestResponse
import com.example.taxishare.data.remote.apis.server.response.RegisterCommentResponse
import com.example.taxishare.data.remote.apis.server.response.TaxiShareRegisterResponse
import com.example.taxishare.extension.uiSubscribe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ServerRepositoryImpl(private val serverClient: ServerClient) : ServerRepository {

    companion object {
        @Volatile
        private var INSTANCE: ServerRepository? = null

        fun getInstance(serverClient: ServerClient): ServerRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ServerRepositoryImpl(serverClient)
            }
    }

    override fun loginRequest(loginRequest: ServerRequest.PostRequest): Observable<LoginRequestResponse> =
        serverClient.loginRequest(loginRequest)
            .uiSubscribe()

    override fun isSameIdExist(serverRequest: ServerRequest.PostRequest): Observable<ServerResponse> =
        serverClient.isSameIdExist(serverRequest)
            .map { ServerResponse.fromServerResponseCode(it.responseCode) }
            .uiSubscribe()

    override fun isSameNicknameExist(serverRequest: ServerRequest.PostRequest): Observable<ServerResponse> =
        serverClient.isSameNicknameExist(serverRequest)
            .map { ServerResponse.fromServerResponseCode(it.responseCode) }
            .uiSubscribe()

    override fun signUpRequest(signUpRequest: SignUpRequest): Observable<ServerResponse> =
        serverClient.signUpRequest(signUpRequest)
            .map { ServerResponse.fromServerResponseCode(it.responseCode) }
            .uiSubscribe()

    override fun getSearchPlacesInfo(searchPlacesRequest: SearchPlacesRequest): Observable<MutableList<Location>> =
        serverClient.getSearchPlacesInfo(searchPlacesRequest)
            .uiSubscribe()

    override fun registerTaxiShare(registerTaxiShareRequest: RegisterTaxiShareRequest): Observable<TaxiShareRegisterResponse> =
        serverClient.registerTaxiShareInfo(registerTaxiShareRequest)
            .uiSubscribe()

    override fun getTaxiShareList(taxiShareListGetRequest: TaxiShareListGetRequest): Observable<MutableList<TaxiShareInfo>> =
        serverClient.getTaxiShareInfo(taxiShareListGetRequest.nextPageNum, taxiShareListGetRequest.uid)
            .map { TypeMapper.taxiShareInfoModelToData(it) }
            .uiSubscribe()

    override fun registerComment(registerCommentRequest: RegisterCommentRequest): Observable<Comment> =
        serverClient.registerComment(registerCommentRequest)
            .map { TypeMapper.registerCommentResponseToComment(it) }
            .uiSubscribe()

    override fun loadComments(id: String, commentId : String): Observable<MutableList<Comment>> =
        serverClient.loadComments(id, commentId)
            .map { TypeMapper.commentModelToComment(it) }
            .uiSubscribe()

    override fun participateTaxiShare(participateTaxiShareRequest: ParticipateTaxiShareRequest): Observable<ServerResponse> =
        serverClient.participateTaxiShare(participateTaxiShareRequest)
            .map { ServerResponse.fromServerResponseCode(it.responseCode) }
            .uiSubscribe()

    override fun removeComment(removeCommentRequest: RemoveCommentRequest): Observable<ServerResponse> =
        serverClient.removeComment(removeCommentRequest)
            .map { ServerResponse.fromServerResponseCode(it.responseCode) }
            .uiSubscribe()

    override fun removeTaxiShare(removeTaxiShareRequest: TaxiShareRemoveRequest): Observable<ServerResponse> =
        serverClient.removeTaxiSharePost(removeTaxiShareRequest)
            .map { ServerResponse.fromServerResponseCode(it.responseCode) }
            .uiSubscribe()
}