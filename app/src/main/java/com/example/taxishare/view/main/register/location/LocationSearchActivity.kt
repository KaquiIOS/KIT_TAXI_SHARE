package com.example.taxishare.view.main.register.location

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taxishare.R
import com.example.taxishare.app.Constant
import com.google.android.gms.maps.MapView
import kotlinx.android.synthetic.main.activity_location_search.*


class LocationSearchActivity : AppCompatActivity(), LocationSearchView {

    private val searchListAdapter: SearchLocationAdapter by lazy {
        SearchLocationAdapter(
            animation = AnimationUtils.loadAnimation(this, R.anim.slide_down),
            mapView = MapView(this).apply {
                layoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        Constant.SEARCH_HISTORY_MAP_HEIGHT
                    )
            })
    }

    private val presenter: LocationSearchPresenter by lazy {
        LocationSearchPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_search)

        initView()
        initListener()
    }

    private fun initView() {

        et_search_location.hint = intent.getStringExtra(Constant.LOCATION_SEARCH_HINT)

        rcv_search_location_list.apply {
            adapter = searchListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            /*setRecyclerListener {
                (it as SearchLocationAdapter.SearchLocationViewHolder).apply {
                    it.googleMap.clear()
                    it.googleMap.mapType = GoogleMap.MAP_TYPE_NONE
                }
            }*/
        }
    }

    private fun initListener() {

    }
}
