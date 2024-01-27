package com.bilalberekgm.bluearchive.ui.fragments

import android.content.Context
import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bilalberekgm.bluearchive.R
import com.bilalberekgm.bluearchive.databinding.FragmentBlueArchiveDetailsBinding
import com.bilalberekgm.bluearchive.databinding.FragmentBlueArchiveStudentsPageBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlueArchiveDetailsFragment : Fragment() {
    private val args: BlueArchiveDetailsFragmentArgs by navArgs<BlueArchiveDetailsFragmentArgs>()
    private lateinit var binding: FragmentBlueArchiveDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBlueArchiveDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        matchDataFromNamesAndPhotoFragment()
        setupActionBar()

    }

    private fun matchDataFromNamesAndPhotoFragment() {
        binding.apply {
            val birthday = birthday
            val damageType = damage
            val school = school
            val name = name
            val photo = characterImage
            args.apply {
                birthday.text = characterData.birthday
                damageType.text = characterData.damageType
                school.text = characterData.school
                name.text = characterData.name
                val context = activity as Context

                Glide.with(context)
                    .load(characterData.photoUrl)
                    .into(photo)
            }
        }
    }

    private fun setupActionBar() {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("controlComeback","$item.itemId")
        return when (item.itemId) {

            android.R.id.home -> {
                findNavController().navigateUp()
                Log.d("controlComeback","$item.itemId")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
