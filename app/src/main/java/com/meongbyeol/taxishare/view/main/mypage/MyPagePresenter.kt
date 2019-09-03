package com.meongbyeol.taxishare.view.main.mypage

import com.meongbyeol.taxishare.data.model.TaxiShareInfo
import com.meongbyeol.taxishare.data.repo.ServerRepository
import io.reactivex.disposables.Disposable

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
            TaxiShareInfo(id, uid)
        )
    }


    fun loadMyTaxiShareList() {
        if (!::loadMyTaxiShareListDisposable.isInitialized || loadMyTaxiShareListDisposable.isDisposed) {
            loadMyTaxiShareListDisposable = serverRepoImpl.loadMyTaxiShareList()
                .subscribe({
                    if (it.isEmpty()) {
                        view.setBackgroundWhite()
                    } else {
                        view.setBackgroundGray()
                    }
                    view.setMyList(it)
                }, {
                    it.printStackTrace()
                })
        }
    }
}