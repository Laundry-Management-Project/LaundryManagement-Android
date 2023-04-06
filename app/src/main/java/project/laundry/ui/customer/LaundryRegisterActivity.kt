package project.laundry.ui.customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import project.laundry.data.dataclass.ClientData
import project.laundry.R
import project.laundry.data.dataclass.AddReservation
import project.laundry.databinding.ActivityLaundryRegisterBinding

class LaundryRegisterActivity : AppCompatActivity() {
    lateinit var binding : ActivityLaundryRegisterBinding
    lateinit var uid : String
    lateinit var sid : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLaundryRegisterBinding.inflate(layoutInflater)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
        val myPref = getSharedPreferences("User", MODE_PRIVATE)
        myPref.getString("uid", "")?.let{
            uid = it
        }
        if(uid == ""){

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
        val intent = Intent()
        intent.getStringExtra("sid")?.let{
            sid = it
        }

        // buid intent로 받아오기
        binding.btnRegister.setOnClickListener {
            val returnIntent = Intent(this, StoreDetailActivity::class.java)
            //"bu_id": "string",
            //  "clothCount": "string",
            //  "content": "string",
            //  "cu_id": "string"
//            val crd = ClientData(2, binding.clientName.text.toString(), binding.clientNumber.text.toString(),
//                laundryNum, binding.laundryPrice.text.toString().toInt(), "2023-03-27")

            val rd = AddReservation(sid, laundryNum.toString(), binding.clientName.text.toString())
            returnIntent.putExtra("reservation", rd)
            setResult(RESULT_OK, returnIntent)
            finish()
        }
        setContentView(binding.root)
    }
}