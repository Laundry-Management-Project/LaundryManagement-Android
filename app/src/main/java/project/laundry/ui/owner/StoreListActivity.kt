package project.laundry.ui.owner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import project.laundry.R
import project.laundry.data.dataclass.LoginOwResponse
import project.laundry.data.dataclass.StoreListItems
import project.laundry.databinding.ActivityStoreListBinding

class StoreListActivity : AppCompatActivity() {
    lateinit var binding : ActivityStoreListBinding
    private val viewModel = StoreListViewModel()

    private val childForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                RESULT_OK -> {
                    val storeItem = result.data?.getSerializableExtra("storeItem") as StoreListItems?
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

        val intentLogin = intent
        val loginRes = intentLogin.getSerializableExtra("loginOw") as LoginOwResponse?
        Log.d("login", loginRes.toString())

        loginRes?.storeList?.let{
            viewModel.load(it)
        }

        binding.storeRecycler.layoutManager = LinearLayoutManager(this)

        viewModel.storeListItems.observe(this, Observer { item ->
            binding.storeRecycler.adapter = StoreListAdpater(this, item)
        })

        binding.btnAddStore.setOnClickListener {
            val intent = Intent(this, StoreRegisterActivity::class.java)
            childForResult.launch(intent)
        }

        setContentView(binding.root)
    }
}