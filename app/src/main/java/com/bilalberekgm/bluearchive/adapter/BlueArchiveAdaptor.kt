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
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class BlueArchiveAdaptor (
    var coroutineScope:CoroutineScope,
    var lifecycle: Lifecycle
): PagingDataAdapter<Data,BlueArchiveAdaptor.ViewHolder>(differCallback) {
    //private val dataFlow = MutableStateFlow<PagingData<Data>?>(null)
    private lateinit var onCharacterClickedListener: (Data) -> Unit
    private lateinit var binding: RecyclerViewItemBinding
    private lateinit var context: Context
    private var originalData: PagingData<Data>? = null



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

    suspend fun setOriginalData(data: PagingData<Data>) {
        Log.d("controlRecyclerView", "submitData  originalData icin   cagrıldı1")
        originalData = data
        submitData(originalData!!)
    }

    fun setOnCharacterClickListener(listener: (Data) -> Unit){
        onCharacterClickedListener = listener
    }

    /**
     *
     *
     *
     *     fun takeQueryText(text: String?) {
     *         if (text.isNullOrEmpty()) {
     *             Log.d("controlRecyclerView", "submitData originalData için çağrıldı2")
     *
     *         } else {
     *             // Filter data and update
     *             dataFlow.value = originalData?.filter { data ->
     *                 data.name.lowercase(Locale.ROOT).contains(text.lowercase(Locale.ROOT))
     *             }
     *         }
     *     }
     *
     *     init {
     *         // Observe dataFlow and submit the data when it changes
     *         lifecycle.addObserver(object : LifecycleObserver {
     *             @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
     *             fun onCreate() {
     *                 coroutineScope.launch {
     *                     dataFlow.collect { data ->
     *                         data?.let { submitData(lifecycle,it) }
     *                     }
     *                 }
     *             }
     *         })
     *
     *     }
     *
     *
     *
     *
     */

}