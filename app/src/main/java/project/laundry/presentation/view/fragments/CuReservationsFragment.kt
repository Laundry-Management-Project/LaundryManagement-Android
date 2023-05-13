package project.laundry.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import project.laundry.data.App
import project.laundry.databinding.FragmentReservationsCuBinding
import project.laundry.presentation.view.CuReAdapter
import project.laundry.presentation.viewmodel.CuReservationsViewModel

class CuReservationsFragment : Fragment() {

    lateinit var binding : FragmentReservationsCuBinding
    val viewModel : CuReservationsViewModel = CuReservationsViewModel()

    val uid= App.prefs.uid!!
    val userType = App.prefs.userType!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentReservationsCuBinding.inflate(layoutInflater, container, false)
        initView()

        viewModel.loading.observe(viewLifecycleOwner){isLoading->
            if(isLoading){
                binding.progressBar.visibility= View.VISIBLE
            }else{
                binding.progressBar.visibility= View.GONE
            }
        }
        viewModel.reservations.observe(viewLifecycleOwner){reservations ->
            binding.reRecycler.adapter = CuReAdapter(requireActivity(), reservations)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.getReservations(uid,userType)
    }
    private fun initView(){

        binding.reRecycler.layoutManager = LinearLayoutManager(requireActivity())

    }
}