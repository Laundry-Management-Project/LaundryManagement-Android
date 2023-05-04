package project.laundry.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.laundry.data.dataclass.AddReservation
import project.laundry.data.dataclass.Reservation
import project.laundry.data.repository.Repository

class AddReservationViewModel : ViewModel() {
    val myReservation : MutableLiveData<Reservation> = MutableLiveData()
    private val rep = Repository()

    fun addReservation(uid:String, buid:String,  rd:AddReservation){
        Log.d("addResrvationviewmodel", uid.toString())
        Log.d("addResrvationviewmodel", buid.toString())
        Log.d("addResrvationviewmodel", rd.toString())
        rep.addReservation(uid, buid, rd){ response ->
            response?.let{
                myReservation.value = it
            }
        }
    }
}