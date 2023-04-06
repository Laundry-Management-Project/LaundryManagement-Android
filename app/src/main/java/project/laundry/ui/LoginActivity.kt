package project.laundry.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import project.laundry.data.dataclass.LoginPostDTO
import project.laundry.databinding.ActivityLoginBinding
import project.laundry.ui.customer.CustomerMainActivity
import project.laundry.ui.owner.OwnerMainActivity
import project.laundry.ui.owner.StoreListActivity

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    private val viewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        binding.radioGroup.check(binding.radioButtonCustomer.id)

        var userType:String = "CU"

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val sel = findViewById<RadioButton>(checkedId)

            userType = if(sel.text.toString() == "손님"){
                "CU"
            } else {
                "OW"
            }
        }
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            viewModel.postLoginResponse(LoginPostDTO(binding.etId.text.toString(), binding.etPw.text.toString()),userType)
        }


        viewModel.loginCuRes.observe(this, Observer { response ->
            Log.d("loginResponse", response.toString())
            if(response.status){
                val intent = Intent(this, CustomerMainActivity::class.java)
                intent.putExtra("loginCu", response)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
            }
        })
        viewModel.loginOwRes.observe(this, Observer { response ->
            Log.d("loginResponse", response.toString())
            if(response.status){
                val intent = Intent(this, StoreListActivity::class.java)
                Log.d("loginToMain", response.toString())
                intent.putExtra("loginOw", response)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.uid.observe(this, Observer { uid ->
            val sharedPreference = getSharedPreferences("User", MODE_PRIVATE)
            val editor  : SharedPreferences.Editor = sharedPreference.edit()
            editor.putString("uid",uid)
            editor.commit()
        })

        setContentView(binding.root)
    }
}