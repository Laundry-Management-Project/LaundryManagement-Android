package project.laundry.presentation.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.laundry.data.dataclass.Reservation
import project.laundry.databinding.OwReRecyclerItemBinding
import project.laundry.presentation.view.activity.OwReservationDetailActivity

class OwReAdapter(private val ctx: Context, private val myReservations : List<Reservation>) : RecyclerView.Adapter<OwReAdapter.ViewHolder>(){
    class ViewHolder(private val binding : OwReRecyclerItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(ctx: Context, rd : Reservation){
            binding.layout.setOnClickListener {
                val intent = Intent(ctx, OwReservationDetailActivity::class.java)
                intent.putExtra("re_id", rd.re_id)
                ctx.startActivity(intent)
            }
            binding.storeName.text = rd.cu_name
            binding.num.text = rd.num.toString() + "개"
            binding.date.text = rd.createdAt
            binding.clothingType.text = rd.clothing_type
            binding.details.text = rd.request_detail
            binding.status.text = when(rd.cloth_status){
                "WASH_BEFORE" -> "세탁 전"
                "WASH_IN_PROCESS" -> "세탁 중"
                "WASH_COMPLETE" -> "세탁 완료"
                else -> "error"
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = OwReRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reservation = myReservations[position]
        holder.bind(ctx, reservation)
    }

    override fun getItemCount(): Int {
        return myReservations.size
    }
}