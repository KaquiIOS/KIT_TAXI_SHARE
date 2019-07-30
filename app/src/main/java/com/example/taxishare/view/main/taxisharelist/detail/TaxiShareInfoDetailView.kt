/*
 * Created by WonJongSeong on 2019-07-19
 */

package com.example.taxishare.view.main.taxisharelist.detail

import com.example.taxishare.data.model.Comment

interface TaxiShareInfoDetailView {


    fun changeRegisterButtonState(isActivated : Boolean)

    fun addComments(commentList : MutableList<Comment>)
    fun insertComment(comment : Comment)

    fun registerCommentSuccess()
    fun registerCommentFail()
    fun registerCommentNotFinish()

    fun noCommentExist()
    fun loadCommentSuccess()
    fun loadCommentFail()
    fun loadCommentNotFinished()
}