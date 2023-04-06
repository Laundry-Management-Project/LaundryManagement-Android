package project.laundry.data.dataclass

import com.google.gson.annotations.SerializedName

data class Reservation(
    @SerializedName("bu_address") val bu_address : String,
    @SerializedName("bu_name") val bu_name : String,
    @SerializedName("clothCount") val clothCount : String,
    @SerializedName("clothStatus") val clothStatus : String,
    @SerializedName("content") val content : String,
    @SerializedName("cu_name") val cu_name : String,
    @SerializedName("rid") val rid : String,
    @SerializedName("bu_id") val bu_id:String,
    @SerializedName("createdAt") val createdAt : String
)
