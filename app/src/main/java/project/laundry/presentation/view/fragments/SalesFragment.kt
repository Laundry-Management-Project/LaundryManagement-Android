package project.laundry.presentation.owner.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import project.laundry.data.dataclass.DateItems
import project.laundry.databinding.FragmentSalesBinding
import project.laundry.presentation.owner.CalendarAdapter
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.*

class SalesFragment : Fragment() {
    lateinit var binding : FragmentSalesBinding

    val itemList = arrayListOf<DateItems>()
    val listAdapter = CalendarAdapter(itemList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSalesBinding.inflate(layoutInflater, container, false)


        binding.monthYearTv.text = LocalDate.now().year.toString() + LocalDate.now().month.toString()
        val mLayoutManager = GridLayoutManager(requireContext(), 7)

        // recyclerView orientation (가로 방향 스크롤 설정)
        binding.calendarList.layoutManager = mLayoutManager

        setListView()


        return binding.root
    }

    private fun setListView() {
        // 현재 달의 마지막 날짜
        val lastDayOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth())
        lastDayOfMonth.format(DateTimeFormatter.ofPattern("dd"))

        for(i: Int in 1..lastDayOfMonth.dayOfMonth) {
            val date = LocalDate.of(LocalDate.now().year, LocalDate.now().month, i)
            val dayOfWeek: DayOfWeek = date.dayOfWeek
            dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US)

            itemList.add(DateItems(i.toString(), "+33,000"))
        }
        binding.calendarList.adapter = listAdapter
    }
    companion object {
    }
}