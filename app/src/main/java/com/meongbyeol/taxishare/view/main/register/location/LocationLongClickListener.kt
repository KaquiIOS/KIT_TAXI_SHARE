/*
 * Created by WonJongSeong on 2019-07-14
 */

package com.meongbyeol.taxishare.view.main.register.location

import com.meongbyeol.taxishare.data.model.Location

interface LocationLongClickListener {
    fun locationLongClicked(selectedLocation : Location)
}