package project.laundry.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.laundry.data.App
import project.laundry.data.dataclass.LoginResponse
import project.laundry.data.dataclass.LoginPost
import project.laundry.data.repository.Repository

class LoginViewModel : ViewModel() {

    val loginRes : MutableLiveData<LoginResponse> = MutableLiveData()
    val uid : MutableLiveData<String> = MutableLiveData()


    val rep = Repository()
    fun postLoginResponse(loginPostDTO: LoginPost) {

        Log.d("loginResponseType", loginPostDTO.toString())

        rep.postLogin(loginPostDTO) { response ->
            if(response != null){
                loginRes.value = response
                uid.value = response.uid
                App.prefs.token = response.token
                Log.d("token", response.token)
            }
            else{
                Log.d("loginResponse", "없음")
            }
        }
    }

//    fun useToken(token :String){
//        rep.useToken(token)
//    }
}