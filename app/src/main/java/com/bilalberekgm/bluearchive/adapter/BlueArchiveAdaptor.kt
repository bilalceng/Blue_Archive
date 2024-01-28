package com.bilalberekgm.bluearchive.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.paging.filter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bilalberekgm.bluearchive.databinding.RecyclerViewItemBinding
import com.bilalberekgm.bluearchive.model.CharacterResponse
import com.bilalberekgm.bluearchive.model.Data
import com.bilalberekgm.bluearchive.paging.BlueArchivePagingSource
import com.bumptech.glide.Glide
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import java.util.Locale
import javax.inject.Inject
@FragmentScoped
class BlueArchiveAdaptor @Inject constructor(): PagingDataAdapter<Data,BlueArchiveAdaptor.ViewHolder>(differCallback){
    private lateinit var onCharacterClickedListener: (Data) -> Unit
    private lateinit var binding: RecyclerViewItemBinding
    private lateinit var context: Context

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {
        fun bind(response:Data) {
            binding.apply {
                characterName.text = response.name
                Glide.with(context)
                    .load(response.photoUrl)
                    .into(characterPhoto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = RecyclerViewItemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(
                oldItem: Data,
                newItem: Data
            ): Boolean {
                return oldItem._id == newItem._id
            }
            override fun areContentsTheSame(
                oldItem: Data,
                newItem: Data
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(item)
        }

        holder.itemView.setOnClickListener {
            onCharacterClickedListener.let{
                Log.d("listonToclick","$item")
                it(item!!)
            }
        }
        holder.setIsRecyclable(false)
    }

    fun setOnCharacterClickListener(listener: (Data) -> Unit){
        onCharacterClickedListener = listener
    }

}