package project.laundry.presentation.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import project.laundry.data.AwsImage
import project.laundry.data.dataclass.AddReservation
import project.laundry.data.dataclass.Store
import project.laundry.data.repository.Repository

class StoreDetailViewModel(application: Application) : AndroidViewModel(application) {

    val loading = MutableLiveData<Boolean>()
    val store : MutableLiveData<Store> = MutableLiveData()
    private val rep = Repository()

    private val ctx = application.applicationContext

    val bitmaps = MutableLiveData<List<Bitmap>>()
    fun loadDetail(userType:String, sid:String){
//        loading.value=true

        val awsImage = AwsImage(ctx)
        viewModelScope.launch {
            bitmaps.postValue(awsImage.getImage("jun"))
        }
//        rep.getStoreDetail(userType, sid){ response ->
//            response?.let{
//                store.value = response
//            }
//            loading.value=false
//        }
    }

    fun putStoreDetail(){

    }


}