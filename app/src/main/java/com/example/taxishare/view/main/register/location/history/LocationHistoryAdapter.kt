/*
 * Created by WonJongSeong on 2019-05-15
 */

package com.example.taxishare.view.main.register.location.history


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taxishare.R
import com.example.taxishare.app.Constant
import com.example.taxishare.data.model.Location
import com.example.taxishare.view.main.register.location.LocationLongClickListener
import com.example.taxishare.view.main.register.location.LocationSelectionListener
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.item_history_location.view.*
import org.jetbrains.anko.sdk27.coroutines.onLongClick
import java.lang.ref.WeakReference


class LocationHistoryAdapter constructor(private val mapView: MapView, private val animation: Animation) :
    ListAdapter<Location, LocationHistoryAdapter.LocationHistoryViewHolder>(Location.DIFF_UTIL), OnMapReadyCallback {

    private val locationList: MutableList<Location> = mutableListOf()
    private lateinit var onSelectionListener: LocationSelectionListener
    private lateinit var onLocationLongClickListener: LocationLongClickListener
    private lateinit var mapRef: WeakReference<LocationHistoryViewHolder>
    private lateinit var googleMap: GoogleMap

    init {
        mapView.apply {
            onCreate(null)
            getMapAsync(this@LocationHistoryAdapter)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHistoryViewHolder =
        LocationHistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_history_location,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = locationList.size

    override fun onMapReady(p0: GoogleMap?) {
        MapsInitializer.initialize(mapView.context)
        // If map is not initialised properly
        googleMap = p0 ?: return
    }

    private fun setMapLocation(position: Int) {
        // 이미 초기화 된 경우는 무시
        if (!::googleMap.isInitialized) return
        // resume
        mapView.onResume()

        val curLatLng = LatLng(locationList[position].latitude, locationList[position].longitude)

        // 구글 맵 초기화
        with(googleMap) {
            // 위치 설정 및 줌정도 설정
            moveCamera(CameraUpdateFactory.newLatLngZoom(curLatLng, Constant.MAP_ZOOM_IN))
            // addMarker
            addMarker(
                MarkerOptions().position(curLatLng)
                    .title("출발")
                    .alpha(0.0f)
                    .infoWindowAnchor(0.5f, 1.0f)
            ).showInfoWindow()
            // 지도 타입
            mapType = GoogleMap.MAP_TYPE_NORMAL
        }
    }

    override fun onBindViewHolder(holderHistory: LocationHistoryViewHolder, position: Int) {
        holderHistory.bindView(position)
        holderHistory.itemView.setOnClickListener {
            if (!::mapRef.isInitialized) {
                mapRef = WeakReference(holderHistory)
                addView(holderHistory, position)
            } else if (mapRef.get() != holderHistory) {
                removeView(mapRef.get())
                mapRef = WeakReference(holderHistory)
                addView(holderHistory, position)
            } else {
                mapRef.clear()
                removeView(holderHistory)
            }
        }
        holderHistory.itemView.onLongClick {
            if (::onLocationLongClickListener.isInitialized) {
                onLocationLongClickListener.locationLongClicked(locationList[position])
            }
        }
        holderHistory.itemView.btn_history_item_select.setOnClickListener {
            if (::onSelectionListener.isInitialized) {
                onSelectionListener.locationSelected(locationList[position])
            }
        }
    }

    fun setSearchHistoryList(searchHistoryList: MutableList<Location>) {
        locationList.clear()
        locationList.addAll(searchHistoryList)
        submitList(searchHistoryList)
    }

    fun setOnSelectionListener(onSelectionListener: LocationSelectionListener) {
        this@LocationHistoryAdapter.onSelectionListener = onSelectionListener
    }

    fun setOnLocationLongClickListener(locationLongClickListener: LocationLongClickListener) {
        this@LocationHistoryAdapter.onLocationLongClickListener = locationLongClickListener
    }

    inner class LocationHistoryViewHolder(private val viewHolder: View) : RecyclerView.ViewHolder(viewHolder) {

        fun bindView(position: Int) {
            with(locationList[position]) {
                viewHolder.tv_history_item_name.text = locationName
                viewHolder.tv_history_item_jibun_address.text = jibunAddress
                viewHolder.tv_history_item_road_address.text = roadAddress
            }
        }
    }

    private fun addView(curHolderHistory: LocationHistoryViewHolder, position: Int) {
        setMapLocation(position)
        curHolderHistory.itemView.search_list_linear.addView(mapView)
        mapView.startAnimation(animation)
    }

    private fun removeView(preHolderHistory: LocationHistoryViewHolder?) {
        preHolderHistory?.itemView?.search_list_linear?.removeView(mapView)
    }
}