/*
 * Created by WonJongSeong on 2019-07-18
 */

package com.example.taxishare.view.main.taxisharelist

import com.example.taxishare.app.Constant
import com.example.taxishare.data.model.ServerResponse
import com.example.taxishare.data.remote.apis.server.request.ParticipateTaxiShareRequest
import com.example.taxishare.data.remote.apis.server.request.TaxiShareListGetRequest
import com.example.taxishare.data.remote.apis.server.request.TaxiShareRemoveRequest
import com.example.taxishare.data.repo.ServerRepository
import com.example.taxishare.view.main.taxisharelist.detail.TestInterface
import io.reactivex.disposables.Disposable

class TaxiShareListPresenter(
    private val view: TaxiShareListView,
    private val serverRepo: ServerRepository
) {

    private var nextPageNum: Int = -1
    private lateinit var loadTaxiShareInfoDisposable: Disposable
    private lateinit var participateTaxiShareDisposable: Disposable
    private lateinit var removeTaxiShareDisposable: Disposable


    fun removeTaxiShareInfo(postId: String) {

        if (!::removeTaxiShareDisposable.isInitialized || removeTaxiShareDisposable.isDisposed) {
            removeTaxiShareDisposable = serverRepo.removeTaxiShare(TaxiShareRemoveRequest(postId))
                .subscribe({
                    if(it == ServerResponse.TAXISHARE_REMOVE_SUCCESS) {
                        view.showRemoveTaxiShareSuccess(postId .toInt())
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

    fun participateTaxiShare(postId : String) {

        if (!::participateTaxiShareDisposable.isInitialized || participateTaxiShareDisposable.isDisposed) {
            participateTaxiShareDisposable = serverRepo.participateTaxiShare(ParticipateTaxiShareRequest(postId))
                .subscribe({
                    if(it == ServerResponse.PARTICIPATE_TAXI_SHARE_SUCCESS) {
                        view.showParticipateTaxiShareSuccess(postId)
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
                            view.setTaxiShareList(it)
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
}