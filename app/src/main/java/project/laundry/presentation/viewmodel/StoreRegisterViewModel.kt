package project.laundry.presentation.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import project.laundry.data.AwsImage
import project.laundry.data.dataclass.AddStore
import project.laundry.data.dataclass.Store
import project.laundry.data.repository.Repository

class StoreRegisterViewModel(application: Application) : AndroidViewModel(application) {

    val store : MutableLiveData<Store> = MutableLiveData()
    val imageUris = MutableLiveData<ArrayList<Uri>>()
    val mUris = ArrayList<Uri>()
    private val rep = Repository()

    private val ctx = application.applicationContext

    fun addStore(uid : String, addStoreDto: AddStore) {
        rep.addStore(uid, addStoreDto) { response ->
            if(response!=null){
                val awsImage=AwsImage(ctx)
                store.value = response
                Log.d("addStore", response.toString())
                awsImage.uploadImg(response.bu_id, mUris)
            }
            else{
                Log.d("addStore", response.toString())
            }
        }
    }

    fun addImage(uri:Uri){
        mUris.add(uri)
        imageUris.value = mUris
    }
}