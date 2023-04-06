package project.laundry.ui.customer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import project.laundry.data.dataclass.LoginCuResponse
import project.laundry.data.dataclass.StoreListItems
import project.laundry.databinding.FragmentMapBinding
import project.laundry.ui.customer.BuRecyclerAdapter

class MapFragment : Fragment() {

    lateinit var binding : FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMapBinding.inflate(layoutInflater, container, false)

        val items : ArrayList<StoreListItems> = ArrayList()

        val loginRes = arguments?.getSerializable("storeList") as LoginCuResponse?
        if(loginRes?.storeList != null) {
            for (i in loginRes.storeList) {
                items.add(StoreListItems(i.storeAddress, i.buHours, i.sid, i.storeName))
            }
        }
        binding.buRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.buRecycler.adapter = BuRecyclerAdapter(requireActivity(), items)

        return binding.root
    }

    companion object {
    }
}