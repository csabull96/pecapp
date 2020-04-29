package com.example.pecapp.screens.newcatch.date

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pecapp.R
import com.example.pecapp.databinding.FragmentNewCatchDateBinding
import com.example.pecapp.utils.Utils.Companion.isNotNull
import java.util.*

class FragmentCatchDate : Fragment() {

    private lateinit var binding: FragmentNewCatchDateBinding
    private lateinit var dateViewModel: CatchDateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_catch_date, container, false)

        val application = activity!!.application
        val dateViewModelFactory = CatchDateViewModelFactory(application)
        dateViewModel = ViewModelProvider(this, dateViewModelFactory).get(CatchDateViewModel::class.java)

        binding.dateViewModel = dateViewModel.apply {
            val args = FragmentCatchDateArgs.fromBundle(arguments!!)
            initialize(args.currentCatch)
        }

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendarView = view!!.findViewById<CalendarView>(R.id.calendar_view)
        dateViewModel.lastCatchDate.observe(viewLifecycleOwner, Observer {
            calendarView.date = it
        })

        calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            val calendar = GregorianCalendar(year, month, dayOfMonth)
            dateViewModel.lastCatchDate.postValue(calendar.timeInMillis)
        }

        view!!.findViewById<Button>(R.id.button_date_next).setOnClickListener {
            dateViewModel.nextButtonPressed()
        }

        dateViewModel.navigateToCatchFish.observe(viewLifecycleOwner, Observer {
            if (it.isNotNull()) {
                this.findNavController().navigate(FragmentCatchDateDirections.actionFragmentNewCatchDateToFragmentNewCatchFish(it))
               dateViewModel.navigationToCatchFishDone()
            }
        })
    }
}
