package project.laundry.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.laundry.data.dataclass.AddStore
import project.laundry.data.dataclass.Store
import project.laundry.data.repository.Repository

class StoreRegisterViewModel : ViewModel() {

    val store : MutableLiveData<Store> = MutableLiveData()
    private val rep = Repository()

    fun addStore(uid : String, addStoreDto: AddStore) {

        rep.addStore(uid, addStoreDto) { response ->
            if(response!=null){
                store.value = response
                Log.d("addStore", response.toString())
            }
            else{
                Log.d("addStore", response.toString())
            }
        }
    }
}