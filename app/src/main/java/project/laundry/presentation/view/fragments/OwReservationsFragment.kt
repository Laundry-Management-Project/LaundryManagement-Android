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
import project.laundry.data.App
import project.laundry.databinding.FragmentReservationsOwBinding
import project.laundry.presentation.view.OwReAdapter
import project.laundry.presentation.viewmodel.OwReservationsViewModel

class OwReservationsFragment : Fragment() {

    lateinit var binding : FragmentReservationsOwBinding
    lateinit var viewModel : OwReservationsViewModel

    val uid = App.prefs.uid!!
    var buId = App.prefs.buId!!
    var userType = App.prefs.userType!!
    override fun onAttach(context: Context) {
        viewModel = OwReservationsViewModel(requireActivity().application)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentReservationsOwBinding.inflate(layoutInflater, container, false)

        binding.reservationsRecycler.layoutManager=LinearLayoutManager(requireContext())

        viewModel.loadReservations(userType, buId)

        viewModel.reservations.observe(viewLifecycleOwner) { reservations ->
            binding.reservationsRecycler.adapter = OwReAdapter(requireContext(),reservations)
        }

        return binding.root
    }


}