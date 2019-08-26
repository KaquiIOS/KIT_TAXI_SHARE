/*
 * Created by WonJongSeong on 2019-07-30
 */

package com.meongbyeol.taxishare.data.remote.apis.server

import com.meongbyeol.taxishare.data.model.CommentModel
import com.meongbyeol.taxishare.data.remote.apis.server.response.RegisterCommentResponse
import com.meongbyeol.taxishare.data.remote.apis.server.response.RemoveCommentResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CommentAPI {

    @POST("registerComment")
    fun registerComment(@Body serverRequest: Map<String, String>): Observable<RegisterCommentResponse>

    @GET("loadComments")
    fun loadComments(@Query("id") id: String, @Query("commentId") commentId: String): Observable<MutableList<CommentModel>>

    @POST("removeComment")
    fun removeComment(@Body serverRequest: Map<String, String>) : Observable<RemoveCommentResponse>

}