package project.laundry.data.repository

import android.util.Log
import project.laundry.data.APIInterface
import project.laundry.data.dataclass.LoginPostDTO
import project.laundry.data.dataclass.LoginResponse
import project.laundry.data.dataclass.SignUpPostDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository() {

    private val myApi = Retrofit.Builder()
        .baseUrl("https://laundry-management-ywsj-team.koyeb.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(APIInterface::class.java)

    fun postLoginInfo(loginPost:LoginPostDTO, callback: (LoginResponse?) -> Unit) {
        val call: Call<LoginResponse> = myApi.postLoginResponse(loginPost)
        Log.d("loginResponse1", "고")
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()
                    Log.d("loginResponseBody", res.toString())
                    // 받은 데이터 처리
                    callback(res)
                } else {
                    Log.d("loginResponseBody",response.code().toString())
                    // API 호출에 실패한 경우
                    callback(null)
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // 통신 실패 처리
                Log.d("loginResponseFail", t.message.toString())
                callback(null)
            }
        })
    }
    fun postSignUpInfo(signUpPost: SignUpPostDto, callback: (LoginResponse?) -> Unit) {
        val call: Call<LoginResponse> = myApi.postSignUpResponse(signUpPost)
        Log.d("loginResponse1", "고")
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()
                    Log.d("loginResponseBody", res.toString())
                    // 받은 데이터 처리
                    callback(res)
                } else {
                    Log.d("loginResponseBody",response.code().toString())
                    // API 호출에 실패한 경우
                    callback(null)
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // 통신 실패 처리
                Log.d("loginResponseFail", t.message.toString())
                callback(null)
            }
        })
    }

}