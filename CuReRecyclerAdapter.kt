package project.laundry.presentation.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.laundry.data.dataclass.Reservation
import project.laundry.databinding.ReRecyclerItemBinding
import project.laundry.presentation.view.activity.StoreDetailActivity

class CuReRecyclerAdapter(val ctx : Context, val items : ArrayList<Reservation>) : RecyclerView.Adapter<CuReRecyclerAdapter.ViewHolder>() {
    class ViewHolder(val binding : ReRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ctx : Context, item : Reservation){
            binding.clothingType.text = item.clothing_type
            binding.storeName.text = item.bu_name
            binding.num.text = item.num.toString()
            binding.details.text = item.request_detail
            binding.status.text = item.cloth_status

            binding.layout.setOnClickListener {
                val intent = Intent(ctx, StoreDetailActivity::class.java)
                intent.putExtra("bu_id", item.bu_id)
                ctx.startActivity(intent)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuReRecyclerAdapter.ViewHolder {
        val binding = ReRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CuReRecyclerAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CuReRecyclerAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(ctx, item)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}