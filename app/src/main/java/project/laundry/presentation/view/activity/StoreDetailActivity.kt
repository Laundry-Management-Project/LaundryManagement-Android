package project.laundry.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import project.laundry.data.dataclass.AddReservation
import project.laundry.databinding.ActivityStoreDetailBinding
import project.laundry.presentation.viewmodel.StoreDetailViewModel

class StoreDetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityStoreDetailBinding
    private val viewModel = StoreDetailViewModel()
    lateinit var uid :String
    lateinit var sid : String
    private val activityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when(result.resultCode) {
                RESULT_OK -> {
                    val rd = result.data?.getSerializableExtra("reservation") as AddReservation
                    Log.d("rd", rd.toString())
                    viewModel.addReservation(uid, rd)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myPref = getSharedPreferences("User", MODE_PRIVATE)
        myPref.getString("uid", "")?.let{
            uid = it
        }

        val intent1 = Intent()
        intent1.getStringExtra("sid")?.let{
            sid=it
        }

        binding = ActivityStoreDetailBinding.inflate(layoutInflater)

        binding.btnRegister.setOnClickListener {
            Log.d("buttonclick", "success")
            val intent = Intent(this, LaundryRegisterActivity::class.java)
            intent.putExtra("sid", sid)
            activityForResult.launch(intent)
        }
        setContentView(binding.root)
    }
}