package com.example.pecapp.screens.catchcertificate

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.pecapp.R
import kotlinx.android.synthetic.main.fragment_catch_certificate.*

class FragmentCatchCertificate : Fragment() {
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_catch_certificate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var args =
            FragmentCatchCertificateArgs.fromBundle(arguments!!)
        text_congratulation.text = args.id.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_new_catch, menu)

        if (null == getShareIntent().resolveActivity(activity!!.packageManager)) {
            menu.findItem(R.id.menu_item_share)?.setVisible(false)
        }
    }



    private fun getShareIntent(): Intent {
        var args =
            FragmentCatchCertificateArgs.fromBundle(arguments!!)
        return ShareCompat.IntentBuilder.from(activity!!)
            .setText(args.id.toString())
            .setType("text/plain")
            .intent
    }

    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }
}
