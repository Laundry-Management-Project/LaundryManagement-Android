package project.laundry.presentation.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import project.laundry.data.dataclass.SignUpPost
import project.laundry.databinding.ActivitySignUpBinding
import project.laundry.presentation.viewmodel.SignUpViewModel
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    private val viewModel = SignUpViewModel()
    var userType: String = "cu"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

        viewModel.signUpRes.observe(this, Observer { response ->
            if (response.status) {
                finish()
            } else {
                Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
            }
        })
        setContentView(binding.root)
    }

    private fun initView() {


        binding = ActivitySignUpBinding.inflate(layoutInflater)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.radioGroup.check(binding.radioButtonCustomer.id)


        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val sel = findViewById<RadioButton>(checkedId)
            userType = if (sel.text.toString() == "손님") {
                "cu"
            } else {
                "ow"
            }
        }


        binding.etId.addTextChangedListener {
            if (!Pattern.matches("^[a-zA-Z0-9]*\$", it.toString())) {
                binding.tvCheckId.visibility = VISIBLE
            } else {
                binding.tvCheckId.visibility = INVISIBLE
            }
        }
        binding.etPw.addTextChangedListener {

            if (!Pattern.matches(
                    "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,20}$",
                    it.toString()
                )
            ) {
                binding.tvCheckPw.visibility = VISIBLE
            } else {
                binding.tvCheckPw.visibility = INVISIBLE
            }
        }
        binding.etName.addTextChangedListener {
            if(!Pattern.matches("^[가-힣0-9]*\$",it.toString())){
                binding.tvCheckName.visibility = VISIBLE
            } else {
                binding.tvCheckName.visibility = INVISIBLE
            }
        }
        binding.etPhoneNum.addTextChangedListener {

            if(!Patterns.PHONE.matcher(it.toString()).matches()){
                binding.tvCheckPhone.visibility = VISIBLE
            } else {
                binding.tvCheckPhone.visibility = INVISIBLE
            }
        }

        binding.btnRegister.setOnClickListener {
            val phone = binding.etPhoneNum.text.toString()
            val id = binding.etId.text.toString()
            val pw = binding.etPw.text.toString()
            val name = binding.etName.text.toString()



            viewModel.addUser(SignUpPost(id, pw, name, phone), userType)
        }
    }

}