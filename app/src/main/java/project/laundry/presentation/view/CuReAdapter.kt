package project.laundry.presentation.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.laundry.data.App
import project.laundry.data.dataclass.Reservation
import project.laundry.databinding.CuReRecyclerItemBinding
import project.laundry.presentation.view.activity.StoreDetailActivity

class CuReAdapter(val ctx : Context, val items : ArrayList<Reservation>) : RecyclerView.Adapter<CuReAdapter.ViewHolder>() {
    class ViewHolder(val binding : CuReRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ctx : Context, item : Reservation){
            binding.clothingType.text = item.clothing_type
            binding.storeName.text = item.bu_name
//            binding.num.text = item.num.toString()
            binding.details.text = item.request_detail

            binding.status.text = when(item.cloth_status){
                "WASH_BEFORE" -> "세탁 전"
                "WASH_IN_PROCESS" -> "세탁 중"
                "WASH_COMPLETE" -> "세탁 완료"
                else -> "error"
            }

            binding.layout.setOnClickListener {
                App.prefs.buId=item.bu_id
                val intent = Intent(ctx, StoreDetailActivity::class.java)

                ctx.startActivity(intent)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuReAdapter.ViewHolder {
        val binding = CuReRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CuReAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CuReAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(ctx, item)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}