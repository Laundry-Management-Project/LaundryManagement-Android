package project.laundry.data.dataclass

data class CalendarInfo(
    val current_month_income : Int,
    val current_month_visitor : Int,
    val current_year_income : Int,
    val current_year_visitor : Int,

    val daily_income : ArrayList<Income>,
    val monthly_income : ArrayList<Income>
)

data class Income(
    val date : Int,
    val price : Int
)
