package project.laundry.presentation.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import project.laundry.R
import project.laundry.data.dataclass.AddReservation
import project.laundry.databinding.ActivityLaundryRegisterBinding
import project.laundry.presentation.viewmodel.AddReservationViewModel

class AddReservationActivity : AppCompatActivity() {
    lateinit var binding : ActivityLaundryRegisterBinding
    lateinit var uid : String
    lateinit var buId : String
    lateinit var userType:String

    private val viewModel = AddReservationViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getUserInfo()

        val intent = intent
        intent.getStringExtra("bu_id")?.let{
            buId = it
        }

        initView()



        setContentView(binding.root)
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
        binding = ActivityLaundryRegisterBinding.inflate(layoutInflater)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        var laundryNum : Int = 0
        val items = resources.getStringArray(R.array.num_array)
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        binding.spinnerLaundry.adapter = spinnerAdapter
        binding.spinnerLaundry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if(position != 0){
                    laundryNum = items[position].toInt()
                }


            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        binding.btnRegister.setOnClickListener {
            val returnIntent = Intent(this, StoreDetailActivity::class.java)
            val rd = AddReservation(binding.etDetails.text.toString(), binding.etClothingType.text.toString(),laundryNum)
            viewModel.addReservation(uid, buId, rd)
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }
}