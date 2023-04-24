package project.laundry.presentation.view.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import project.laundry.data.dataclass.LoginPost
import project.laundry.databinding.ActivityLoginBinding
import project.laundry.presentation.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    private val viewModel = LoginViewModel()
    var userType:String = "CU"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

        viewModel.loginRes.observe(this, Observer { response ->
            Log.d("loginResponse", response.toString())
            if(response.status){
                if(userType == "OW"){
                    val intent = Intent(this, OwStoresActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else if(userType == "CU"){
                    val intent = Intent(this, CustomerMainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            else{
                Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.uid.observe(this, Observer { uid ->
            val sharedPreference = getSharedPreferences("User", MODE_PRIVATE)
            val editor  : SharedPreferences.Editor = sharedPreference.edit()
            editor.putString("uid",uid)
            editor.putString("userType", userType)
            editor.commit()
        })

        setContentView(binding.root)
    }
    private fun initView(){
        binding = ActivityLoginBinding.inflate(layoutInflater)

        binding.radioGroup.check(binding.radioButtonCustomer.id)

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
            viewModel.postLoginResponse(LoginPost(binding.etId.text.toString(), binding.etPw.text.toString(),userType))
        }
    }
}