package com.example.pecapp.screens.newcatch.location

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
import com.example.pecapp.databinding.FragmentNewCatchLocationBinding
import com.example.pecapp.utils.Utils.Companion.isNotNull

class FragmentCatchLocation : Fragment() {

    private lateinit var binding: FragmentNewCatchLocationBinding
    private lateinit var locationViewModel: CatchLocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_catch_location, container, false)

        val application = activity!!.application
        val locationViewModelFactory = CatchLocationViewModelFactory(application)
        locationViewModel = ViewModelProvider(this, locationViewModelFactory).get(CatchLocationViewModel::class.java)

        binding.locationViewModel = locationViewModel.apply {
            val args = FragmentCatchLocationArgs.fromBundle(arguments!!)
            initialize(args.currenetCatch)
        }

        binding.lifecycleOwner = this

        locationViewModel.navigateToCatchWeather.observe(viewLifecycleOwner, Observer {
            if (it.isNotNull()) {
                this.findNavController().navigate(FragmentCatchLocationDirections.actionFragmentNewCatchLocationToFragmentNewCatchWeather(it))
                locationViewModel.navigationFinished()
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view!!.findViewById<Button>(R.id.button_location_next).setOnClickListener {
            locationViewModel.nextButtonPressed()
        }
    }
}
