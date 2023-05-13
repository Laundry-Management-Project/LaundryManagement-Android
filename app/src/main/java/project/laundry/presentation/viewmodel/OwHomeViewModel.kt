package project.laundry.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.laundry.data.dataclass.AddStore
import project.laundry.data.dataclass.Store
import project.laundry.data.repository.Repository

class OwHomeViewModel : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val store : MutableLiveData<Store> = MutableLiveData()
    private val rep = Repository()

    fun loadDetail(userType:String, sid:String){
        loading.value=true
        rep.getStoreDetail(userType, sid){ response ->
            response?.let{
                store.value = it
            }
            loading.value=false
        }
    }
    fun updateDetail(buId:String, uId:String, updateStore: AddStore){
        loading.value=true
        rep.putStoreDetail(buId,uId,updateStore){ response ->
            response?.let{
                store.value=it
            }
            loading.value=false
        }

    }
}