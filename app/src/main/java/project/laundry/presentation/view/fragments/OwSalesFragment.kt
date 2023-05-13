package project.laundry.presentation.view.fragments

import android.content.Context
import android.os.Bundle
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

        binding.monthYearTv.text = LocalDate.now().year.toString() + LocalDate.now().month.toString()
        val mLayoutManager = GridLayoutManager(requireContext(), 7)

        // recyclerView orientation (가로 방향 스크롤 설정)
        binding.calendarList.layoutManager = mLayoutManager


        viewModel.getCalendarInfo(buId, "10", "5", "2023")
        viewModel.calendarData.observe(viewLifecycleOwner){
            setListView(it.daily_income)
            binding.tvIncomeMonth.text = it.current_month_income.toString()
            binding.tvVisitorMonth.text = it.current_month_visitor.toString()
        }


        return binding.root
    }

    private fun setListView(dailyIncome : ArrayList<Income>) {
        // 현재 달의 마지막 날짜
        val lastDayOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth())
        lastDayOfMonth.format(DateTimeFormatter.ofPattern("dd"))

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val tempDate = calendar.get(Calendar.DAY_OF_WEEK)-1


        for(i:Int in 1 .. tempDate){
            itemList.add(DateItems("", ""))
        }
        for(i: Int in 1..LocalDate.now().dayOfMonth) {
            val date = LocalDate.of(LocalDate.now().year, LocalDate.now().month, i)
            val dayOfWeek: DayOfWeek = date.dayOfWeek
            dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US)

            itemList.add(DateItems(i.toString(), dailyIncome.get(i-1).price.toString()))
        }
        binding.calendarList.adapter = listAdapter
    }

}