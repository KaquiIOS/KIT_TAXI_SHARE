/*
 * Created by WonJongSeong on 2019-04-09
 */

package com.example.taxishare.data.remote.apis.server

import com.example.taxishare.app.Constant
import com.example.taxishare.data.remote.apis.server.request.ServerRequest
import com.example.taxishare.data.remote.apis.server.request.SignUpRequest
import com.example.taxishare.data.remote.apis.server.response.DuplicateIdExistCheckResponse
import com.example.taxishare.data.remote.apis.server.response.LoginRequestResponse
import com.example.taxishare.data.remote.apis.server.response.SignUpRequestResponse
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

    fun loginRequest(loginRequest : ServerRequest) : Observable<LoginRequestResponse> =
            retrofit.create(ServerAPI::class.java)
                .loginRequest(loginRequest.getRequest())
                
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())


    fun isSameIdExist(serverRequest: ServerRequest): Observable<DuplicateIdExistCheckResponse> =
        retrofit.create(ServerAPI::class.java)
            .checkSameIdExist(serverRequest.getRequest())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    fun signUpRequest(signUpRequest: SignUpRequest) : Observable<SignUpRequestResponse> =
            retrofit.create(ServerAPI::class.java)
                .signUpRequest(signUpRequest.getRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
}