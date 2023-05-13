package project.laundry.data.repository

import project.laundry.data.AppDatabase

class MyDataBase (val database: AppDatabase) {
//    suspend fun getClientData() : List<ClientData> = withContext(Dispatchers.IO) {
//        lateinit var myClientData : List<ClientData>
//        database.withTransaction {
//            myClientData = database.clientDao().getAll()
//        }
//        return@withContext myClientData
//    }
//    suspend fun addClient(client : ClientData) = withContext(Dispatchers.IO){
//        database.withTransaction {
//            database.clientDao().insertAll(client)
//        }
//    }
}