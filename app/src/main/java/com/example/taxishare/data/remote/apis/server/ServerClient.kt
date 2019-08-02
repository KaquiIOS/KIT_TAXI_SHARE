/*
 * Created by WonJongSeong on 2019-04-09
 */

package com.example.taxishare.data.remote.apis.server

import com.example.taxishare.app.Constant
import com.example.taxishare.data.model.CommentModel
import com.example.taxishare.data.model.Location
import com.example.taxishare.data.model.TaxiShareInfoModel
import com.example.taxishare.data.remote.apis.server.request.*
import com.example.taxishare.data.remote.apis.server.response.*
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ServerClient private constructor() {

    private var retrofit: Retrofit

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    companion object {
        private var INSTANCE: ServerClient? = null

        fun getInstance(): ServerClient =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ServerClient().also {
                    INSTANCE = it
                }
            }
    }

    fun loginRequest(loginRequest: ServerRequest.PostRequest): Observable<LoginRequestResponse> =
        retrofit.create(LoginAPI::class.java)
            .loginRequest(loginRequest.getRequest())

    fun isSameIdExist(serverRequest: ServerRequest.PostRequest): Observable<DuplicateIdExistCheckResponse> =
        retrofit.create(SignUpAPI::class.java)
            .checkSameIdExist(serverRequest.getRequest())

    fun isSameNicknameExist(serverRequest: ServerRequest.PostRequest): Observable<DuplicateNicknameExistCheckResponse> =
        retrofit.create(SignUpAPI::class.java)
            .checkSameNickNameExist(serverRequest.getRequest())

    fun signUpRequest(signUpRequest: SignUpRequest): Observable<SignUpRequestResponse> =
        retrofit.create(SignUpAPI::class.java)
            .signUpRequest(signUpRequest.getRequest())

    fun getSearchPlacesInfo(searchPlacesRequest: SearchPlacesRequest): Observable<MutableList<Location>> =
        retrofit.create(PlaceSearchAPI::class.java)
            .getSearchPlacesInfo(searchPlacesRequest.query)

    fun registerTaxiShareInfo(registerTaxiShareRequest: RegisterTaxiShareRequest): Observable<TaxiShareRegisterResponse> =
        retrofit.create(TaxiShareInfoAPI::class.java)
            .registerTaxiShareInfo(registerTaxiShareRequest.getRequest())

    fun getTaxiShareInfo(nextPageNum: Int, uid: Int): Observable<MutableList<TaxiShareInfoModel>> =
        retrofit.create(TaxiShareInfoAPI::class.java)
            .getTaxiShareInfo(nextPageNum, uid)


    fun registerComment(registerCommentRequest: RegisterCommentRequest): Observable<RegisterCommentResponse> =
        retrofit.create(CommentAPI::class.java)
            .registerComment(registerCommentRequest.getRequest())

    fun loadComments(id: String, commentId: String): Observable<MutableList<CommentModel>> =
        retrofit.create(CommentAPI::class.java)
            .loadComments(id, commentId)

    fun participateTaxiShare(participateTaxiShareRequest: ParticipateTaxiShareRequest): Observable<ParticipateTaxiShareResponse> =
        retrofit.create(TaxiShareInfoAPI::class.java)
            .participateTaxiShare(participateTaxiShareRequest.getRequest())


    fun removeComment(removeCommentRequest: RemoveCommentRequest) : Observable<RemoveCommentResponse> =
        retrofit.create(CommentAPI::class.java)
            .removeComment(removeCommentRequest.getRequest())

    fun removeTaxiSharePost(taxiShareRemoveRequest: TaxiShareRemoveRequest) : Observable<TaxiShareRemoveResponse> =
        retrofit.create(TaxiShareInfoAPI::class.java)
            .removeTaxiShare(taxiShareRemoveRequest.getRequest())

    fun updateTaxiSharePost(taxiShareModifyRequest: TaxiShareModifyRequest) : Observable<TaxiShareModifyResponse> =
        retrofit.create(TaxiShareInfoAPI::class.java)
            .updateTaxiShare(taxiShareModifyRequest.getRequest())

    fun leaveTaxiShare(leaveTaxiShareRequest: LeaveTaxiShareRequest) : Observable<LeaveTaxiShareResponse> =
        retrofit.create(TaxiShareInfoAPI::class.java)
            .leaveTaxiShare(leaveTaxiShareRequest.getRequest())

    fun loadDetailTaxiShareInfo(detailTaxiShareLoadRequest: DetailTaxiShareLoadRequest) : Observable<DetailTaxiShareLoadResponse> =
        retrofit.create(TaxiShareInfoAPI::class.java)
            .loadDetailTaxiShareInfo(detailTaxiShareLoadRequest.postId, detailTaxiShareLoadRequest.uid.toInt())

}