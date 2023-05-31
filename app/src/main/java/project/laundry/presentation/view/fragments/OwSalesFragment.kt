package project.laundry.presentation.view.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import project.laundry.data.App
import project.laundry.data.dataclass.DateItems
import project.laundry.data.dataclass.Income
import project.laundry.databinding.FragmentSalesOwBinding
import project.laundry.presentation.view.CalendarAdapter
import project.laundry.presentation.viewmodel.OwSalesViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.*

class OwSalesFragment : Fragment() {
    lateinit var binding : FragmentSalesOwBinding

    val uid = App.prefs.uid!!
    val userType = App.prefs.userType!!
    val buId = App.prefs.buId!!

    private val itemList = arrayListOf<DateItems>()
    private val listAdapter = CalendarAdapter(itemList)

    val viewModel = OwSalesViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSalesOwBinding.inflate(layoutInflater, container, false)

        binding.monthYearTv.text = LocalDate.now().year.toString() +"년 "+ LocalDate.now().month.value+"월"
        val mLayoutManager = GridLayoutManager(requireContext(), 7)

        // recyclerView orientation (가로 방향 스크롤 설정)
        binding.calendarList.layoutManager = mLayoutManager

        val now = LocalDate.now()
        var day = now.dayOfMonth
        var month = now.monthValue
        var year = now.year

        binding.preMonthBtn.setOnClickListener {
            month -= 1
            viewModel.getCalendarInfo(buId, day.toString(),month.toString(),year.toString())
        }
        binding.nextMonthBtn.setOnClickListener {
            month += 1
            viewModel.getCalendarInfo(buId, day.toString(),month.toString(),year.toString())
        }
        viewModel.getCalendarInfo(buId, day.toString(),month.toString(),year.toString())

//        viewModel.test()
        viewModel.calendarData.observe(viewLifecycleOwner){
            setListView(it.daily_income, day, month, year)
            binding.tvIncomeMonth.text = it.current_month_income.toString()
            binding.tvVisitorMonth.text = it.current_month_visitor.toString()
        }


        return binding.root
    }

    private fun setListView(dailyIncome : ArrayList<Income>, day:Int, month:Int, year:Int) {

        val calendar = Calendar.getInstance()
        calendar.set(month, 1)
        val tempDate = calendar.get(Calendar.DAY_OF_WEEK)-1


        for(i:Int in 1 .. tempDate){
            itemList.add(DateItems("", ""))
        }
        var count = 0


        for(i: Int in 1..LocalDate.now().lengthOfMonth()) {
            val date = LocalDate.of(year,month, i)
            val dayOfWeek: DayOfWeek = date.dayOfWeek
            dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US)

            if(dailyIncome.size>count && dailyIncome[count].date==i) {
                itemList.add(DateItems(i.toString(), "+ " + dailyIncome[count].price.toString()))
                count++
            }
            else{
                itemList.add(DateItems(i.toString(),""))
            }
        }
        binding.calendarList.adapter = listAdapter
    }

}