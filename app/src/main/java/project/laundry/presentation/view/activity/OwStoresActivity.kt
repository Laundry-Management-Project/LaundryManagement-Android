package project.laundry.presentation.view.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import project.laundry.data.App
import project.laundry.data.dataclass.Store
import project.laundry.databinding.ActivityStoresOwBinding
import project.laundry.presentation.view.OwStoresAdapter
import project.laundry.presentation.viewmodel.OwStoresViewModel

class OwStoresActivity : AppCompatActivity() {
    lateinit var binding : ActivityStoresOwBinding
    private val viewModel = OwStoresViewModel()

    val uid = App.prefs.uid!!
    val userType = App.prefs.userType!!

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

        initView()
        Log.d("userInfoActivity", uid!!)
        Log.d("userInfoActivity", userType!!)

        viewModel.loadStores(uid, userType)

        viewModel.stores.observe(this, Observer { item ->
            binding.storeRecycler.adapter = OwStoresAdapter(this, item)
        })

        setContentView(binding.root)
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