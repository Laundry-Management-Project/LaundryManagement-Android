package project.laundry.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.laundry.data.dataclass.AddReservation
import project.laundry.data.dataclass.Store
import project.laundry.data.repository.Repository

class StoreDetailViewModel : ViewModel() {

    val store : MutableLiveData<Store> = MutableLiveData()
    private val rep = Repository()
    fun loadDetail(userType:String, sid:String){
        rep.getStoreDetail(userType, sid){ response ->
            response?.let{
                store.value = response
            }
        }
    }

    fun putStoreDetail(){

    }


}