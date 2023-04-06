package project.laundry.ui.owner.fragments

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.launch
import project.laundry.data.AppDatabase
import project.laundry.data.dataclass.ClientData
import project.laundry.data.repository.MyDataBase

class ClientViewModel(application: Application): AndroidViewModel(application) {
    val clients : MutableLiveData<List<ClientData>> = MutableLiveData()
    private val myClient : ArrayList<ClientData> = ArrayList()

    @SuppressLint("StaticFieldLeak")
    val ctx : Context = application.applicationContext

    private val db = Room.databaseBuilder(
        ctx,
        AppDatabase::class.java,
        "clientDao"
    ).build()
    private val rep = MyDataBase(db)


    fun loadClients(){
        viewModelScope.launch {
            clients.value = rep.getClientData()
        }
    }

    fun addClient(client: ClientData){
        //이거 나중에 바꾸기 옵저버에서 addclient해보는걸로
        viewModelScope.launch {
            rep.addClient(client)
        }
        myClient.add(client)
        clients.value = myClient
    }
}