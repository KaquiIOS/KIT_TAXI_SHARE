package com.example.taxishare.view.main.mypage

import com.example.taxishare.data.model.MyTaxiShareItem
import com.example.taxishare.data.model.TaxiShareInfo

interface MyPageView {


    fun openDetailTaxiSharePage(taxiShareInfo : TaxiShareInfo)

    fun setMyList(myList : MutableList<MyTaxiShareItem>)

    fun loadMyListFail()

    fun setBackgroundGray()
    fun setBackgroundWhite()

}