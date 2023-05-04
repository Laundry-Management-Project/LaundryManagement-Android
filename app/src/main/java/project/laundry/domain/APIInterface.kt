package project.laundry.domain

import project.laundry.data.dataclass.*
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

    //  손님
    @GET ("customer/reservations")
    fun getReservationToCu(
        @Query("uId") uid:String,
    ) : Call<Reservations>
    @GET ("customer/businesses")
    fun getStoresToCu(
        @Query("uId") uid:String
    ) : Call<Stores>

    @GET ("customer/{buId}")
    fun getStoreDetailToCu(
        @Path("buId") buId:String
    ) : Call<Store>

    @POST ("customer/{buId}/reservation")
    fun postReservation(
        @Path("buId") bu_id:String,
        @Query("uId") uid : String,
        @Body rd : AddReservation
    ) : Call<Reservation>



    // 사장
    @GET ("owner/businesses")
    fun getStoresToOw(
        @Query("uId") uid:String
    ) : Call<Stores>

    @POST ("owner/business/add")
    fun postStore(
        @Query("uId") uid:String,
        @Body addStoreDto : AddStore
    ) : Call<Store>

    @GET ("owner/{buId}/reservations")
    fun getReservationToOw(
        @Path("buId") buId:String
    ) : Call<Reservations>

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
    ) : Call<Reservations>

    // 로그인 및 회원가입
    @POST("signup/cu")
    fun postSignUpCu(@Body signUpPostDto: SignUpPost): Call<LoginResponse>

    @POST("signup/ow")
    fun postSignUpOw(@Body signUpPostDto: SignUpPost): Call<LoginResponse>

    @POST("login")
    fun postLogin(@Body loginPostDto : LoginPost): Call<LoginResponse>
}