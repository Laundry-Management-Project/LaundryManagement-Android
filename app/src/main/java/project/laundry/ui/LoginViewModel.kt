package project.laundry.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.laundry.data.dataclass.LoginPostDTO
import project.laundry.data.dataclass.LoginResponse
import project.laundry.data.repository.Repository

class LoginViewModel : ViewModel() {

    val loginRes : MutableLiveData<LoginResponse> = MutableLiveData()

    fun postLoginResponse(loginPostDTO: LoginPostDTO) {
        val rep = Repository()
        rep.postLoginInfo(loginPostDTO) { response ->
            if(response != null){
                loginRes.value = response
            }
            else{
                Log.d("loginResponse", "없음")
            }
        }
    }
}