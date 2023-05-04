package project.laundry.presentation.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import project.laundry.data.dataclass.SignUpPost
import project.laundry.databinding.ActivitySignUpBinding
import project.laundry.presentation.viewmodel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {
    lateinit var binding:ActivitySignUpBinding
    private val viewModel= SignUpViewModel()
    var userType:String = "cu"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

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
    private fun initView(){
        binding = ActivitySignUpBinding.inflate(layoutInflater)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.radioGroup.check(binding.radioButtonCustomer.id)


        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val sel = findViewById<RadioButton>(checkedId)
            userType = if(sel.text.toString() == "손님"){
                "cu"
            } else {
                "ow"
            }
        }
        binding.btnRegister.setOnClickListener {
            viewModel.addUser(
                SignUpPost(binding.etId.text.toString(),
                    binding.etPw.text.toString(),
                    binding.etName.text.toString(),
                    binding.etPhoneNum.text.toString(),
                ),
                userType
            )
        }
    }

}