package project.laundry.data

import androidx.room.Database
import androidx.room.RoomDatabase
import project.laundry.data.dataclass.ClientData

@Database(entities = [ClientData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientDao
}