package project.laundry.presentation.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import project.laundry.data.dataclass.Store
import project.laundry.data.dataclass.Stores
import project.laundry.databinding.FragmentStoresBinding
import project.laundry.presentation.view.BuRecyclerAdapter
import project.laundry.presentation.viewmodel.StoresViewModel

class StoresFragmnet : Fragment() {

    lateinit var binding : FragmentStoresBinding
    val viewModel : StoresViewModel = StoresViewModel()

    private var uid = ""
    private var userType = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStoresBinding.inflate(layoutInflater, container, false)

        val myPref = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE)
        myPref.getString("uid", "")?.let{
            uid = it
        }
        myPref.getString("userType", "")?.let{
            userType = it
        }
        viewModel.getStores(userType, uid)

        binding.buRecycler.layoutManager = LinearLayoutManager(requireContext())

        viewModel.stores.observe(viewLifecycleOwner) { stores ->
            binding.buRecycler.adapter = BuRecyclerAdapter(requireActivity(), stores)
        }


        return binding.root
    }

    companion object {
    }
}