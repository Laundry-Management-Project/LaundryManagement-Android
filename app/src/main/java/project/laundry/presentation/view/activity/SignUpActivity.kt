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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.radioGroup.check(binding.radioButtonCustomer.id)

        var userType:String = "CU"
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val sel = findViewById<RadioButton>(checkedId)
            userType = if(sel.text.toString() == "손님"){
                "CU"
            } else {
                "OW"
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

        viewModel.signUpRes.observe(this, Observer { response ->
            if(response.status){
                finish()
            }
            else{
                Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
            }
        })
//        for (i in 0 until binding.layout.childCount) {
//            val child = binding.layout.getChildAt(i)
//            if (child is EditText) {
//                child.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
//                    val layoutParams = v.layoutParams
//                    val height = if (hasFocus) 70 else 60
//                    val heightToPx = height.toPx(this)
//                    layoutParams.height = heightToPx
//                    v.layoutParams = layoutParams
//                }
//            }
//        }
        setContentView(binding.root)
    }
//    fun Int.toPx(context: Context): Int {
//        val scale = context.resources.displayMetrics.density
//        return (this * scale + 0.5f).toInt()
//    }

}