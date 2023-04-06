package project.laundry.ui.owner

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import project.laundry.data.dataclass.StoreListItems
import project.laundry.databinding.ItemRecyclerStoreListBinding

class StoreListAdpater(private val ctx: Context, private val items: List<StoreListItems>) : RecyclerView.Adapter<StoreListAdpater.ViewHolder>() {
    class ViewHolder(private val binding: ItemRecyclerStoreListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ctx: Context, item: StoreListItems) {
            binding.storeLayout.setOnClickListener {
                val intent = Intent(ctx, OwnerMainActivity::class.java)
                ctx.startActivity(intent)
            }
            binding.storeName.text=item.storeName
            binding.storeAddress.text=item.storeAddress
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