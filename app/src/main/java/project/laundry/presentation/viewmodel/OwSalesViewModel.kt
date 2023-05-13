package project.laundry.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.laundry.data.dataclass.CalendarInfo
import project.laundry.data.repository.Repository

class OwSalesViewModel : ViewModel() {
    val calendarData : MutableLiveData<CalendarInfo> = MutableLiveData()

    fun getCalendarInfo(buId:String, day:String,month:String,year:String){
        val rep = Repository()
        rep.getCalendarInfo(buId, day,month,year){response->
            response?.let{
                Log.d("calendar", it.toString())
                calendarData.value = it
            }
        }
    }
}