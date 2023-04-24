package project.laundry.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.laundry.data.dataclass.LoginResponse
import project.laundry.data.dataclass.LoginPost
import project.laundry.data.repository.Repository

class LoginViewModel : ViewModel() {

    val loginRes : MutableLiveData<LoginResponse> = MutableLiveData()
    val uid : MutableLiveData<String> = MutableLiveData()
    fun postLoginResponse(loginPostDTO: LoginPost) {
        val rep = Repository()
        Log.d("loginResponseType", loginPostDTO.toString())

        rep.postLogin(loginPostDTO) { response ->
            if(response != null){
                loginRes.value = response
                uid.value = response.uid
            }
            else{
                Log.d("loginResponse", "없음")
            }
        }


    }
}