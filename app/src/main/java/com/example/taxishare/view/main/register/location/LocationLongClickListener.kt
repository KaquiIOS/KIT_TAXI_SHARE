/*
 * Created by WonJongSeong on 2019-07-14
 */

package com.example.taxishare.view.main.register.location

import com.example.taxishare.data.model.Location

interface LocationLongClickListener {
    fun locationLongClicked(selectedLocation : Location)
}