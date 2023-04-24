package project.laundry.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import project.laundry.databinding.FragmentReservationsCuBinding
import project.laundry.presentation.view.CuReAdapter
import project.laundry.presentation.viewmodel.CuReservationsViewModel

class CuReservationsFragment : Fragment() {

    lateinit var binding : FragmentReservationsCuBinding
    val viewModel : CuReservationsViewModel = CuReservationsViewModel()

    private var uid = ""
    private var userType = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        getUserInfo()

        binding = FragmentReservationsCuBinding.inflate(layoutInflater, container, false)
        initView()

        viewModel.getReservations(uid, userType)
        viewModel.reservations.observe(viewLifecycleOwner){reservations ->
            binding.reRecycler.adapter = CuReAdapter(requireActivity(), reservations)
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

    private fun initView(){

        binding.reRecycler.layoutManager = LinearLayoutManager(requireActivity())

    }
}