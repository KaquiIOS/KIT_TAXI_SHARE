/*
 * Created by WonJongSeong on 2019-07-19
 */

package com.example.taxishare.view.main.taxisharelist.detail

import com.example.taxishare.data.model.Comment
import com.example.taxishare.data.model.TaxiShareDetailInfo

interface TaxiShareInfoDetailView {


    fun changeRegisterButtonState(isActivated: Boolean)

    fun addComments(commentList: MutableList<Comment>)
    fun insertComment(comment: Comment)

    fun registerCommentSuccess()
    fun registerCommentFail()
    fun registerCommentNotFinish()

    fun noCommentExist()
    fun loadCommentSuccess()
    fun loadCommentFail()
    fun loadCommentNotFinished()

    fun removeCommentSuccess(commentId: Int)
    fun removeCommentFail()
    fun removeCommentNotFinished()

    fun setDetailInfo(taxiShareInfo: TaxiShareDetailInfo)
    fun failLoadDetailInfo()
    fun loadDetailInfoNotFinish()
    fun detailInfoDeleted()

    fun showRemoveTaxiShareSuccess()
    fun showRemoveTaxiShareFail()
    fun showRemoveTaxiShareNotFinish()

    fun showParticipateTaxiShareSuccess()
    fun showParticipateTaxiShareFail()
    fun showParticipateTaxiShareNotFinish()

    fun showLeaveTaxiShareSuccess()
    fun showLeaveTaxiShareFail()
    fun showLeaveTaxiShareNotFinish()

    fun saveCurrentTaxiShareInfo()

    fun disableAllComponents()
}