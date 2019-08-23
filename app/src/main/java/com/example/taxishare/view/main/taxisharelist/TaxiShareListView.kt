/*
 * Created by WonJongSeong on 2019-07-18
 */

package com.example.taxishare.view.main.taxisharelist

import com.example.taxishare.data.model.TaxiShareInfo

interface TaxiShareListView {

    fun setTaxiShareList(taxiShareList : MutableList<TaxiShareInfo>, isRefresh : Boolean)

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

    fun showMessageDialog()
    fun dismissMessageDialog()

    fun setBackgroundGray()
    fun setBackgroundWhite()

    fun dismissRefresh()

    fun showLoadingDialog()
    fun dismissLoadingDialog()
}