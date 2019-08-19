package com.example.taxishare.view.main.mypage

import com.example.taxishare.data.model.MyTaxiShareItem

interface MyPageView {


    fun openDetailTaxiSharePage()

    fun setMyList(myList : MutableList<MyTaxiShareItem>)



}