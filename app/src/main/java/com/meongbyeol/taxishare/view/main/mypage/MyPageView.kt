package com.meongbyeol.taxishare.view.main.mypage

import com.meongbyeol.taxishare.data.model.MyTaxiShareItem
import com.meongbyeol.taxishare.data.model.TaxiShareInfo

interface MyPageView {
    fun openDetailTaxiSharePage(taxiShareInfo : TaxiShareInfo)
    fun setMyList(myList : MutableList<MyTaxiShareItem>)
    fun loadMyListFail()
    fun setBackgroundGray()
    fun setBackgroundWhite()
}