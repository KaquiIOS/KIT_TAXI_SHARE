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

    fun loadTaxiShareListNotFinished()
    fun loadTaxiShareListFail()
}