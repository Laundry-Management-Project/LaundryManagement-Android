package project.laundry.presentation.owner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import project.laundry.data.dataclass.AddStoreDto
import project.laundry.databinding.ActivityStoreRegisterBinding
import project.laundry.presentation.view.LoginActivity

class StoreRegisterActivity : AppCompatActivity() {
    lateinit var binding : ActivityStoreRegisterBinding

    private val viewModel = StoreRegisterViewModel()
    lateinit var uid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreference = getSharedPreferences("User", MODE_PRIVATE)
        sharedPreference.getString("uid", "")?.let{
            uid = it
            if(uid == ""){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        binding = ActivityStoreRegisterBinding.inflate(layoutInflater)


        binding.btnRegister.setOnClickListener {
            val addStoreDto = AddStoreDto(
                binding.etStoreAddress.text.toString(),
                binding.etBuHours.text.toString(),
                binding.etStoreName.text.toString()
            )
            viewModel.addStore(uid, addStoreDto)
        }
        viewModel.storeList.observe(this, Observer { storeList ->
            if(storeList!=null){
                val returnIntent = Intent(this, StoreListActivity::class.java)
                returnIntent.putExtra("storeItem", storeList)
                returnIntent.putExtra("test", "test")
                setResult(RESULT_OK, returnIntent)
                finish()
            }
        })
        setContentView(binding.root)
    }
}