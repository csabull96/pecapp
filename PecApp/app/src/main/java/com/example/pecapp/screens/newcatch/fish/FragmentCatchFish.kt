package com.example.pecapp.screens.newcatch.fish

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pecapp.R
import com.example.pecapp.databinding.FragmentNewCatchFishBinding
import com.example.pecapp.utils.Utils.Companion.isNotNull
import java.io.File

private const val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 19
private const val ACTION_PICK_INTENT_CODE = 11

class FragmentNewCatchFish : Fragment() {

    private lateinit var binding: FragmentNewCatchFishBinding
    private lateinit var fishViewModel: CatchFishViewModel
    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_new_catch_fish, container, false)

        val application = activity!!.application
        val fishViewModelFactory = CatchFishViewModelFactory(application)
        fishViewModel =
            ViewModelProvider(this, fishViewModelFactory).get(CatchFishViewModel::class.java)

        binding.fishViewModel = fishViewModel.apply {
            val args = FragmentNewCatchFishArgs.fromBundle(arguments!!)
            initialize(args.currentCatch)
        }

        binding.lifecycleOwner = this

        fishViewModel.navigateToCatchMethod.observe(viewLifecycleOwner, Observer {
            if (it.isNotNull()) {
                this.findNavController().navigate(
                    FragmentNewCatchFishDirections.actionFragmentNewCatchFishToFragmentNewCatchMethod(it)
                )
                fishViewModel.navigationFinished()
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view!!.findViewById(R.id.new_catch_picture)
        imageView.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(
                        activity!!,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions(
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        WRITE_EXTERNAL_STORAGE_REQUEST_CODE
                    )
                } else {
                    chosePicture()
                }
            } else {
                chosePicture()
            }
        }

        view!!.findViewById<Button>(R.id.button_fish_next).setOnClickListener {
            fishViewModel.nextButtonPressed(imageView.drawable)
        }

        fishViewModel.picture.observe(viewLifecycleOwner, Observer {
            val picture = File(it)
            if (picture.exists()) {
                val uri = Uri.fromFile(picture)
                imageView.setImageURI(uri)
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chosePicture()
            } else {
                Toast.makeText(this.context, "External storage access denied.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun chosePicture() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, ACTION_PICK_INTENT_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ACTION_PICK_INTENT_CODE && data != null) {
            val contentURI = data.data
            imageView.setImageURI(contentURI)
            fishViewModel.imageSetByUser = true
        }
    }
}
