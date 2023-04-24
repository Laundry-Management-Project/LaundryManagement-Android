package project.laundry.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.laundry.data.dataclass.Reservation
import project.laundry.data.repository.Repository

class ReservationsViewModel : ViewModel() {

    val reservations : MutableLiveData<ArrayList<Reservation>> = MutableLiveData()
    val rep = Repository()

    fun getReservations(uid : String, userType:String){
        rep.getReservations(userType, uid){response ->
            response?.let{
                reservations.value = response
            }
        }
    }
}