/*
 * Created by WonJongSeong on 2019-07-18
 */

package com.example.taxishare.view.main.taxisharelist

import com.example.taxishare.data.model.TaxiShareInfo

interface TaxiShareListView {

    fun setTaxiShareList(taxiShareList : MutableList<TaxiShareInfo>)
    fun insertTaxiShareInfo(taxiShareInfo : TaxiShareInfo)
    fun removeTaxiShareInfo(pos : Int)
    fun modifyTaxiShareInfo(pos : Int)

    fun showLoadTaxiShareListNotFinishedMessage()
    fun showLoadTaxiShareListFailMessage()
    fun showLastPageOfTaxiShareListMessage()
    fun showParticipateTaxiShareSuccess(postId : String)
    fun showParticipateTaxiShareFail()
    fun showParticipateTaxiShareNotFinish()
    fun showRemoveTaxiShareSuccess(postId: Int)
    fun showRemoveTaxiShareFail()
    fun showRemoveTaxiShareNotFinish()
    fun showLeaveTaxiShareSuccess(postId: Int)
    fun showLeaveTaxiShareFail()
    fun showLeaveTaxiShareNotFinish()
}