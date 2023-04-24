package project.laundry.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import project.laundry.R
import project.laundry.databinding.FragmentReservationsBinding
import project.laundry.presentation.view.ReRecyclerAdapter
import project.laundry.presentation.viewmodel.ReservationsViewModel

class ReservationsFragment : Fragment() {

    lateinit var binding : FragmentReservationsBinding
    val viewModel : ReservationsViewModel = ReservationsViewModel()

    private var uid = ""
    private var userType = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentReservationsBinding.inflate(layoutInflater, container, false)


        val myPref = requireActivity().getSharedPreferences("User", AppCompatActivity.MODE_PRIVATE)
        myPref.getString("uid", "")?.let{
            uid = it
        }
        myPref.getString("userType", "")?.let{
            userType = it
        }

        viewModel.getReservations(uid, userType)

        binding.reRecycler.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.reservations.observe(viewLifecycleOwner){reservations ->
            binding.reRecycler.adapter = ReRecyclerAdapter(requireActivity(), reservations)
        }


        return binding.root
    }


}