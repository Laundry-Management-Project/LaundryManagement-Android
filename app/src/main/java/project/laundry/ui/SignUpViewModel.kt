package project.laundry.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.laundry.data.dataclass.LoginResponse
import project.laundry.data.dataclass.SignUpPostDto
import project.laundry.data.repository.Repository

class SignUpViewModel : ViewModel() {

    val signUpRes : MutableLiveData<LoginResponse> = MutableLiveData()

    fun addUser(signUpPost: SignUpPostDto){
        val rep = Repository()
        rep.postSignUpInfo(signUpPost) { response ->
            if(response != null){
                signUpRes.value = response
            }
            else{
                Log.d("loginResponse", "없음")
            }
        }
    }
}