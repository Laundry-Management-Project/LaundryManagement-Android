package project.laundry.presentation.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.laundry.data.dataclass.Store
import project.laundry.databinding.BuRecyclerItemBinding
import project.laundry.presentation.view.activity.StoreDetailActivity

class CuStoresAdapter(private val ctx : Context, private val items : ArrayList<Store>) : RecyclerView.Adapter<CuStoresAdapter.ViewHolder>() {
    class ViewHolder(val binding : BuRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(ctx :Context, item: Store){
            binding.storeName.text = item.name
            binding.layout.setOnClickListener {
                val intent = Intent(ctx, StoreDetailActivity::class.java)
                intent.putExtra("bu_id", item.bu_id)
                ctx.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BuRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(ctx, item)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}