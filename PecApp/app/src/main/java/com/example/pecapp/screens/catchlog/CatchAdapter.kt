package com.example.pecapp.screens.catchlog

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pecapp.database.data.Catch
import com.example.pecapp.databinding.CatchItemBinding
import java.text.SimpleDateFormat
import java.util.*

class CatchAdapter(private val clickListener: CatchItemClickListener) : ListAdapter<Catch, CatchAdapter.ViewHolder>(
    CatchDiffCallback()
) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: CatchItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Catch, clickListener: CatchItemClickListener) {
            binding.catchItem = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CatchItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class CatchDiffCallback : DiffUtil.ItemCallback<Catch>() {
    override fun areItemsTheSame(oldItem: Catch, newItem: Catch): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Catch, newItem: Catch): Boolean {
        return oldItem == newItem
    }
}

class CatchItemClickListener(val clickListener: (date: Long) -> Unit) {
    fun onClick(catch: Catch) = clickListener(catch.date)
}

@BindingAdapter("setCatchType")
fun TextView.setCatchType(catch: Catch?) {
    catch?.let {
        text = catch.type
    }
}

@BindingAdapter("setCatchWeight")
fun TextView.setCatchWeight(catch: Catch?) {
    catch?.let {
        text = "${catch.weight.toString()} kg"
    }
}

@BindingAdapter("setCatchLocation")
fun TextView.setCatchLocation(catch: Catch?) {
    catch?.let {
        text = catch.location
    }
}

@BindingAdapter("setCatchPicture")
fun ImageView.setCatchPicture(catch: Catch?) {
    catch?.let {
        setImageURI(Uri.parse(catch.picture))
    }
}

@BindingAdapter("setCatchDate")
fun TextView.setCatchDate(catch: Catch?) {
    catch?.let {
        val format = SimpleDateFormat("yyyy.MM.dd")
        val date = Date(catch.date)
        text = format.format(date)
    }
}
