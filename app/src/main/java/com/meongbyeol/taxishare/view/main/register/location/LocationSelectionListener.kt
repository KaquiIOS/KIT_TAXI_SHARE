/*
 * Created by WonJongSeong on 2019-07-07
 */

package com.meongbyeol.taxishare.view.main.register.location

import com.meongbyeol.taxishare.data.model.Location

interface LocationSelectionListener {
    fun locationSelected(location : Location)
}