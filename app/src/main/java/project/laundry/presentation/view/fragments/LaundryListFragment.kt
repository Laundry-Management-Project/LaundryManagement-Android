package project.laundry.presentation.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import project.laundry.databinding.FragmentClientListBinding
import project.laundry.presentation.view.LaundryListRecyclerAdapter
import project.laundry.presentation.viewmodel.ClientViewModel

class LaundryListFragment : Fragment() {

    lateinit var binding : FragmentClientListBinding
    lateinit var viewModel : ClientViewModel

    override fun onAttach(context: Context) {
        viewModel = ClientViewModel(requireActivity().application)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentClientListBinding.inflate(layoutInflater, container, false)

        binding.clientRecycler.layoutManager=LinearLayoutManager(requireContext())

        viewModel.clients.observe(viewLifecycleOwner) { clients ->
            binding.clientRecycler.adapter = LaundryListRecyclerAdapter(requireContext(),clients)
        }

        viewModel.loadClients()


        return binding.root
    }

    companion object {
    }
}