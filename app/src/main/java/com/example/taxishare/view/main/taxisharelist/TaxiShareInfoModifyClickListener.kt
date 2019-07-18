/*
 * Created by WonJongSeong on 2019-07-18
 */

package com.example.taxishare.view.main.taxisharelist

import com.example.taxishare.data.model.TaxiShareInfo

interface TaxiShareInfoModifyClickListener {
    fun onTaxiShareInfoModifyClicked(selectedTaxiShareInfo : TaxiShareInfo, pos : Int)
}