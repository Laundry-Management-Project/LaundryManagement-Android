package project.laundry.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.laundry.data.dataclass.SignUpPostDto
import project.laundry.data.dataclass.SignUpResponse
import project.laundry.data.repository.Repository

class SignUpViewModel : ViewModel() {

    val signUpRes : MutableLiveData<SignUpResponse> = MutableLiveData()

    fun addUser(signUpPost: SignUpPostDto, userType:String){
        val rep = Repository()
        if(userType == "CU"){
            rep.postSignUpCuInfo(signUpPost) { response ->
                if(response != null){
                    signUpRes.value = response
                }
                else{
                    Log.d("loginResponse", "없음")
                }
            }
        }
        else if(userType == "OW"){
            rep.postSignUpOwInfo(signUpPost) { response ->
                if(response != null){
                    signUpRes.value = response
                }
                else{
                    Log.d("loginResponse", "없음")
                }
            }
        }

    }
}