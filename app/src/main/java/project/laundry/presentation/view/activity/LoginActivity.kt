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
            viewModel.postLoginResponse(LoginPost(binding.etId.text.toString(), binding.etPw.text.toString(),userType))
        }


        viewModel.loginRes.observe(this, Observer { response ->
            Log.d("loginResponse", response.toString())
            if(response.status){
                if(userType == "OW"){
                    val intent = Intent(this, StoreListActivity::class.java)
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