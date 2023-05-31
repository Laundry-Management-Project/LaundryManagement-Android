package project.laundry.presentation.view

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import project.laundry.databinding.ImagePagerItemBinding
import project.laundry.databinding.ImageRecyclerItemBinding

class StoreImagePagerAdapter(private val ctx : Context, private val images: List<Bitmap>) : RecyclerView.Adapter<StoreImagePagerAdapter.ImageViewHolder>() {
    // ViewHolder 클래스 정의와 onCreateViewHolder, onBindViewHolder 등의 메서드 구현

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImagePagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreImagePagerAdapter.ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position]
        holder.bind(ctx, image)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ImageViewHolder(private val binding : ImagePagerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ctx:Context, bitmap : Bitmap){



            val requestOptions = RequestOptions()
                .override(1200, 1200) // 원하는 크기로 조정합니다.

            Glide.with(ctx)
                .asBitmap()
                .load(bitmap) // 비트맵 이미지를 로드합니다.
                .apply(requestOptions)
                .into(binding.storeImage)
        }
    }
}