package com.example.pecapp.screens.catchlog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.pecapp.R
import com.example.pecapp.database.PecAppDatabase
import com.example.pecapp.databinding.FragmentCatchLogBinding
import com.example.pecapp.network.WeatherApi
import com.example.pecapp.network.WeatherNetworkDataSource
import com.example.pecapp.repository.PecAppRepository
import java.text.SimpleDateFormat
import java.util.logging.SimpleFormatter

class FragmentCatchLog : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding: FragmentCatchLogBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_catch_log, container, false)

        val application = requireNotNull(this.activity).application

        val database = PecAppDatabase.getInstance(application).pecAppDatabaseDao

        val weatherNetwork = WeatherNetworkDataSource(WeatherApi.retrofitService)

        val repository = PecAppRepository(database, weatherNetwork)

        val catchLogViewModelFactory = CatchLogViewModelFactory(repository, application)

        val catchLogViewModel =
            ViewModelProviders.of(this, catchLogViewModelFactory).get(CatchLogViewModel::class.java)

        binding.catchLogViewModel = catchLogViewModel

        val adapter = CatchAdapter(CatchItemClickListener {
            date -> catchLogViewModel.onCatchItemClicked(date)
        })

        catchLogViewModel.navigateToCatchCertificate.observe(viewLifecycleOwner, Observer {
            it?.let {
                //TODO(we can do the navigation to the catch details)
                val format = SimpleDateFormat("yyyy.MM.dd")
                Toast.makeText(context, format.format(it), Toast.LENGTH_SHORT).show()
                catchLogViewModel.onNavigatedToCatchDetails()
            }
        })

        binding.catchLog.adapter = adapter

        catchLogViewModel.catches.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.lifecycleOwner = this

        return binding.root
    }

}
