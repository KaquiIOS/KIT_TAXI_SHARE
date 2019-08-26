/*
 * Created by WonJongSeong on 2019-07-13
 */

package com.meongbyeol.taxishare.view.main.register.location.history

import com.meongbyeol.taxishare.data.model.Location
import com.meongbyeol.taxishare.data.model.MyLocation

interface LocationHistoryView {

    fun setSearchHistoryList(historyList : MutableList<Location>)
    fun setSavedLocation(savedList : MutableList<MyLocation>)
    fun reloadSavedLocation()
}