package com.bilalberekgm.bluearchive.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bilalberekgm.bluearchive.databinding.ItemViewPagerBinding
import com.bilalberekgm.bluearchive.studentModel.Data
import com.bumptech.glide.Glide
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
@FragmentScoped
class ViewPagerAdapter @Inject constructor():RecyclerView.Adapter<ViewPagerAdapter.ViewPagerHolder>() {
    private lateinit var context: Context

    inner class ViewPagerHolder(private val binding: ItemViewPagerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(studentResponse: Data) {
            binding.apply {
                var namesText = ""
                var hobbies = ""

                name.text = studentResponse.name
                school.text = studentResponse.school
                background.text = studentResponse.background
                studentResponse.hobbies.forEach{
                    hobbies += it
                }
                studentHobbies.text = hobbies
                studentWeapon.text = studentResponse.weapon
                studentDamage.text = studentResponse.damageType

                namesText +=
                    studentResponse.names.firstName +
                        " " + studentResponse.names.lastName +
                        " " + studentResponse.names.japanName

                originalNames.text = namesText
                studentAge.text = studentResponse.age
                studentHeight.text = studentResponse.height


                Glide.with(context)
                    .load(studentResponse.photoUrl)
                    .into(characterImage)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewPagerAdapter.ViewPagerHolder {


        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemViewPagerBinding.inflate(inflater, parent, false)
        context = parent.context
        return  ViewPagerHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.ViewPagerHolder, position: Int) {
        val item = differ.currentList[position]
        item?.let {
            holder.bind(item)
        }

        holder.setIsRecyclable(false)
    }
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

    val differ  = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {
       return  differ.currentList.size
    }
}