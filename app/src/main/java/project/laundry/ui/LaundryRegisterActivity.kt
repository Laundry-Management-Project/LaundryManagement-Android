package project.laundry.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import project.laundry.data.dataclass.ClientData
import project.laundry.R
import project.laundry.databinding.ActivityLaundryRegisterBinding

class LaundryRegisterActivity : AppCompatActivity() {
    lateinit var binding : ActivityLaundryRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            val returnIntent = Intent()
            val crd = ClientData(2, binding.clientName.text.toString(), binding.clientNumber.text.toString(),
                laundryNum, binding.laundryPrice.text.toString().toInt(), "2023-03-27")

            returnIntent.putExtra("client_register_data", crd)
            setResult(RESULT_OK, returnIntent)
            finish()
        }
        setContentView(binding.root)
    }
}