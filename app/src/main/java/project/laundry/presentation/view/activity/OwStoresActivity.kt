package project.laundry.presentation.view.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import project.laundry.data.dataclass.Store
import project.laundry.databinding.ActivityStoresOwBinding
import project.laundry.presentation.view.OwStoresAdapter
import project.laundry.presentation.viewmodel.OwStoresViewModel

class OwStoresActivity : AppCompatActivity() {
    lateinit var binding : ActivityStoresOwBinding
    private val viewModel = OwStoresViewModel()
    private var uid = ""
    private var userType = ""

    private val startActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        when (result.resultCode) {
            RESULT_OK -> {
                val intent = intent
                val newStore = intent.getSerializableExtra("store") as Store?
                newStore?.let{
                    viewModel.addStoreItem(it)
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getUserInfo()
        initView()

        viewModel.loadStores(uid, userType)

        viewModel.stores.observe(this, Observer { item ->
            binding.storeRecycler.adapter = OwStoresAdapter(this, item)
        })

        setContentView(binding.root)
    }
    private fun getUserInfo(){
        val myPref = getSharedPreferences("User", MODE_PRIVATE)
        myPref.getString("uid", "")?.let{
            uid = it
        }
        myPref.getString("userType", "")?.let{
            userType = it
        }
    }
    private fun initView(){
        binding = ActivityStoresOwBinding.inflate(layoutInflater)
        binding.storeRecycler.layoutManager = LinearLayoutManager(this)
        binding.btnAddStore.setOnClickListener {
            val intent = Intent(this, OwStoreRegisterActivity::class.java)
            startActivityForResult.launch(intent)
        }
    }
}