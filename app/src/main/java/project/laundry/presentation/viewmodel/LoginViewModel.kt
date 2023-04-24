package project.laundry.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.laundry.data.dataclass.LoginCuResponse
import project.laundry.data.dataclass.LoginOwResponse
import project.laundry.data.dataclass.LoginPostDTO
import project.laundry.data.repository.Repository

class LoginViewModel : ViewModel() {

    val loginOwRes : MutableLiveData<LoginOwResponse> = MutableLiveData()
    val loginCuRes : MutableLiveData<LoginCuResponse> = MutableLiveData()
    val uid : MutableLiveData<String> = MutableLiveData()
    fun postLoginResponse(loginPostDTO: LoginPostDTO, userType : String) {
        val rep = Repository()
        Log.d("loginResponseType", loginPostDTO.toString())
        if(userType == "CU"){
            rep.postLoginCuInfo(loginPostDTO) { response ->
                if(response != null){
                    loginCuRes.value = response
                    uid.value = response.uid
                }
                else{
                    Log.d("loginResponse", "없음")
                }
            }
        }
        else if(userType == "OW"){
            rep.postLoginOwInfo(loginPostDTO) { response ->
                if(response != null){
                    loginOwRes.value = response
                    uid.value = response.uid
                }
                else{
                    Log.d("loginResponse", "없음")
                }
            }
        }

    }
}