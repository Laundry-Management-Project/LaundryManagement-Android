package project.laundry.data.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Reservation(
    val re_id : String,
    val bu_id : String,
    val cu_id : String,
    val cu_name : String,
    val bu_name : String,
    val bu_address : String,
    val num : Int,
    val cu_phone:String,
    val contact : String,
    val cloth_status : String,
    val price : Int,
    val clothing_type : String,
    val request_detail : String,
    val createdAt : String
    ) : Serializable

data class Reservations (
    val reservations : ArrayList<Reservation>
    ) : Serializable
