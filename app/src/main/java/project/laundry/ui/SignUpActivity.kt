package project.laundry.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import project.laundry.data.dataclass.SignUpPostDto
import project.laundry.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var binding:ActivitySignUpBinding
    private val viewModel=SignUpViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)

        binding.radioGroup.check(binding.radioButtonCustomer.id)

        var userType:String = binding.radioButtonCustomer.text.toString()
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val sel = findViewById<RadioButton>(checkedId)
            userType = sel.text.toString()
        }
        binding.btnRegister.setOnClickListener {
            viewModel.addUser(
                SignUpPostDto(binding.etId.text.toString(),
                    binding.etPw.text.toString(),
                    binding.etName.text.toString(),
                    binding.etPhoneNum.text.toString(),
                    userType)
            )
        }
        viewModel.signUpRes.observe(this, Observer { response ->
            if(response.status){
                finish()
            }
            else{
                Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
            }
        })
        setContentView(binding.root)
    }
}