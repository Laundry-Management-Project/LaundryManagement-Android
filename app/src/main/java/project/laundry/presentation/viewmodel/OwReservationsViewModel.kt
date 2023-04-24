package project.laundry.presentation.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.launch
import project.laundry.data.AppDatabase
import project.laundry.data.dataclass.AddReservation
import project.laundry.data.dataclass.ClientData
import project.laundry.data.dataclass.PutReservation
import project.laundry.data.dataclass.Reservation
import project.laundry.data.repository.MyDataBase
import project.laundry.data.repository.Repository

class OwReservationsViewModel(application: Application): AndroidViewModel(application) {

    val reservations : MutableLiveData<ArrayList<Reservation>> = MutableLiveData()
    val rep = Repository()

    fun loadReservations(userType : String, buId:String){
        rep.getReservations(userType, buId){ response ->
            response?.let{
                reservations.value = it
            }
        }
    }

    fun putReservation(buId:String, reId : String, rd : PutReservation){
        rep.putReservation(buId, reId, rd){response ->
            response?.let{
                reservations.value = it
            }
        }
    }
}