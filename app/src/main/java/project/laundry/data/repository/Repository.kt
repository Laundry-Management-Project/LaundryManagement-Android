package project.laundry.data.repository

import android.util.Log
import okhttp3.OkHttpClient
import project.laundry.data.AuthInterceptor
import project.laundry.domain.APIInterface
import project.laundry.data.dataclass.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository() {

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build()
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
                    Log.d("response", "response is fail - post login")
                    Log.d("response",response.errorBody()?.string()!!)
                    callback(null)
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
        if(userType == "cu"){
            call = myApi.postSignUpCu(signUpPost)
        }
        else if(userType == "ow"){
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
                    Log.d("response", "response is fail - post sign up")
                    Log.d("response",response.errorBody()?.string()!!)
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
                    Log.d("response", "response is fail - add store")
                    Log.d("response",response.errorBody()?.string()!!)
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Store>, t: Throwable) {
                callback(null)
                Log.d("storeError", "fail")
            }
        })
    }

    fun addReservation(uid:String, buId : String, rd:AddReservation, callback: (Reservation?) -> Unit){
        val call:Call<Reservation> = myApi.postReservation(buId, uid, rd)
        call.enqueue(object : Callback<Reservation>{
            override fun onResponse(call: Call<Reservation>, response: Response<Reservation>) {
                val res = response.body()
                if(response.isSuccessful){
                    callback(res)
                }
                else {
                    Log.d("response", "response is fail - add reservation")
                    Log.d("response",response.errorBody()?.string()!!)
                    callback(null)
                }
            }
            override fun onFailure(call: Call<Reservation>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getStores(userType: String, id:String, callback: (Stores?) -> Unit){
        lateinit var call: Call<Stores>
        if(userType == "ow"){
            call = myApi.getStoresToOw(id)
        }
        else if(userType == "cu"){
            call = myApi.getStoresToCu(id)
        }
        call.enqueue(object : Callback<Stores>{
            override fun onResponse(call: Call<Stores>, response: Response<Stores>) {
                val res = response.body()
                if(response.isSuccessful){
                    callback(res)
                }
                else {
                    Log.d("response", "response is fail - get stores")
                    Log.d("response",response.errorBody()?.string()!!)
                    callback(null)
                }
            }
            override fun onFailure(call: Call<Stores>, t: Throwable) {
                callback(null)
            }

        })
    }
    fun getReservations(userType: String, id:String, callback: (Reservations?) -> Unit){
        lateinit var call: Call<Reservations>
        if(userType == "ow"){
            call = myApi.getReservationToOw(id)
        }
        else if(userType == "cu"){
            call = myApi.getReservationToCu(id)
        }
        call.enqueue(object : Callback<Reservations>{
            override fun onResponse(call: Call<Reservations>, response: Response<Reservations>) {
                val res = response.body()
                if(response.isSuccessful){
                    callback(res)
                }
                else {
                    Log.d("response", "response is fail - get reservation")
                    Log.d("response",response.errorBody()?.string()!!)
                    callback(null)
                }
            }
            override fun onFailure(call: Call<Reservations>, t: Throwable) {
                callback(null)
            }
        })
    }
    fun getStoreDetail(userType: String, id:String, callback: (Store?) -> Unit){
        lateinit var call: Call<Store>
        if(userType == "ow"){
            call = myApi.getStoreDetailToOw(id)
        }
        else if(userType == "cu"){
            call = myApi.getStoreDetailToCu(id)
        }
        call.enqueue(object : Callback<Store>{
            override fun onResponse(call: Call<Store>, response: Response<Store>) {
                val res = response.body()
                if(response.isSuccessful){
                    callback(res)
                }
                else {
                    Log.d("response", "response is fail - get store detail")
                    Log.d("response",response.errorBody()?.string()!!)
                    callback(null)
                }
            }
            override fun onFailure(call: Call<Store>, t: Throwable) {
                callback(null)
            }
        })
    }
    fun putReservation(buId:String, reId:String, putResDto: PutReservation, callback: (Reservations?) -> Unit){
        val call = myApi.putReservation(buId, reId, putResDto)
        call.enqueue(object : Callback<Reservations>{
            override fun onResponse(call: Call<Reservations>, response: Response<Reservations>) {
                val res = response.body()
                if(response.isSuccessful){
                    callback(res)
                }
                else {
                    Log.d("response", "response is fail - put reservation")
                    Log.d("response",response.errorBody()?.string()!!)
                    callback(null)
                }

            }

            override fun onFailure(call: Call<Reservations>, t: Throwable) {
                callback(null)
            }
        })
    }
    fun putStoreDetail(buId:String, uId : String, addStoreDto: AddStore, callback: (Store?) -> Unit){
        val call = myApi.putStoreDetail(buId, uId, addStoreDto)
        call.enqueue(object:Callback<Store>{
            override fun onResponse(call: Call<Store>, response: Response<Store>) {
                val res = response.body()
                if(response.isSuccessful){
                    callback(res)
                }
                else{
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Store>, t: Throwable) {
                callback(null)
            }
        })
    }

}