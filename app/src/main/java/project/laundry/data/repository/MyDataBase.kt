package project.laundry.data.repository

import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import project.laundry.data.AppDatabase
import project.laundry.data.dataclass.ClientData

class MyDataBase (val database: AppDatabase) {
    suspend fun getClientData() : List<ClientData> = withContext(Dispatchers.IO) {
        lateinit var myClientData : List<ClientData>
        database.withTransaction {
            myClientData = database.clientDao().getAll()
        }
        return@withContext myClientData
    }
    suspend fun addClient(client : ClientData) = withContext(Dispatchers.IO){
        database.withTransaction {
            database.clientDao().insertAll(client)
        }
    }
}