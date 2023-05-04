package project.laundry.presentation.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import project.laundry.data.App
import project.laundry.databinding.FragmentStoresCuBinding
import project.laundry.presentation.view.CuStoresAdapter
import project.laundry.presentation.viewmodel.CuStoresViewModel

class CuStoresFragmnet : Fragment() {

    lateinit var binding : FragmentStoresCuBinding
    val viewModel : CuStoresViewModel = CuStoresViewModel()

    val uid= App.prefs.uid!!
    val userType = App.prefs.userType!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStoresCuBinding.inflate(layoutInflater, container, false)

        viewModel.getStores(userType, uid)

        binding.buRecycler.layoutManager = LinearLayoutManager(requireContext())

        viewModel.stores.observe(viewLifecycleOwner) { stores ->
            binding.buRecycler.adapter = CuStoresAdapter(requireActivity(), stores)
        }


        return binding.root
    }
}