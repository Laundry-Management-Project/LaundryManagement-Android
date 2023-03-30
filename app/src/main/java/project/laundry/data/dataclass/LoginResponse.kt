package project.laundry.data.dataclass

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message") val message : String,
    @SerializedName("status") val status : Boolean,
    @SerializedName("id") val uid:String
)
