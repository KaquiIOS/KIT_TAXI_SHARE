/*
 * Created by WonJongSeong on 2019-05-12
 */

package com.meongbyeol.taxishare.view.main

interface MainView {

    fun openStartLocationSettingActivity()
    fun openEndLocationSettingActivity()
    fun openStartTimeSettingActivity()
    fun resetFilteringSetting()

    fun changeToolbarNameAsTaxiList()
    fun changeToolbarNameAsMyPage()
}