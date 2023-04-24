package project.laundry.presentation.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.laundry.data.dataclass.Reservation
import project.laundry.databinding.OwReRecyclerItemBinding

class OwReAdapter(private val ctx: Context, private val myReservations : List<Reservation>) : RecyclerView.Adapter<OwReAdapter.ViewHolder>(){
    class ViewHolder(private val binding : OwReRecyclerItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(ctx: Context, rd : Reservation){
            binding.linearLayout.setOnClickListener {
            }
            binding.clientName.text = rd.cu_name
            binding.clientLaundry.text = rd.num.toString()
            binding.clientDate.text = rd.createdAt
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