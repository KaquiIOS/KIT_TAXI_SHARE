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
                    view.setTaxiShareList(it)
                }, {
                    view.loadTaxiShareListFail()
                    it.printStackTrace()
                })
        } else {
            view.loadTaxiShareListNotFinished()
        }
    }
}