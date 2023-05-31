package project.laundry.presentation.view.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import project.laundry.R
import project.laundry.data.App
import project.laundry.data.dataclass.AddStore
import project.laundry.databinding.FragmentHomeOwBinding
import project.laundry.presentation.view.StoreImagePagerAdapter
import project.laundry.presentation.viewmodel.OwHomeViewModel
import project.laundry.presentation.viewmodel.StoreRegisterViewModel

class OwHomeFragment : Fragment() {

    lateinit var binding : FragmentHomeOwBinding

    val uId = App.prefs.uid!!
    val buId = App.prefs.buId!!
    val userType = App.prefs.userType!!


    lateinit var viewModel : OwHomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeOwBinding.inflate(layoutInflater, container, false)

        val viewModelProvider = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))
        viewModel = viewModelProvider[OwHomeViewModel::class.java]


        viewModel.loadDetail(userType, buId)

        viewModel.bitmaps.observe(viewLifecycleOwner){bitmaps ->
            if(bitmaps.isNotEmpty()){
                binding.viewPager.adapter = StoreImagePagerAdapter(requireActivity(), bitmaps)
            }
        }
        viewModel.loading.observe(viewLifecycleOwner){isLoading->
            if(isLoading){
                binding.progressBar.visibility= VISIBLE
            }else{
                binding.progressBar.visibility= GONE
            }
        }
        viewModel.store.observe(viewLifecycleOwner){store ->
            binding.tvStoreName.text = store.name
            binding.tvBuHours.text = store.bu_hour
            binding.tvIntro.text = store.intro
            binding.tvAddress.text = store.address
            binding.tvContact.text = store.contact


            binding.etBuHours.text = Editable.Factory.getInstance().newEditable(store.bu_hour)
            binding.etIntro.text = Editable.Factory.getInstance().newEditable(store.intro)
            binding.etAddress.text = Editable.Factory.getInstance().newEditable(store.address)
            binding.etContact.text = Editable.Factory.getInstance().newEditable(store.contact)
        }

        binding.btnEdit.setOnClickListener {
            if(binding.btnEdit.text == "수정하기"){
                binding.tvBuHours.visibility = INVISIBLE
                binding.tvIntro.visibility = INVISIBLE
                binding.tvAddress.visibility = INVISIBLE
                binding.tvContact.visibility = INVISIBLE

                binding.etBuHours.visibility = VISIBLE
                binding.etIntro.visibility = VISIBLE
                binding.etAddress.visibility = VISIBLE
                binding.etContact.visibility = VISIBLE

                binding.btnEdit.text = "등록"
            } else{
                val updateStore = AddStore(
                    binding.etAddress.text.toString(),
                    binding.etBuHours.text.toString(),
                    binding.tvStoreName.text.toString(),
                    binding.etContact.text.toString(),
                    binding.etIntro.text.toString()
                )
                viewModel.updateDetail(buId, uId, updateStore)

                binding.tvBuHours.visibility = VISIBLE
                binding.tvIntro.visibility = VISIBLE
                binding.tvAddress.visibility = VISIBLE
                binding.tvContact.visibility = VISIBLE

                binding.etBuHours.visibility = INVISIBLE
                binding.etIntro.visibility = INVISIBLE
                binding.etAddress.visibility = INVISIBLE
                binding.etContact.visibility = INVISIBLE

                binding.btnEdit.text = "수정하기"
            }

        }

        return binding.root
    }

    companion object {
    }
}