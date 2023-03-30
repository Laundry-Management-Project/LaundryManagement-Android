package project.laundry.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import project.laundry.data.dataclass.ClientData
import project.laundry.databinding.FragmentClientListBinding
import project.laundry.ui.ClientRecyclerAdapter
import project.laundry.ui.LaundryRegisterActivity

class ClientListFragment : Fragment() {

    lateinit var binding : FragmentClientListBinding
    lateinit var viewModel : ClientViewModel

    private val activityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when(result.resultCode) {
                RESULT_OK -> {
                    val crd = result.data?.getSerializableExtra("client_register_data") as ClientData
                    Log.d("crd", crd.toString())
                    viewModel.addClient(crd)
                }
            }
        }

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
            binding.clientRecycler.adapter = ClientRecyclerAdapter(requireContext(),clients)
        }

        binding.extendedFab.setOnClickListener {
            Log.d("buttonclick", "success")
            val intent = Intent(requireActivity(), LaundryRegisterActivity::class.java)
            activityForResult.launch(intent)
        }

        viewModel.loadClients()


        return binding.root
    }

    companion object {
    }
}