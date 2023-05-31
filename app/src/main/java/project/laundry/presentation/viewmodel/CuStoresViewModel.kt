package project.laundry.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.laundry.data.dataclass.Store
import project.laundry.data.repository.Repository

class CuStoresViewModel : ViewModel() {

    val stores : MutableLiveData<ArrayList<Store>> = MutableLiveData()

    val loading = MutableLiveData<Boolean>()
    fun getStores(userType:String, uid : String){
        val rep = Repository()
        loading.value=true

        rep.getStores(userType, uid, 1,10){ response ->
            response?.let{
                stores.value = it.stores
            }
            loading.value=false
        }
    }
}