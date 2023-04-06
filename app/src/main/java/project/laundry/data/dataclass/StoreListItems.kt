package project.laundry.data.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StoreListItems(
    @SerializedName("address") val storeAddress : String,
    @SerializedName("bu_hour") val buHours : String,
    @SerializedName("id") val sid:String,
    @SerializedName("name") val storeName :String
) : Serializable