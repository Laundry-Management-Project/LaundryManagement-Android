package project.laundry.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.laundry.data.dataclass.Reservation
import project.laundry.data.repository.Repository

class CuReservationsViewModel : ViewModel() {

    val reservations : MutableLiveData<ArrayList<Reservation>> = MutableLiveData()
    val rep = Repository()

    fun getReservations(uid : String, userType:String){
        rep.getReservations(userType, uid){response ->
            Log.d("getReservation", response.toString())
            response?.let{
                reservations.value = response.reservations
            }
        }
    }
}