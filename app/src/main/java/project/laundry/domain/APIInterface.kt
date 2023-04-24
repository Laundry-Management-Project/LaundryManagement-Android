package project.laundry.domain

import project.laundry.data.dataclass.*
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

    //  손님
    @GET ("customer/reservations")
    fun getReservationToCu(
        @Query("uid") uid:String,
    ) : Call<ArrayList<Reservation>>
    @GET ("customer/businesses")
    fun getStoresToCu(
        @Query("uid") uid:String
    ) : Call<Stores>

    @GET ("customer/{buId}")
    fun getStoreDetailToCu(
        @Path("buId") buId:String
    ) : Call<Store>

    @POST (" customer/{buId}/reservation")
    fun postReservation(
        @Path("buId") bu_id:String,
        @Query("uid") uid : String,
        @Body rd : AddReservation
    ) : Call<Reservation>



    // 사장
    @GET ("owner/businesses")
    fun getStoresToOw(
        @Query("uid") uid:String
    ) : Call<Stores>

    @POST ("owner/business/add")
    fun postStore(
        @Query("uid") uid:String,
        @Body addStoreDto : AddStore
    ) : Call<Store>

    @GET ("owner/{buId}/reservations")
    fun getReservationToOw(
        @Path("buId") buId:String
    ) : Call<ArrayList<Reservation>>

    @GET ("owner/{buId}/detail")
    fun getStoreDetailToOw(
        @Path("buId") buId:String
    ) : Call<Store>

    @PUT ("owner/{buId}/update")
    fun putStoreDetail(
        @Path("buId") buId:String,
        @Query("uId") uid:String,
        @Body addStoreDto : AddStore
    ) : Call<Store>

    @PUT ("owner/{buId}/reservations/{reId}}")
    fun putReservation(
        @Path("buId") buId:String,
        @Path("reId") reId:String,
        @Body putResDto : PutReservation
    ) : Call<Reservation>

    // 로그인 및 회원가입
    @POST("signup/cu")
    fun postSignUpCu(@Body signUpPostDto: SignUpPost): Call<LoginResponse>

    @POST("signup/ow")
    fun postSignUpOw(@Body signUpPostDto: SignUpPost): Call<LoginResponse>

    @POST("login")
    fun postLogin(@Body loginPostDto : LoginPost): Call<LoginResponse>
}