package com.example.pecapp.screens.newcatch.method

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
import com.example.pecapp.databinding.FragmentNewCatchMethodBinding
import com.example.pecapp.utils.Utils.Companion.isNotNull

class FragmentCatchMethod : Fragment() {

    private lateinit var binding: FragmentNewCatchMethodBinding
    private lateinit var methodViewModel: CatchMethodViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_catch_method, container, false)

        val application = activity!!.application
        val methodViewModelFactory = CatchMethodViewModelFactory(application)
        methodViewModel = ViewModelProvider(this, methodViewModelFactory).get(CatchMethodViewModel::class.java)

        binding.methodViewModel = methodViewModel.apply {
            val args = FragmentCatchMethodArgs.fromBundle(arguments!!)
            initialize(args.currentCatch)
        }

        binding.lifecycleOwner = this

        methodViewModel.navigateToCatchLocation.observe(viewLifecycleOwner, Observer {
            if (it.isNotNull()) {
                this.findNavController().navigate(FragmentCatchMethodDirections.actionFragmentNewCatchMethodToFragmentNewCatchLocation(it))
                methodViewModel.navigationFinished()
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view!!.findViewById<Button>(R.id.button_method_next).setOnClickListener {
            methodViewModel.nextButtonPressed()
        }
    }
}
