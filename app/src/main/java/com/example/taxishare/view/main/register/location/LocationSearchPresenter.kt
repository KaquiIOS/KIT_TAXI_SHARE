/*
 * Created by WonJongSeong on 2019-05-20
 */

package com.example.taxishare.view.main.register.location

import android.util.Log
import com.example.taxishare.data.local.room.entity.LocationModel
import com.example.taxishare.data.model.Location
import com.example.taxishare.data.remote.apis.server.request.SearchPlacesRequest
import com.example.taxishare.data.repo.LocationRepository
import com.example.taxishare.data.repo.ServerRepository
import io.reactivex.disposables.Disposable
import java.util.*

class LocationSearchPresenter constructor(
    private val view: LocationSearchView,
    private val serverRepoImpl: ServerRepository,
    private val locationRepoImpl : LocationRepository
) {

    private lateinit var searchDisposable: Disposable

    fun searchLocation(searchTarget: String) {

        if (::searchDisposable.isInitialized && !searchDisposable.isDisposed)
            searchDisposable.dispose()

        searchDisposable = serverRepoImpl.getSearchPlacesInfo(SearchPlacesRequest(searchTarget))
            .subscribe({
                // 여기서 목록 갱신
                view.displaySearchedLocationList(it)
            }, {
                    it.printStackTrace()
            })
    }

    fun saveSelectedLocation(location : Location) {
        with(location) {
            locationRepoImpl.insertLocation(
                LocationModel(latitude, longitude, locationName, roadAddress, jibunAddress, Date())
            ).subscribe({
                Log.d("Test", "SaveSelectedLocationToDB")
                view.locationSaveFinish(location)
            }, {
                it.printStackTrace()
                view.locationSaveFinish(location)
            })
        }
    }

    fun onDestroy() {
        if (::searchDisposable.isInitialized && !searchDisposable.isDisposed)
            searchDisposable.dispose()
    }
}