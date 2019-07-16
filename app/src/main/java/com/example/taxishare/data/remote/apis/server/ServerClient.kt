/*
 * Created by WonJongSeong on 2019-04-09
 */

package com.example.taxishare.data.remote.apis.server

import com.example.taxishare.app.Constant
import com.example.taxishare.data.model.Location
import com.example.taxishare.data.model.ServerResponse
import com.example.taxishare.data.model.TaxiShareInfo
import com.example.taxishare.data.remote.apis.server.request.*
import com.example.taxishare.data.remote.apis.server.response.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

    fun loginRequest(loginRequest : ServerRequest.PostRequest) : Observable<LoginRequestResponse> =
            retrofit.create(ServerAPI::class.java)
                .loginRequest(loginRequest.getRequest())

    fun isSameIdExist(serverRequest: ServerRequest.PostRequest): Observable<DuplicateIdExistCheckResponse> =
        retrofit.create(ServerAPI::class.java)
            .checkSameIdExist(serverRequest.getRequest())

    fun isSameNicknameExist(serverRequest: ServerRequest.PostRequest) : Observable<DuplicateNicknameExistCheckResponse> =
            retrofit.create(ServerAPI::class.java)
                .checkSameNickNameExist(serverRequest.getRequest())

    fun signUpRequest(signUpRequest: SignUpRequest) : Observable<SignUpRequestResponse> =
            retrofit.create(ServerAPI::class.java)
                .signUpRequest(signUpRequest.getRequest())

    fun getSearchPlacesInfo(searchPlacesRequest: SearchPlacesRequest) : Observable<MutableList<Location>> =
        retrofit.create(ServerAPI::class.java)
            .getSearchPlacesInfo(searchPlacesRequest.query)

    fun registerTaxiShareInfo(registerTaxiShareRequest: RegisterTaxiShareRequest) : Observable<TaxiShareRegisterResponse> =
        retrofit.create(ServerAPI::class.java)
            .registerTaxiShareInfo(registerTaxiShareRequest.getRequest())
}