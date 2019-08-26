/*
 * Created by WonJongSeong on 2019-05-20
 */

package com.meongbyeol.taxishare.view.main.register.location

import com.meongbyeol.taxishare.data.model.Location

interface LocationSearchView {

    fun displaySearchedLocationList(searchList: MutableList<Location>)

    fun locationSaveFinish(location: Location)
}