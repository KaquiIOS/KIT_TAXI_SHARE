/*
 * Created by WonJongSeong on 2019-05-20
 */

package com.example.taxishare.view.main.register.location

import com.example.taxishare.data.model.Location

interface LocationSearchView {

    fun displaySearchedLocationList(searchList: MutableList<Location>)


}