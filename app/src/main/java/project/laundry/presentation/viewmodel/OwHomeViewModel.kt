package project.laundry.presentation.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import project.laundry.data.AwsImage
import project.laundry.data.dataclass.AddStore
import project.laundry.data.dataclass.Store
import project.laundry.data.repository.Repository

class OwHomeViewModel(application: Application) : AndroidViewModel(application) {
    val loading = MutableLiveData<Boolean>()
    val store : MutableLiveData<Store> = MutableLiveData()
    private val rep = Repository()

    private val ctx = application.applicationContext

    val bitmaps = MutableLiveData<List<Bitmap>>()
    fun loadDetail(userType:String, sid:String){
        loading.value=true
        val awsImage = AwsImage(ctx)
        viewModelScope.launch {
            bitmaps.postValue(awsImage.getImage(sid))
        }
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