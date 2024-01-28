package com.bilalberekgm.bluearchive.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bilalberekgm.bluearchive.R
import com.bilalberekgm.bluearchive.adapter.ViewPagerAdapter
import com.bilalberekgm.bluearchive.databinding.FragmentBlueArchiveStudentsPageBinding
import com.bilalberekgm.bluearchive.util.Resource
import com.bilalberekgm.bluearchive.viewmodel.BlueArchiveViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class BlueArchiveStudentsPageFragment : Fragment() {
    private lateinit var binding: FragmentBlueArchiveStudentsPageBinding
    private val viewModel:BlueArchiveViewModel by viewModels()
    @Inject
     lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBlueArchiveStudentsPageBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setUpAdapterAndIndicator()
        fetchStudentsDataAndObserve()
        setupActionBar()

    }

    private fun setUpAdapterAndIndicator() {
        binding.apply {
            viewPager2.adapter = viewPagerAdapter
            viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            circleIndicator3.setViewPager(binding.viewPager2)
            circleIndicator3.createIndicators(5, 1)
            viewPagerAdapter.registerAdapterDataObserver(circleIndicator3.adapterDataObserver);
        }
    }

    private fun fetchStudentsDataAndObserve(){
        viewModel.fetchStudents()
        viewModel.liveData.observe(viewLifecycleOwner, Observer { studentResponse ->
           when(studentResponse){
               is Resource.Success -> {
                   hideProgress()
                   studentResponse.data?.let { response ->
                       viewPagerAdapter.differ.submitList(response.data)
                   }
               }
               is Resource.Error -> {
                   hideProgress()
                   Toast.makeText(activity, "${studentResponse.message}", Toast.LENGTH_SHORT).show()
               }

               is Resource.Loading ->{
                   showProgress()
               }
           }

        })
    }
    private fun setupActionBar() {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun disableUserInteraction() {
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
    }

    private fun enableUserInteraction() {
        activity?.window?.clearFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun showProgress() {
        binding.progressbar.visibility = ProgressBar.VISIBLE
        disableUserInteraction()
    }

    private fun hideProgress() {
        binding.progressbar.visibility = ProgressBar.GONE
        enableUserInteraction()
    }
}