package project.laundry.data.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class ClientData(
    @PrimaryKey val uid : Int,
    @ColumnInfo(name = "client_name") val clientName:String,
    @ColumnInfo(name = "phone_num") val phoneNum:String,
    @ColumnInfo(name = "laundry") val laundry:Int,
    @ColumnInfo(name = "price") val price:Int,
    @ColumnInfo(name = "register_date") val registerDate : String
    ) : Serializable
