package project.laundry.data

import project.laundry.data.dataclass.*
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

    @GET ("owner/{uid}/businesses/{buId}/home")
    fun getOwnerData(
        @Path("uid") uid:String,
        @Path("buId") buId:String
    ) : Call<String>
    @POST ("owner/{uid}/businesses/add")
    fun postOwnerData(
        @Path("uid") uid:String,
        @Body addStoreDto : AddStoreDto
    ) : Call<StoreListItems>

    @POST ("customer/{uid}/reservation/add")
    fun addReservation(
        @Path("uid") uid:String,
        @Body rd : AddReservation
    ) : Call<String>

    @POST("signup/cu")
    fun postSignUpCuResponse(@Body signUpPostDto: SignUpPostDto): Call<SignUpResponse>

    @POST("signup/ow")
    fun postSignUpOwResponse(@Body signUpPostDto: SignUpPostDto): Call<SignUpResponse>

    @POST("login/cu")
    fun postLoginCuResponse(@Body loginPostDto : LoginPostDTO): Call<LoginCuResponse>

    @POST("login/ow")
    fun postLoginOwResponse(@Body loginPostDto : LoginPostDTO): Call<LoginOwResponse>
}