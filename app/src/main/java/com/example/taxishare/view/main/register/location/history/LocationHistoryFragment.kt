package com.example.taxishare.view.main.register.location.history


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taxishare.R
import com.example.taxishare.app.Constant
import com.example.taxishare.view.main.register.location.LocationSelectionListener
import com.google.android.gms.maps.MapView
import kotlinx.android.synthetic.main.fragment_location_history.*

class LocationHistoryFragment : Fragment() {

    companion object {
        fun newInstance() =
            LocationHistoryFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private lateinit var locationSelectionListener: LocationSelectionListener

    private val searchHistoryListAdapter: LocationHistoryAdapter by lazy {
        LocationHistoryAdapter(
            animation = AnimationUtils.loadAnimation(context, R.anim.slide_down),
            mapView = MapView(context).apply {
                layoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        Constant.SEARCH_HISTORY_MAP_HEIGHT
                    )
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcv_location_history_list.apply {
            adapter = searchHistoryListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    fun setLocationSelectedListener(locationSelectionListener: LocationSelectionListener) {
        this@LocationHistoryFragment.locationSelectionListener = locationSelectionListener
    }
}
