package com.example.pecapp.screens.newcatch.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.pecapp.R
import com.example.pecapp.database.PecAppDatabase
import com.example.pecapp.databinding.FragmentNewCatchWeatherBinding
import com.example.pecapp.network.WeatherApi
import com.example.pecapp.network.WeatherNetworkDataSource
import com.example.pecapp.repository.PecAppRepository
import com.example.pecapp.utils.Utils.Companion.isNotNull

class FragmentCatchWeather : Fragment() {

    private lateinit var binding: FragmentNewCatchWeatherBinding
    private lateinit var weatherViewModel: CatchWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_catch_weather, container,false)

        val application = activity!!.application
        val database = PecAppDatabase.getInstance(context!!).pecAppDatabaseDao
        val weatherNetwork = WeatherNetworkDataSource(WeatherApi.retrofitService)
        val repository = PecAppRepository(database, weatherNetwork)
        val weatherViewModelFactory = CatchWeatherViewModelFactory(repository, application)
        weatherViewModel = ViewModelProvider(this, weatherViewModelFactory).get(CatchWeatherViewModel::class.java)

        binding.weatherViewModel = weatherViewModel.apply {
            val args = FragmentCatchWeatherArgs.fromBundle(arguments!!)
            initialize(args.currentCatch)
        }
        binding.lifecycleOwner = this

        weatherViewModel.navigateToCatchCertificate.observe(viewLifecycleOwner, Observer {
            if (it.isNotNull()) {
                this.findNavController().navigate(FragmentCatchWeatherDirections.actionFragmentNewCatchDateAndLocationToFragmentCatchCertificate(it.id))
                weatherViewModel.navigationFinished()
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view!!.findViewById<Button>(R.id.button_weather_next).setOnClickListener {
            weatherViewModel.nextButtonPressed()
        }
    }
}
