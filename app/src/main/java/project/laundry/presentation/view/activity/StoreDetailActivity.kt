package project.laundry.presentation.view.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import project.laundry.data.dataclass.AddReservation
import project.laundry.databinding.ActivityStoreDetailBinding
import project.laundry.presentation.viewmodel.StoreDetailViewModel
import java.io.Serializable

class StoreDetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityStoreDetailBinding
    private val viewModel = StoreDetailViewModel()
    var uid = ""
    var buId = ""
    lateinit var userType : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getUserInfo()

        val intent = intent
        intent.getStringExtra("bu_id")?.let{
            buId = it
        }

        initView()
        if(userType == "OW"){
            //수정할 수 있는 버튼 및 화면 구성
        }

        viewModel.loadDetail(userType, buId)
        viewModel.store.observe(this){store ->
            binding.tvStoreName.text = store.name
            binding.tvBuHours.text = store.bu_hour
            binding.tvContent.text = store.intro
            binding.tvAddress.text = store.address
            binding.tvContact.text = store.contact
        }

        setContentView(binding.root)

        fun <T: Serializable> Intent.intentSerializable(key: String, clazz: Class<T>): T? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                this.getSerializableExtra(key, clazz)
            } else {
                this.getSerializableExtra(key) as T?
            }
        }

    }
    private fun getUserInfo(){
        val myPref = getSharedPreferences("User", MODE_PRIVATE)
        myPref.getString("uid", "")?.let{
            uid = it
        }
        myPref.getString("userType", "")?.let{
            userType=it
        }

    }
    private fun initView(){
        binding = ActivityStoreDetailBinding.inflate(layoutInflater)
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.btnRegister.setOnClickListener {
            Log.d("buttonclick", "success")
            val intent = Intent(this, AddReservationActivity::class.java)
            intent.putExtra("bu_id", buId)
            startActivity(intent)
        }

    }
}