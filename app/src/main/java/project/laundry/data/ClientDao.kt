package project.laundry.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import project.laundry.data.dataclass.ClientData

@Dao
interface ClientDao {
    @Query("SELECT * FROM ClientData")
    suspend fun getAll(): List<ClientData>

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<ClientData>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): ClientData

    @Insert
    suspend fun insertAll(vararg clients: ClientData)

    @Delete
    suspend fun delete(user: ClientData)
}