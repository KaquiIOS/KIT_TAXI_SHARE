/*
 * Created by WonJongSeong on 2019-07-18
 */

package com.example.taxishare.view.main.taxisharelist

import com.example.taxishare.app.AlarmManagerInterface
import com.example.taxishare.app.Constant
import com.example.taxishare.data.model.ServerResponse
import com.example.taxishare.data.remote.apis.server.request.LeaveTaxiShareRequest
import com.example.taxishare.data.remote.apis.server.request.ParticipateTaxiShareRequest
import com.example.taxishare.data.remote.apis.server.request.TaxiShareListGetRequest
import com.example.taxishare.data.remote.apis.server.request.TaxiShareRemoveRequest
import com.example.taxishare.data.repo.ServerRepository
import io.reactivex.disposables.Disposable
import java.util.*

class TaxiShareListPresenter(
    private val view: TaxiShareListView,
    private val serverRepo: ServerRepository,
    private val alarmManager : AlarmManagerInterface
) {

    private var nextPageNum: Int = -1
    private lateinit var loadTaxiShareInfoDisposable: Disposable
    private lateinit var participateTaxiShareDisposable: Disposable
    private lateinit var removeTaxiShareDisposable: Disposable
    private lateinit var leaveTaxiShareDisposable: Disposable


    fun leaveTaxiShare(postId: String) {
        if (!::leaveTaxiShareDisposable.isInitialized || leaveTaxiShareDisposable.isDisposed) {
            leaveTaxiShareDisposable = serverRepo.leaveTaxiShare(LeaveTaxiShareRequest(postId))
                .subscribe({
                    if (it == ServerResponse.TAXISHARE_LEAVE_SUCCESS) {
                        view.showLeaveTaxiShareSuccess(postId.toInt())
                        alarmManager.cancelAlarm(postId.toInt())
                    } else {
                        view.showLeaveTaxiShareFail()
                    }
                }, {
                    it.printStackTrace()
                    view.showLeaveTaxiShareFail()
                })
        } else {
            view.showLeaveTaxiShareNotFinish()
        }
    }

    fun removeTaxiShareInfo(postId: String) {

        if (!::removeTaxiShareDisposable.isInitialized || removeTaxiShareDisposable.isDisposed) {
            removeTaxiShareDisposable = serverRepo.removeTaxiShare(TaxiShareRemoveRequest(postId))
                .subscribe({
                    if (it == ServerResponse.TAXISHARE_REMOVE_SUCCESS) {
                        view.showRemoveTaxiShareSuccess(postId.toInt())
                        alarmManager.cancelAlarm(postId.toInt())
                    } else {
                        view.showRemoveTaxiShareFail()
                    }
                }, {
                    it.printStackTrace()
                    view.showRemoveTaxiShareFail()
                })
        } else {
            view.showRemoveTaxiShareNotFinish()
        }
    }

    fun participateTaxiShare(postId: String, date : Date) {

        if (!::participateTaxiShareDisposable.isInitialized || participateTaxiShareDisposable.isDisposed) {
            participateTaxiShareDisposable = serverRepo.participateTaxiShare(ParticipateTaxiShareRequest(postId))
                .subscribe({
                    if (it == ServerResponse.PARTICIPATE_TAXI_SHARE_SUCCESS) {
                        view.showParticipateTaxiShareSuccess(postId)
                        alarmManager.setOneTimeAlarm(postId.toInt(), Calendar.getInstance().apply { time = date })
                    } else {
                        view.showParticipateTaxiShareFail()
                    }
                }, {
                    it.printStackTrace()
                    view.showParticipateTaxiShareFail()
                })
        } else {
            view.showParticipateTaxiShareNotFinish()
        }
    }

    fun loadTaxiShareInfoList(isLatest: Boolean) {

        if (!::loadTaxiShareInfoDisposable.isInitialized || loadTaxiShareInfoDisposable.isDisposed) {

            if (isLatest) nextPageNum = -1

            loadTaxiShareInfoDisposable =
                serverRepo.getTaxiShareList(TaxiShareListGetRequest(nextPageNum, Constant.USER_ID.toInt()))
                    .subscribe({
                        if (it.size > 0) {
                            nextPageNum = it[it.size - 1].id.toInt()
                            view.setTaxiShareList(it, isLatest)
                        } else {
                            view.showLastPageOfTaxiShareListMessage()
                        }
                    }, {
                        view.showLoadTaxiShareListFailMessage()
                        it.printStackTrace()
                    })
        } else {
            view.showLoadTaxiShareListNotFinishedMessage()
        }
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
    }
}