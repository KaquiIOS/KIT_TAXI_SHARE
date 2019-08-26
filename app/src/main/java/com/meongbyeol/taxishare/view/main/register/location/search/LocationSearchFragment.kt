package com.meongbyeol.taxishare.view.main.register.location.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meongbyeol.taxishare.R
import com.meongbyeol.taxishare.data.model.Location
import com.meongbyeol.taxishare.view.main.register.location.LocationSelectionListener
import kotlinx.android.synthetic.main.fragment_location_search.*


class LocationSearchFragment : Fragment() {

    companion object {
        fun newInstance() =
            LocationSearchFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private val adapter: LocationSearchAdapter = LocationSearchAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcv_location_search_list.apply {
            adapter = this@LocationSearchFragment.adapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            itemAnimator = null
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
    }

    fun setLocationSearchList(searchList: MutableList<Location>) {
        adapter.setSearchResultList(searchList)
    }

    fun setLocationSelectedListener(locationSelectionListener: LocationSelectionListener) {
        adapter.setLocationSelectionListener(locationSelectionListener)
    }
}
