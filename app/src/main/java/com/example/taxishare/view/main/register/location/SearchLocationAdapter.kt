/*
 * Created by WonJongSeong on 2019-05-15
 */

package com.example.taxishare.view.main.register.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taxishare.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.location_viewholder.view.*
import java.lang.ref.WeakReference


class SearchLocationAdapter constructor(private val mapView: MapView, private val animation: Animation) :
    RecyclerView.Adapter<SearchLocationAdapter.SearchLocationViewHolder>(), OnMapReadyCallback {

    private val locationList: ArrayList<LatLng> by lazy { ArrayList<LatLng>() }
    private lateinit var mapRef: WeakReference<SearchLocationViewHolder>
    private lateinit var googleMap: GoogleMap

    init {
        mapView.apply {
            onCreate(null)
            getMapAsync(this@SearchLocationAdapter)
        }

        locationList.add(LatLng(-33.920455, 18.466941))
        locationList.add(LatLng(39.937795, 116.387224))
        locationList.add(LatLng(46.948020, 7.448206))
        locationList.add(LatLng(51.589256, 4.774396))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchLocationViewHolder =
        SearchLocationViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.location_viewholder,
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
        // 구글 맵 초기화
        with(googleMap) {
            // 위치 설정 및 줌정도 설정
            moveCamera(CameraUpdateFactory.newLatLngZoom(locationList[position], 13f))
            // addMarker
            addMarker(
                MarkerOptions().position(locationList[position])
                    .title("출발")
                    .alpha(0.0f)
                    .infoWindowAnchor(0.5f, 1.0f))
                .showInfoWindow()
            // 지도 타입
            mapType = GoogleMap.MAP_TYPE_NORMAL
        }
    }

    override fun onBindViewHolder(holder: SearchLocationViewHolder, position: Int) {
        holder.bindView(position)
        holder.itemView.setOnClickListener {
            if (!::mapRef.isInitialized) {
                mapRef = WeakReference(holder)
                addView(holder, position)
            } else if (mapRef.get() != holder) {
                removeView(mapRef.get())
                mapRef = WeakReference(holder)
                addView(holder, position)
            } else {
                mapRef.clear()
                removeView(holder)
            }
        }
    }

    inner class SearchLocationViewHolder(private val viewHolder: View) : RecyclerView.ViewHolder(viewHolder) {

        private val name: TextView = viewHolder.text_view_search_location_name
        private val address: TextView = viewHolder.text_view_search_location_address

        fun bindView(position: Int) {
            locationList[position].let {
                viewHolder.text_view_search_location_name.text = "test"
            }
        }
    }

    private fun addView(curHolder: SearchLocationViewHolder, position: Int) {
        setMapLocation(position)
        curHolder.itemView.search_list_constraint.addView(mapView)
        mapView.startAnimation(animation)
    }

    private fun removeView(preHolder: SearchLocationViewHolder?) {
        preHolder?.itemView?.search_list_constraint?.removeView(mapView)
    }
}