package project.laundry.data.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Store (
    val bu_id : String,
    val name : String,
    val address : String,
    val contact : String,
    val bu_hour : String,
    val intro : String
    ): Serializable

data class Stores(
    @SerializedName("businesses") val stores : ArrayList<Store>
    ) : Serializable