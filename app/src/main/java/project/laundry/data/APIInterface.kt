package project.laundry.data

import project.laundry.data.dataclass.LoginPostDTO
import project.laundry.data.dataclass.LoginResponse
import project.laundry.data.dataclass.SignUpPostDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface APIInterface {

    @POST("signUp")
    fun postSignUpResponse(@Body signUpPostDto: SignUpPostDto): Call<LoginResponse>

    @POST("login")
    fun postLoginResponse(@Body loginPostDto : LoginPostDTO): Call<LoginResponse>

}