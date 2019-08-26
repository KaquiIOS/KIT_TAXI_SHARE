/*
 * Created by WonJongSeong on 2019-07-18
 */

package com.meongbyeol.taxishare.view.main.taxisharelist

import com.meongbyeol.taxishare.data.model.TaxiShareInfo

interface TaxiShareInfoModifyClickListener {
    fun onTaxiShareInfoModifyClicked(selectedTaxiShareInfo : TaxiShareInfo, pos : Int)
}