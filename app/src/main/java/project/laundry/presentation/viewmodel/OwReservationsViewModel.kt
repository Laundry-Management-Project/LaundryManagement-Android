package project.laundry.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import project.laundry.data.dataclass.PutReservation
import project.laundry.data.dataclass.Reservation
import project.laundry.data.repository.Repository

class OwReservationsViewModel(application: Application): AndroidViewModel(application) {

    val reservations : MutableLiveData<ArrayList<Reservation>> = MutableLiveData()
    val rep = Repository()


    val loading = MutableLiveData<Boolean>()
    fun loadReservations(userType : String, buId:String){
        loading.value=true
        rep.getReservations(userType, buId){ response ->
            response?.let{
                reservations.value = it.reservations
            }
            loading.value=false
        }
    }

    fun putReservation(buId:String, reId : String, rd : PutReservation){
        rep.putReservation(buId, reId, rd){response ->
            response?.let{
                reservations.value = it.reservations
            }
        }
    }
}