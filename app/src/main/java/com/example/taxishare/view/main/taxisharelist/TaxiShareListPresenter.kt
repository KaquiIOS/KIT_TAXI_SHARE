/*
 * Created by WonJongSeong on 2019-07-18
 */

package com.example.taxishare.view.main.taxisharelist

import com.example.taxishare.data.remote.apis.server.request.TaxiShareListGetRequest
import com.example.taxishare.data.repo.ServerRepository
import io.reactivex.disposables.Disposable

class TaxiShareListPresenter(
    private val view: TaxiShareListView,
    private val serverRepo: ServerRepository
) {

    private var nextPageNum: Int = -1
    private lateinit var loadTaxiShareInfoDisposable: Disposable

    fun loadTaxiShareInfoList(isLatest: Boolean) {

        if (!::loadTaxiShareInfoDisposable.isInitialized || loadTaxiShareInfoDisposable.isDisposed) {

            if (isLatest) nextPageNum = -1

            loadTaxiShareInfoDisposable = serverRepo.getTaxiShareList(TaxiShareListGetRequest(nextPageNum))
                .subscribe({
                    if(it.size > 0) {
                        it[it.size - 1]
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