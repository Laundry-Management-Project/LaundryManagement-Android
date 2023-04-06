package project.laundry.ui.customer

import androidx.lifecycle.ViewModel
import project.laundry.data.dataclass.AddReservation
import project.laundry.data.repository.Repository

class StoreDetailViewModel : ViewModel() {

    fun addReservation(uid : String, rd : AddReservation){
        val rep = Repository()
        rep.addReservation(uid , rd) {response->
            val result = response
        }
    }

}