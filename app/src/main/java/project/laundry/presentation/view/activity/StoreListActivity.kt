package project.laundry.presentation.view.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import project.laundry.data.dataclass.Store
import project.laundry.databinding.ActivityStoreListBinding
import project.laundry.presentation.view.StoreListAdpater
import project.laundry.presentation.viewmodel.StoreListViewModel

class StoreListActivity : AppCompatActivity() {
    lateinit var binding : ActivityStoreListBinding
    private val viewModel = StoreListViewModel()
    private var uid = ""
    private var userType = ""
    private val childForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                RESULT_OK -> {
                    val storeItem = result.data?.getSerializableExtra("storeItem") as Store?
                    val test = result.data?.getStringExtra("test")
                    storeItem?.let { viewModel.addStoreItem(it) }
                    Log.d("returnStore", storeItem.toString())
                    Log.d("returnStore", test.toString())
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStoreListBinding.inflate(layoutInflater)


        val myPref = getSharedPreferences("User", MODE_PRIVATE)
        myPref.getString("uid", "")?.let{
            uid = it
        }
        myPref.getString("userType", "")?.let{
            userType = it
        }

        viewModel.load(uid, userType)

        binding.storeRecycler.layoutManager = LinearLayoutManager(this)

        viewModel.stores.observe(this, Observer { item ->
            binding.storeRecycler.adapter = StoreListAdpater(this, item)
        })

        binding.btnAddStore.setOnClickListener {
            val intent = Intent(this, StoreRegisterActivity::class.java)
            childForResult.launch(intent)
        }

        setContentView(binding.root)


    }
}