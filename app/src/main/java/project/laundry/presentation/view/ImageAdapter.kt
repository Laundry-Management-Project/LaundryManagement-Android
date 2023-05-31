package project.laundry.presentation.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import project.laundry.data.App
import project.laundry.data.dataclass.Store
import project.laundry.databinding.ImageRecyclerItemBinding
import project.laundry.databinding.ItemRecyclerStoreListBinding
import project.laundry.presentation.view.activity.OwnerMainActivity

class ImageAdapter(private val ctx: Context, private val items: ArrayList<Uri>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ImageRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ctx: Context, item: Uri) {

            val requestOptions = RequestOptions()
                .override(400, 300)// 원하는 크기로 조정합니다.

            Glide.with(ctx)
                .load(item)
                .apply(requestOptions)
                .into(binding.imageView)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ImageRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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