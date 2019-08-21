package com.example.taxishare.view.main.mypage

import com.example.taxishare.data.model.Location
import com.example.taxishare.data.model.TaxiShareInfo
import com.example.taxishare.data.repo.ServerRepository
import io.reactivex.disposables.Disposable
import java.util.*

class MyPagePresenter(
    private val serverRepoImpl: ServerRepository,
    private val view: MyPageView
) {

    private lateinit var loadMyTaxiShareListDisposable: Disposable


    fun onCreate() {

    }

    fun onDestroy() {
        if (!::loadMyTaxiShareListDisposable.isInitialized && !loadMyTaxiShareListDisposable.isDisposed)
            loadMyTaxiShareListDisposable.dispose()
    }

    fun openDetailTaxiShareInfoPage(id: String, uid: String) {
        view.openDetailTaxiSharePage(
            TaxiShareInfo(
                id, uid, "", Date(0),
                Location(0.0, 0.0, "", "", ""),
                Location(0.0, 0.0, "", "", ""),
                0, "", "", 0, false
            )
        )
    }


    fun loadMyTaxiShareList() {
        if (!::loadMyTaxiShareListDisposable.isInitialized || loadMyTaxiShareListDisposable.isDisposed) {
            loadMyTaxiShareListDisposable = serverRepoImpl.loadMyTaxiShareList()
                .subscribe({
                    view.setMyList(it)
                    if (it.isEmpty()) {
                        view.setBackgroundWhite()
                    } else {
                        view.setBackgroundGray()
                    }
                }, {
                    it.printStackTrace()
                })
        }
    }
}