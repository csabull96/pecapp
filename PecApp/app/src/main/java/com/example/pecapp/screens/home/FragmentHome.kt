package com.example.pecapp.screens.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pecapp.R
import com.example.pecapp.database.PecAppDatabase
import com.example.pecapp.databinding.FragmentHomeBinding
import com.example.pecapp.network.WeatherApi
import com.example.pecapp.network.WeatherNetworkDataSource
import com.example.pecapp.repository.PecAppRepository
import com.example.pecapp.utils.Utils.Companion.isNotNull

private const val LOCATION_PERMISSION_REQUEST_CODE = 12

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        val application = activity!!.application
        val database = PecAppDatabase.getInstance(context!!).pecAppDatabaseDao
        val weatherNetwork = WeatherNetworkDataSource(WeatherApi.retrofitService)
        val repository = PecAppRepository(database, weatherNetwork)
        val homeViewModelFactory = HomeViewModelFactory(repository, application)
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)

        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            homeViewModel.getWeatherData()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }

        binding.homeViewModel = homeViewModel
        binding.lifecycleOwner = this

        homeViewModel.navigateToCatchDate.observe(viewLifecycleOwner, Observer {
            if (it.isNotNull()) {
                this.findNavController()
                    .navigate(FragmentHomeDirections.actionFragmentHomeToFragmentNewCatchDate(it))
                homeViewModel.navigationFinished()
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addCatchButton = view!!.findViewById<Button>(R.id.button_new_catch)
        addCatchButton.setOnClickListener {
            homeViewModel.addButtonPressedShort()
        }
        addCatchButton.setOnLongClickListener {
            homeViewModel.addButtonPressedLong()
            Toast.makeText(context!!, "A new catch has been added.", Toast.LENGTH_LONG).show()
            true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                homeViewModel.getWeatherData()
            } else {
                Toast.makeText(
                        this.context,
                        "Please enable location services manually.",
                        Toast.LENGTH_LONG
                    )
                    .show()
            }
        }
    }
}