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
import com.example.taxishare.data.local.room.AppDatabase
import com.example.taxishare.data.mapper.TypeMapper
import com.example.taxishare.data.model.Location
import com.example.taxishare.data.repo.LocationRepositoryImpl
import com.example.taxishare.view.main.register.location.LocationLongClickListener
import com.example.taxishare.view.main.register.location.LocationSearchActivity
import com.example.taxishare.view.main.register.location.LocationSelectionListener
import com.google.android.gms.maps.MapView
import kotlinx.android.synthetic.main.fragment_location_history.*

class LocationHistoryFragment : Fragment(), LocationHistoryView {

    companion object {
        fun newInstance() =
            LocationHistoryFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private lateinit var locationSelectionListener: LocationSelectionListener
    private lateinit var locationSelectionLongClickListener: LocationLongClickListener

    private lateinit var presenter: LocationHistoryPresenter

    private lateinit var searchHistoryListAdapter: LocationHistoryAdapter

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

        initPresenter()
        initAdapter()
        initView()
        presenter.loadSearchLocationHistory()
    }

    override fun setSearchHistoryList(historyList: MutableList<Location>) {
        searchHistoryListAdapter.setSearchHistoryList(historyList)
    }

    private fun initPresenter() {
        presenter = LocationHistoryPresenter(
            this,
            LocationRepositoryImpl.getInstance(AppDatabase.getInstance(context!!), TypeMapper)
        )
    }

    private fun initView() {
        rcv_location_history_list.apply {
            adapter = searchHistoryListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun initAdapter() {
        searchHistoryListAdapter = LocationHistoryAdapter(
            animation = AnimationUtils.loadAnimation(context, R.anim.slide_down),
            mapView = MapView(context).apply {
                layoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        Constant.SEARCH_HISTORY_MAP_HEIGHT
                    )
            }).apply {
            setOnSelectionListener(locationSelectionListener)
            setOnLocationLongClickListener(locationSelectionLongClickListener)
        }
    }

    fun setLocationSelectedListener(locationSelectionListener: LocationSelectionListener) {
        this@LocationHistoryFragment.locationSelectionListener = locationSelectionListener
    }

    fun setLocationItemLongClickListener(locationLongClickListener: LocationLongClickListener) {
        this@LocationHistoryFragment.locationSelectionLongClickListener = locationLongClickListener
    }
}
