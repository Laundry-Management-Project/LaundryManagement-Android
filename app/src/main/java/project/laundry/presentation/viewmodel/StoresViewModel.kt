package project.laundry.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.laundry.data.dataclass.Store
import project.laundry.data.dataclass.Stores
import project.laundry.data.repository.Repository

class StoresViewModel : ViewModel() {

    val stores : MutableLiveData<ArrayList<Store>> = MutableLiveData()

    fun getStores(userType:String, uid : String){
        val rep = Repository()

        rep.getStores(userType, uid){ response ->
            response?.let{
                stores.value = it.stores
            }
        }
    }
}