package project.laundry.presentation.view.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
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
            Log.d("addStore0",result.resultCode.toString())
        when (result.resultCode) {
            RESULT_OK -> {
                val intent = intent
                val newStore = intent.getSerializableExtra("store") as Store?
                Log.d("addStore0", newStore.toString())

                newStore?.let{
                    viewModel.addStoreItem(it)
                    Log.d("addStore1", newStore.toString())
                }
                Log.d("addStore2", newStore.toString())
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

        Log.d("userInfoActivity", uid!!)
        Log.d("userInfoActivity", userType!!)

        viewModel.loadStores(uid, userType)
        viewModel.loading.observe(this, Observer { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = VISIBLE
            } else {
                binding.progressBar.visibility = GONE
            }
        })

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

    override fun onResume() {
        super.onResume()
        viewModel.loadStores(uid, userType)
    }
}