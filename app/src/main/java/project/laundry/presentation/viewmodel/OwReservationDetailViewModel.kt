package project.laundry.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.laundry.data.dataclass.Reservation
import project.laundry.data.repository.Repository

class OwReservationDetailViewModel:ViewModel() {
    val reservationData : MutableLiveData<Reservation> = MutableLiveData()

    fun getReservation(reId:String){
        val rep = Repository()
        //rep.getReservation()
    }
}