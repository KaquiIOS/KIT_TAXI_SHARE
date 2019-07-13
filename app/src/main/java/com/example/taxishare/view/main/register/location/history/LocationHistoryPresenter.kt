/*
 * Created by WonJongSeong on 2019-07-13
 */

package com.example.taxishare.view.main.register.location.history

import com.example.taxishare.data.repo.LocationRepository
import io.reactivex.disposables.Disposable
import java.util.*

class LocationHistoryPresenter(private val view: LocationHistoryView, private val locationRepo: LocationRepository) {

    private lateinit var loadDisposable : Disposable

    fun loadSearchLocationHistory() {
        locationRepo.getLocations(Date())
            .subscribe({
                view.setSearchHistoryList(it)
            }, {

            })
    }
}