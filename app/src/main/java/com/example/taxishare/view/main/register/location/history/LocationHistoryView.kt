/*
 * Created by WonJongSeong on 2019-07-13
 */

package com.example.taxishare.view.main.register.location.history

import com.example.taxishare.data.model.Location

interface LocationHistoryView {

    fun setSearchHistoryList(historyList : MutableList<Location>)
}