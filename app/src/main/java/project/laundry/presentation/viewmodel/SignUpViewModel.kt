package project.laundry.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.laundry.data.dataclass.LoginResponse
import project.laundry.data.dataclass.SignUpPost
import project.laundry.data.repository.Repository

class SignUpViewModel : ViewModel() {

    val signUpRes : MutableLiveData<LoginResponse> = MutableLiveData()

    fun addUser(signUpPost: SignUpPost, userType:String){
        val rep = Repository()

        rep.postSignUp(userType, signUpPost) { response ->
            if(response != null){
                signUpRes.value = response
            }
            else{
                Log.d("loginResponse", "없음")
            }
        }

    }
}