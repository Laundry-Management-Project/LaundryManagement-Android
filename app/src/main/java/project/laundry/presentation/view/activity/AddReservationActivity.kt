package project.laundry.presentation.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import project.laundry.R
import project.laundry.data.App
import project.laundry.data.dataclass.AddReservation
import project.laundry.databinding.ActivityReservationAddBinding

import project.laundry.presentation.viewmodel.AddReservationViewModel

class AddReservationActivity : AppCompatActivity() {
    lateinit var binding : ActivityReservationAddBinding

    val uid = App.prefs.uid!!
    val buId = App.prefs.buId!!
    val userType = App.prefs.userType!!

    private val viewModel = AddReservationViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

        viewModel.myReservation.observe(this){ reservation ->
            if(reservation.re_id.isNotEmpty()){
//                val returnIntent = Intent(this, StoreDetailActivity::class.java)
//                setResult(RESULT_OK, returnIntent)
                finish()
            }
        }


        setContentView(binding.root)
    }
    private fun initView(){
        binding = ActivityReservationAddBinding.inflate(layoutInflater)

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

            val rd = AddReservation(binding.etDetails.text.toString(), binding.etClothingType.text.toString(),laundryNum)
            viewModel.addReservation(uid, buId, rd)

        }
    }
}