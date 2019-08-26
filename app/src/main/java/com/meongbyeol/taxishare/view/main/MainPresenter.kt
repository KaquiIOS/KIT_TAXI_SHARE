/*
 * Created by WonJongSeong on 2019-05-12
 */

package com.meongbyeol.taxishare.view.main

class MainPresenter(private val view: MainView) {


    fun setFilteringSetting(menuSelection: MenuSelection) {

        when (menuSelection) {
            MenuSelection.SET_END_LOCATION -> view.openEndLocationSettingActivity()
            MenuSelection.SET_START_LOCATION -> view.openStartLocationSettingActivity()
            MenuSelection.SET_START_TIME -> view.openStartTimeSettingActivity()
            MenuSelection.RESET_FILTERING_SETTING -> view.resetFilteringSetting()
            else -> {
            }
        }
    }

    fun changeToolbarName(isTaxiShareList : Boolean) {
        if(isTaxiShareList) {
            view.changeToolbarNameAsTaxiList()
        } else {
            view.changeToolbarNameAsMyPage()
        }
    }
}