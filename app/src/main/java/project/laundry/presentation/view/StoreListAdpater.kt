package project.laundry.presentation.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.laundry.data.dataclass.Store
import project.laundry.data.dataclass.Stores
import project.laundry.databinding.ItemRecyclerStoreListBinding
import project.laundry.presentation.view.activity.OwnerMainActivity

class StoreListAdpater(private val ctx: Context, private val items: ArrayList<Store>) : RecyclerView.Adapter<StoreListAdpater.ViewHolder>() {
    class ViewHolder(private val binding: ItemRecyclerStoreListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ctx: Context, item: Store) {
            binding.storeLayout.setOnClickListener {
                val intent = Intent(ctx, OwnerMainActivity::class.java)
                ctx.startActivity(intent)
            }
            binding.storeName.text=item.name
            binding.storeAddress.text=item.address
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerStoreListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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