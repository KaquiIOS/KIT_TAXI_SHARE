package com.example.taxishare.view.main.taxisharelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taxishare.R
import com.example.taxishare.data.remote.apis.server.ServerClient
import com.example.taxishare.data.remote.apis.server.request.TaxiShareListGetRequest
import com.example.taxishare.data.repo.ServerRepositoryImpl

class TaxiShareListFragment : Fragment() {

    companion object {
        fun newInstance() =
            TaxiShareListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_taxi_share_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val disposable = ServerRepositoryImpl
            .getInstance(ServerClient.getInstance())
            .getTaxiShareList(TaxiShareListGetRequest(0))
            .subscribe({
                Log.d("TEST", it.toString())
            }, {
                it.printStackTrace()
            })
    }
}
