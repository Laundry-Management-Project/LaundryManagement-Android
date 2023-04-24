package project.laundry.presentation.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import project.laundry.databinding.FragmentReservationsOwBinding
import project.laundry.presentation.view.OwReAdapter
import project.laundry.presentation.viewmodel.OwReservationsViewModel

class OwReservationsFragment : Fragment() {

    lateinit var binding : FragmentReservationsOwBinding
    lateinit var viewModel : OwReservationsViewModel

    var uid = ""
    var buId = ""
    var userType = ""
    override fun onAttach(context: Context) {
        viewModel = OwReservationsViewModel(requireActivity().application)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        getUserInfo()
        arguments?.getString("bu_id")?.let{
            buId = it
        }

        binding = FragmentReservationsOwBinding.inflate(layoutInflater, container, false)

        binding.reservationsRecycler.layoutManager=LinearLayoutManager(requireContext())

        viewModel.loadReservations(userType, buId)

        viewModel.reservations.observe(viewLifecycleOwner) { reservations ->
            binding.reservationsRecycler.adapter = OwReAdapter(requireContext(),reservations)
        }

        return binding.root
    }
    private fun getUserInfo(){
        val myPref = requireActivity().getSharedPreferences("User", AppCompatActivity.MODE_PRIVATE)
        myPref.getString("uid", "")?.let{
            uid = it
        }
        myPref.getString("userType", "")?.let{
            userType = it
        }
    }

}