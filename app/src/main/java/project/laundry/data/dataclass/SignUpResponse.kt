package project.laundry.data.dataclass

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("message") val message : String,
    @SerializedName("status") val status : Boolean,
    @SerializedName("uid") val uid : String
)