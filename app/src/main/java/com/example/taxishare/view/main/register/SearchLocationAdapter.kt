/*
 * Created by WonJongSeong on 2019-05-15
 */

package com.example.taxishare.view.main.register

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.location_viewholder.view.*

class SearchLocationAdapter : RecyclerView.Adapter<SearchLocationAdapter.SearchLocationViewHolder>() {

    private val locationList : ArrayList<LatLng> by lazy { ArrayList<LatLng>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchLocationViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: SearchLocationViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class SearchLocationViewHolder(private val viewHolder: View) : RecyclerView.ViewHolder(viewHolder), OnMapReadyCallback {

        private lateinit var googleMap: GoogleMap
        private lateinit var latLng: LatLng

        private val mapView: MapView = viewHolder.mv_search_location_map
        private val name: TextView = viewHolder.text_view_search_location_name
        private val address: TextView = viewHolder.text_view_search_location_address

        init {
            with(viewHolder.mv_search_location_map) {
                // MapView 초기화
                onCreate(null)
                // map ready callback 설정
                getMapAsync(this@SearchLocationViewHolder)
            }
        }

        private fun setMapLocation() {
            // 이미 초기화 된 경우는 무시
            if (!::googleMap.isInitialized) return
            // 구글 맵 초기화
            with(googleMap) {
                // 위치 설정 및 줌정도 설정
                moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f))
                // addMarker
                addMarker(MarkerOptions().position(latLng))
                // 지도 타입
                mapType = GoogleMap.MAP_TYPE_NORMAL
                // map listener 설정
                setOnMapClickListener {
                    // 추가할 내용 적어두기
                }
            }
        }

        // getMapAsync 의 callback
        override fun onMapReady(p0: GoogleMap?) {
            MapsInitializer.initialize(viewHolder.context)
            // If map is not initialised properly
            googleMap = p0 ?: return
            setMapLocation()
        }

        /** This function is called when the RecyclerView wants to bind the ViewHolder. */
        fun bindView(position: Int) {
            locationList[position].let {
                latLng = it
                mapView.tag = this
                viewHolder.text_view_search_location_name.text = "test"
                // We need to call setMapLocation from here because RecyclerView might use the
                // previously loaded maps
                setMapLocation()
            }
        }

        fun clearView() {
            with(googleMap) {
                clear()
                googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            }
        }
    }
}