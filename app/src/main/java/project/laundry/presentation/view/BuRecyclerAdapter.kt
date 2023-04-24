package project.laundry.presentation.customer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.laundry.data.dataclass.StoreListItems
import project.laundry.databinding.BuRecyclerItemBinding

class BuRecyclerAdapter(private val ctx : Context, private val items : ArrayList<StoreListItems>) : RecyclerView.Adapter<BuRecyclerAdapter.ViewHolder>() {
    class ViewHolder(val binding : BuRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(ctx :Context, item:StoreListItems){
            binding.storeName.text = item.storeName
            binding.layout.setOnClickListener {
                val intent = Intent(ctx, StoreDetailActivity::class.java)
                intent.putExtra("sid", item.sid)
                ctx.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuRecyclerAdapter.ViewHolder {
        val binding = BuRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuRecyclerAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BuRecyclerAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(ctx, item)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}