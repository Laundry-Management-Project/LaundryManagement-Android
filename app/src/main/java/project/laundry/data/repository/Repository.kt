package project.laundry.data.repository

import android.util.Log
import project.laundry.domain.APIInterface
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

    fun postLogin(loginPost:LoginPost, callback: (LoginResponse?) -> Unit) {
        val call: Call<LoginResponse> = myApi.postLogin(loginPost)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()
                    Log.d("loginResponseBody", res.toString())
                    // 받은 데이터 처리
                    callback(res)
                } else {
                    Log.d("loginResponseError",response.errorBody()?.string()!!)

                    // API 호출에 실패한 경우
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // 통신 실패 처리
                Log.d("loginResponseFail", t.message.toString())
                callback(null)
            }
        })
    }
    fun postSignUp(userType : String, signUpPost: SignUpPost, callback: (LoginResponse?) -> Unit) {
        lateinit var call : Call<LoginResponse>
        if(userType == "CU"){
            call = myApi.postSignUpCu(signUpPost)
        }
        else if(userType == "OW"){
            call = myApi.postSignUpOw(signUpPost)
        }
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()
                    Log.d("SignUpResponseBody", res.toString())
                    // 받은 데이터 처리
                    callback(res)
                } else {
                    Log.d("SignUpResponseError",response.errorBody()?.string()!!)
                    // API 호출에 실패한 경우
                    callback(null)
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // 통신 실패 처리
                Log.d("SignUpResponseFail", t.message.toString())
                callback(null)
            }
        })
    }
    fun addStore(uid:String, addStoreDto: AddStore, callback: (Store?) -> Unit){
        val call: Call<Store> = myApi.postStore(uid, addStoreDto)
        call.enqueue(object : Callback<Store>{
            override fun onResponse(

                call: Call<Store>,
                response: Response<Store>
            ) {
                if (response.isSuccessful) {
                    val res = response.body()
                    callback(res)
                }
                else{
                    Log.d("storeError", response.errorBody()?.string()!!)
                }
            }

            override fun onFailure(call: Call<Store>, t: Throwable) {
                callback(null)
                Log.d("storeError", "fail")
            }
        })
    }

    fun addReservation(uid:String, buId : String, rd:AddReservation, callback: (Reservation?) -> Unit){
        val call:Call<Reservation> = myApi.postReservation(uid, buId, rd)
        call.enqueue(object : Callback<Reservation>{
            override fun onResponse(call: Call<Reservation>, response: Response<Reservation>) {
                val res = response.body()
                callback(res)
            }
            override fun onFailure(call: Call<Reservation>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getStores(userType: String, id:String, callback: (Stores?) -> Unit){
        lateinit var call: Call<Stores>
        if(userType == "OW"){
            call = myApi.getStoresToOw(id)
        }
        else if(userType == " CU"){
            call = myApi.getStoresToCu(id)
        }
        call.enqueue(object : Callback<Stores>{
            override fun onResponse(call: Call<Stores>, response: Response<Stores>) {
                val res = response.body()
                callback(res)
            }
            override fun onFailure(call: Call<Stores>, t: Throwable) {
                callback(null)
            }
        })
    }
    fun getReservations(userType: String, id:String, callback: (ArrayList<Reservation>?) -> Unit){
        lateinit var call: Call<ArrayList<Reservation>>
        if(userType == "OW"){
            call = myApi.getReservationToOw(id)
        }
        else if(userType == " CU"){
            call = myApi.getReservationToCu(id)
        }
        call.enqueue(object : Callback<ArrayList<Reservation>>{
            override fun onResponse(call: Call<ArrayList<Reservation>>, response: Response<ArrayList<Reservation>>) {
                val res = response.body()
                callback(res)
            }
            override fun onFailure(call: Call<ArrayList<Reservation>>, t: Throwable) {
                callback(null)
            }
        })
    }
    fun getStoreDetail(userType: String, id:String, callback: (Store?) -> Unit){
        lateinit var call: Call<Store>
        if(userType == "OW"){
            call = myApi.getStoreDetailToOw(id)
        }
        else if(userType == " CU"){
            call = myApi.getStoreDetailToCu(id)
        }
        call.enqueue(object : Callback<Store>{
            override fun onResponse(call: Call<Store>, response: Response<Store>) {
                val res = response.body()
                callback(res)
            }
            override fun onFailure(call: Call<Store>, t: Throwable) {
                callback(null)
            }
        })
    }
    fun updateStoreDetail(){

    }

}