/*
 * Created by WonJongSeong on 2019-07-13
 */

package com.example.taxishare.view.main.register.location.history

import com.example.taxishare.data.repo.LocationRepository
import com.example.taxishare.data.repo.MyLocationRepository
import io.reactivex.disposables.Disposable
import java.util.*

class LocationHistoryPresenter(
    private val view: LocationHistoryView,
    private val locationRepo: LocationRepository,
    private val savedLocationRepo: MyLocationRepository
) {

    private lateinit var loadDisposable: Disposable
    private lateinit var saveLoadDisposable: Disposable
    private lateinit var removeLocationDisposable: Disposable

    fun loadSearchLocationHistory() {

        if (::saveLoadDisposable.isInitialized && !saveLoadDisposable.isDisposed)
            saveLoadDisposable.dispose()

        if (!::loadDisposable.isInitialized || loadDisposable.isDisposed) {
            loadDisposable = locationRepo.getLocations(Date())
                .subscribe({
                    view.setSearchHistoryList(it)
                }, {
                    it.printStackTrace()
                })
        }
    }

    fun loadSavedLocation() {

        if (::loadDisposable.isInitialized && !loadDisposable.isDisposed)
            loadDisposable.dispose()

        if (!::saveLoadDisposable.isInitialized || saveLoadDisposable.isDisposed) {
            saveLoadDisposable = savedLocationRepo.gets(Date())
                .subscribe({
                    view.setSavedLocation(it)
                }, {
                    it.printStackTrace()
                })
        }
    }

    fun removeSavedLocation(savedName: String) {

        if (::loadDisposable.isInitialized && !loadDisposable.isDisposed)
            loadDisposable.dispose()

        if (!::removeLocationDisposable.isInitialized || removeLocationDisposable.isDisposed) {
            removeLocationDisposable = savedLocationRepo.delete(savedName)
                .subscribe({
                    view.reloadSavedLocation()
                }, {
                    it.printStackTrace()
                })
        }
    }

    fun onDestroy() {
        if (::saveLoadDisposable.isInitialized && !saveLoadDisposable.isDisposed)
            saveLoadDisposable.dispose()

        if (::loadDisposable.isInitialized && !loadDisposable.isDisposed)
            loadDisposable.dispose()

        if(::removeLocationDisposable.isInitialized && !removeLocationDisposable.isDisposed)
            removeLocationDisposable.dispose()
    }
}