package project.laundry.presentation.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import project.laundry.databinding.FragmentStoresCuBinding
import project.laundry.presentation.view.CuStoresAdapter
import project.laundry.presentation.viewmodel.CuStoresViewModel

class CuStoresFragmnet : Fragment() {

    lateinit var binding : FragmentStoresCuBinding
    val viewModel : CuStoresViewModel = CuStoresViewModel()

    private var uid = ""
    private var userType = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStoresCuBinding.inflate(layoutInflater, container, false)

        getUserInfo()

        viewModel.getStores(userType, uid)

        binding.buRecycler.layoutManager = LinearLayoutManager(requireContext())

        viewModel.stores.observe(viewLifecycleOwner) { stores ->
            binding.buRecycler.adapter = CuStoresAdapter(requireActivity(), stores)
        }


        return binding.root
    }

    private fun getUserInfo(){
        val myPref = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE)
        myPref.getString("uid", "")?.let{
            uid = it
        }
        myPref.getString("userType", "")?.let{
            userType = it
        }
    }
}