package project.laundry.data.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponse(
    @SerializedName("message") val message : String,
    @SerializedName("status") val status : Boolean,
    @SerializedName("uid") val uid:String,
    val token:String
) : Serializable
data class SignUpResponse(
    @SerializedName("message") val message : String,
    @SerializedName("status") val status : Boolean,
    @SerializedName("uid") val uid:String
)

