package project.laundry.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import project.laundry.data.dataclass.CalendarInfo
import project.laundry.data.dataclass.Income
import project.laundry.data.repository.Repository

class OwSalesViewModel : ViewModel() {
    val calendarData : MutableLiveData<CalendarInfo> = MutableLiveData()

    fun test(){
        val income1 = ArrayList<Income>();
        val income2 = ArrayList<Income>();
        for(i in 1..30){
            income1.add(Income(i,i));
        }
        for(i in 1..12){
            income2.add(Income(i,i));
        }
        val t = CalendarInfo(0,0,0,0,income1,income2)
        calendarData.value=t
    }
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