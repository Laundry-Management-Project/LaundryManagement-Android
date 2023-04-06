package project.laundry.data.repository

import android.util.Log
import project.laundry.data.APIInterface
import project.laundry.data.dataclass.*
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

    fun postLoginCuInfo(loginPost:LoginPostDTO, callback: (LoginCuResponse?) -> Unit) {
        val call: Call<LoginCuResponse> = myApi.postLoginCuResponse(loginPost)
        call.enqueue(object : Callback<LoginCuResponse> {
            override fun onResponse(call: Call<LoginCuResponse>, response: Response<LoginCuResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()
                    Log.d("loginResponseBody", res.toString())
                    // 받은 데이터 처리
                    callback(res)
                } else {
                    Log.d("loginResponseError",response.errorBody()?.string()!!)

                    val res = LoginCuResponse(null, null, "가입되지 않은 사용자입니다.", false, "")
                    // API 호출에 실패한 경우
                    callback(res)
                }
            }
            override fun onFailure(call: Call<LoginCuResponse>, t: Throwable) {
                // 통신 실패 처리
                Log.d("loginResponseFail", t.message.toString())
                callback(null)
            }
        })
    }
    fun postLoginOwInfo(loginPost:LoginPostDTO, callback: (LoginOwResponse?) -> Unit) {
        val call: Call<LoginOwResponse> = myApi.postLoginOwResponse(loginPost)
        call.enqueue(object : Callback<LoginOwResponse> {
            override fun onResponse(call: Call<LoginOwResponse>, response: Response<LoginOwResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()
                    Log.d("loginResponseBody", res.toString())
                    // 받은 데이터 처리
                    callback(res)
                } else {
                    Log.d("loginResponseError",response.errorBody()?.string()!!)

                    val res = LoginOwResponse(null,"가입되지 않은 사용자입니다.", false, "")
                    // API 호출에 실패한 경우
                    callback(res)
                }
            }
            override fun onFailure(call: Call<LoginOwResponse>, t: Throwable) {
                // 통신 실패 처리
                Log.d("loginResponseFail", t.message.toString())
                callback(null)
            }
        })
    }
    fun postSignUpOwInfo(signUpPost: SignUpPostDto, callback: (SignUpResponse?) -> Unit) {
        val call: Call<SignUpResponse> = myApi.postSignUpOwResponse(signUpPost)
        Log.d("SignUpResponse", "고")
        call.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()
                    Log.d("SignUpResponseBody", res.toString())
                    // 받은 데이터 처리
                    callback(res)
                } else {
                    Log.d("SignUpResponseError",response.message())
                    // API 호출에 실패한 경우
                    callback(null)
                }
            }
            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                // 통신 실패 처리
                Log.d("SignUpResponseFail", t.message.toString())
                callback(null)
            }
        })
    }
    fun postSignUpCuInfo(signUpPost: SignUpPostDto, callback: (SignUpResponse?) -> Unit) {
        val call: Call<SignUpResponse> = myApi.postSignUpCuResponse(signUpPost)
        Log.d("SignUpResponse", "고")
        call.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()
                    Log.d("SignUpResponseBody", res.toString())
                    // 받은 데이터 처리
                    callback(res)
                } else {
                    Log.d("SignUpResponseError",response.message())
                    // API 호출에 실패한 경우
                    callback(null)
                }
            }
            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                // 통신 실패 처리
                Log.d("SignUpResponseFail", t.message.toString())
                callback(null)
            }
        })
    }
    fun postOwnerData(uid:String, addStoreDto: AddStoreDto, callback: (StoreListItems?) -> Unit){
        val call: Call<StoreListItems> = myApi.postOwnerData(uid, addStoreDto)
        call.enqueue(object : Callback<StoreListItems>{
            override fun onResponse(
                call: Call<StoreListItems>,
                response: Response<StoreListItems>
            ) {
                val res = response.body()
                callback(res)
            }

            override fun onFailure(call: Call<StoreListItems>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun addReservation(uid:String, rd:AddReservation, callback: (String?) -> Unit){
        val call:Call<String> = myApi.addReservation(uid, rd)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val res = response.body()
                callback(res)
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                callback(null)
            }
        })
    }

}