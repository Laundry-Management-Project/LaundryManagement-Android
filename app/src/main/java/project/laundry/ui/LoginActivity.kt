package project.laundry.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import project.laundry.data.dataclass.LoginPostDTO
import project.laundry.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    private val viewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        binding.radioGroup.check(binding.radioButtonCustomer.id)

        var userType:String = binding.radioButtonCustomer.text.toString()
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val sel = findViewById<RadioButton>(checkedId)
            userType = sel.text.toString()
        }
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            viewModel.postLoginResponse(LoginPostDTO(binding.etId.text.toString(), binding.etPw.text.toString(),userType))
        }


        viewModel.loginRes.observe(this, Observer { response ->
            Log.d("loginResponse", response.toString())
            if(response.status){
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("uid", response.uid)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
            }
        })
        setContentView(binding.root)
    }
}