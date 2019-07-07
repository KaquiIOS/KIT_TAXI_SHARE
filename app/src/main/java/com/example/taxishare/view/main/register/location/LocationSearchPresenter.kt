/*
 * Created by WonJongSeong on 2019-05-20
 */

package com.example.taxishare.view.main.register.location

import com.example.taxishare.data.remote.apis.server.ServerClient
import com.example.taxishare.data.remote.apis.server.request.SearchPlacesRequest
import io.reactivex.disposables.Disposable

class LocationSearchPresenter constructor(
    private val view: LocationSearchView,
    private val serverClient: ServerClient
) {

    private lateinit var searchDisposable: Disposable

    fun searchLocation(searchTarget: String) {

        if(::searchDisposable.isInitialized && !searchDisposable.isDisposed)
            searchDisposable.dispose()

        searchDisposable = serverClient.getSearchPlacesInfo(SearchPlacesRequest(searchTarget))
            .subscribe({
                // 여기서 목록 갱신
                view.displaySearchedLocationList(it)
            },
            {
                // 에러 처리
                it.printStackTrace()
            })

    }

    fun onDestroy() {
        if(::searchDisposable.isInitialized && !searchDisposable.isDisposed)
            searchDisposable.dispose()
    }
}