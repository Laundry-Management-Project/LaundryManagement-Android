package project.laundry.data.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginOwResponse(
    @SerializedName("businessList") val storeList: List<StoreListItems>?,

    @SerializedName("message") val message : String,
    @SerializedName("status") val status : Boolean,
    @SerializedName("uid") val uid:String
) : Serializable
data class LoginCuResponse(
    @SerializedName("businesses") val storeList: List<StoreListItems>?,
    //val loginResponse: LoginResponse,
    @SerializedName("reservations") val reservation: List<Reservation>?,
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Boolean,
    @SerializedName("uid") val uid:String
) : Serializable

