package com.bilalberekgm.bluearchive.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bilalberekgm.bluearchive.R
import com.bilalberekgm.bluearchive.adapter.BlueArchiveAdaptor
import com.bilalberekgm.bluearchive.databinding.FragmentBlueArchiveNameAndPhotoBinding
import com.bilalberekgm.bluearchive.ui.BlueArchiveActivity
import com.bilalberekgm.bluearchive.viewmodel.BlueArchiveViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class BlueArchiveNameAndPhotoFragment : Fragment() {
    private lateinit var bottomNav:BottomNavigationView
    private var isShowBottomNav:Boolean = true
    private lateinit var binding: FragmentBlueArchiveNameAndPhotoBinding
    private val viewModel: BlueArchiveViewModel by viewModels()
    @Inject
     lateinit var bluArchiveAdaptor: BlueArchiveAdaptor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBlueArchiveNameAndPhotoBinding.inflate(layoutInflater,container,false)
        bottomNav =  (activity as BlueArchiveActivity).bottomNav
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        handleFlowData()
        goToBlueArchiveDetailFragment()
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        handleTextInputs(searchView)

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun handleTextInputs(searchView: SearchView){
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                isShowBottomNav = newText == ""
                viewModel.onQueryChanged(newText!!)
                controlBottomNav(isShowBottomNav)
                return true
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleFlowData(){
        binding.apply {

                viewModel.filteredCharacterList.observe(viewLifecycleOwner){
                    lifecycleScope.launch {
                        if(it != null){
                            bluArchiveAdaptor.notifyDataSetChanged()
                            bluArchiveAdaptor.submitData(it)
                            bluArchiveAdaptor.notifyDataSetChanged()

                        }
                    }
                }
            setupRecyclerView()
        }
    }

    private fun setupRecyclerView(){
        binding.apply {
            rcView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = bluArchiveAdaptor
            }
        }
    }

    private fun controlBottomNav(isShowBottomNav: Boolean){
        bottomNav.isVisible = isShowBottomNav
    }

    private fun goToBlueArchiveDetailFragment(){
        bluArchiveAdaptor.setOnCharacterClickListener {
           val action = BlueArchiveNameAndPhotoFragmentDirections
               .actionBlueArchiveNameAndPhotoFragment2ToBlueArchiveDetailsFragment(it)
            findNavController().navigate(action)
        }
    }

}