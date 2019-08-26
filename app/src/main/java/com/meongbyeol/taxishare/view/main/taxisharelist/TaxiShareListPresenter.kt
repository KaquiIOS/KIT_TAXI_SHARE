/*
 * Created by WonJongSeong on 2019-07-18
 */

package com.meongbyeol.taxishare.view.main.taxisharelist

import com.meongbyeol.taxishare.app.AlarmManagerInterface
import com.meongbyeol.taxishare.app.Constant
import com.meongbyeol.taxishare.data.model.Location
import com.meongbyeol.taxishare.data.model.ServerResponse
import com.meongbyeol.taxishare.data.model.TaxiShareInfo
import com.meongbyeol.taxishare.data.remote.apis.server.request.LeaveTaxiShareRequest
import com.meongbyeol.taxishare.data.remote.apis.server.request.ParticipateTaxiShareRequest
import com.meongbyeol.taxishare.data.remote.apis.server.request.TaxiShareListGetRequest
import com.meongbyeol.taxishare.data.remote.apis.server.request.TaxiShareRemoveRequest
import com.meongbyeol.taxishare.data.repo.ServerRepository
import io.reactivex.disposables.Disposable
import java.util.*

class TaxiShareListPresenter(
    private val view: TaxiShareListView,
    private val serverRepo: ServerRepository,
    private val alarmManager: AlarmManagerInterface
) {

    private var nextPageNum: Int = -1
    private var noTaxiItemExist: Boolean = false
    private lateinit var loadTaxiShareInfoDisposable: Disposable
    private lateinit var participateTaxiShareDisposable: Disposable
    private lateinit var removeTaxiShareDisposable: Disposable
    private lateinit var leaveTaxiShareDisposable: Disposable

    private var startLocation: Location? = null
    private var endLocation: Location? = null
    private var startTime: Date? = null

    fun setStartLocation(location: Location) {
        startLocation = location
    }

    fun setEndLocation(location: Location) {
        endLocation = location
    }

    fun setStartTime(startTime: Date) {
        this.startTime = startTime
    }

    fun leaveTaxiShare(postId: String) {
        if (!::leaveTaxiShareDisposable.isInitialized || leaveTaxiShareDisposable.isDisposed) {

            view.showMessageDialog()

            leaveTaxiShareDisposable = serverRepo.leaveTaxiShare(LeaveTaxiShareRequest(postId))
                .subscribe({
                    if (it == ServerResponse.TAXISHARE_LEAVE_SUCCESS) {
                        view.showLeaveTaxiShareSuccess(postId.toInt())
                        alarmManager.cancelAlarm(postId.toInt())
                    } else {
                        view.showLeaveTaxiShareFail()
                    }
                    view.dismissMessageDialog()
                }, {
                    it.printStackTrace()
                    view.showLeaveTaxiShareFail()
                    view.dismissMessageDialog()
                })
        }
    }

    fun removeTaxiShareInfo(postId: String) {

        if (!::removeTaxiShareDisposable.isInitialized || removeTaxiShareDisposable.isDisposed) {
            view.showMessageDialog()
            removeTaxiShareDisposable = serverRepo.removeTaxiShare(TaxiShareRemoveRequest(postId))
                .subscribe({
                    if (it == ServerResponse.TAXISHARE_REMOVE_SUCCESS) {
                        view.showRemoveTaxiShareSuccess(postId.toInt())
                        alarmManager.cancelAlarm(postId.toInt())
                    } else {
                        view.showRemoveTaxiShareFail()
                    }
                    view.dismissMessageDialog()
                }, {
                    it.printStackTrace()
                    view.showRemoveTaxiShareFail()
                    view.dismissMessageDialog()
                })
        }
    }

    fun participateTaxiShare(postId: String, date: Date, startLocation : String, endLocation : String) {

        if (!::participateTaxiShareDisposable.isInitialized || participateTaxiShareDisposable.isDisposed) {
            view.showMessageDialog()
            participateTaxiShareDisposable = serverRepo.participateTaxiShare(ParticipateTaxiShareRequest(postId))
                .subscribe({
                    if (it == ServerResponse.PARTICIPATE_TAXI_SHARE_SUCCESS) {
                        view.showParticipateTaxiShareSuccess(postId)
                        alarmManager.setOneTimeAlarm(postId.toInt(), Calendar.getInstance().apply { time = date },
                            startLocation, endLocation)
                    } else {
                        view.showParticipateTaxiShareFail()
                    }
                    view.dismissMessageDialog()
                }, {
                    it.printStackTrace()
                    view.showParticipateTaxiShareFail()
                    view.dismissMessageDialog()
                })
        }
    }

    fun loadTaxiShareInfoList(isLatest: Boolean) {

        if (isLatest) {
            nextPageNum = -1
            noTaxiItemExist = false
        }

        if (!noTaxiItemExist && (!::loadTaxiShareInfoDisposable.isInitialized || loadTaxiShareInfoDisposable.isDisposed)) {
            view.showLoadingDialog()
            loadTaxiShareInfoDisposable =
                serverRepo.getTaxiShareList(TaxiShareListGetRequest(nextPageNum, Constant.CURRENT_USER.studentId))
                    .subscribe({

                        filterTaxiShareList(it, false)

                        if (it.size > 0) {
                            nextPageNum = it[it.size - 1].id.toInt()
                        } else {
                            noTaxiItemExist = true
                            view.showLastPageOfTaxiShareListMessage()
                        }
                        view.setTaxiShareList(it, isLatest)
                        view.dismissLoadingDialog()
                        view.dismissRefresh()
                    }, {
                        view.showLoadTaxiShareListFailMessage()
                        view.dismissLoadingDialog()
                        it.printStackTrace()
                    })
        }
    }

    fun filterTaxiShareList(taxiShareList : MutableList<TaxiShareInfo>, setTaxiShareList : Boolean) {

        val mutableList = mutableListOf<TaxiShareInfo>()
        val itr = taxiShareList.iterator()
        val temp = startTime
        while (itr.hasNext()) {
            val taxiTemp = itr.next()
            if (startLocation != null && (startLocation != taxiTemp.startLocation)) {
                itr.remove()
                continue
            }
            else if (endLocation != null && endLocation != taxiTemp.endLocation) {
                itr.remove()
                continue
            }
            else if (temp != null && (temp.time >= taxiTemp.startDate.time)) {
                itr.remove()
                continue
            }
            else {
                mutableList.add(taxiTemp)
            }
        }

        if(taxiShareList.isEmpty()) {
            view.setBackgroundWhite()
        } else {
            view.setBackgroundGray()
        }

        if(setTaxiShareList) {
            view.setTaxiShareList(mutableList, true)
        }
    }


    fun resetFilteringSetting() {
        startLocation = null
        endLocation = null
        startTime = null
    }

    fun onDestroy() {
        if (::removeTaxiShareDisposable.isInitialized && !removeTaxiShareDisposable.isDisposed)
            removeTaxiShareDisposable.dispose()

        if (::loadTaxiShareInfoDisposable.isInitialized && !loadTaxiShareInfoDisposable.isDisposed)
            loadTaxiShareInfoDisposable.dispose()

        if (::participateTaxiShareDisposable.isInitialized && !participateTaxiShareDisposable.isDisposed)
            participateTaxiShareDisposable.dispose()

        if (::leaveTaxiShareDisposable.isInitialized && !leaveTaxiShareDisposable.isDisposed)
            leaveTaxiShareDisposable.dispose()

        view.dismissMessageDialog()
    }
}