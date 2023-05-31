package project.laundry.presentation.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import project.laundry.data.AwsImage
import project.laundry.data.dataclass.AddReservation
import project.laundry.data.dataclass.Reservation
import project.laundry.data.repository.Repository

class AddReservationViewModel(application: Application) : AndroidViewModel(application) {

    val loading = MutableLiveData<Boolean>()
    val myReservation : MutableLiveData<Reservation> = MutableLiveData()
    private val rep = Repository()

    val imageUris = MutableLiveData<ArrayList<Uri>>()
    private val mUris = ArrayList<Uri>()

    private val ctx = application.applicationContext

    fun addReservation(uid:String, buid:String,  rd:AddReservation){
        loading.value=true

        rep.addReservation(uid, buid, rd){ response ->
            response?.let{
                myReservation.value = it

                val awsImage = AwsImage(ctx)
                
                viewModelScope.launch{
                    awsImage.uploadImg(it.re_id, mUris)
                }

            }
            loading.value=false
        }
    }
    fun addImage(uri:Uri){
        mUris.add(uri)
        imageUris.value = mUris
    }
}