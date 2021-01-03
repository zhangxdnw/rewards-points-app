package cn.zxd.app.banner

import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.youth.banner.adapter.BannerAdapter


class ImageBannerAdapter(val fragment: Fragment, dataList: MutableList<ImageDataBean>?) :
    BannerAdapter<ImageDataBean, ImageBannerAdapter.BannerViewHolder>(dataList) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent!!.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(
        holder: BannerViewHolder?,
        data: ImageDataBean?,
        position: Int,
        size: Int
    ) {
        if (data?.resType == 0) {
            data.resId?.let { (holder?.itemView as ImageView).setImageResource(it) }
        } else {
            Glide.with(fragment).load(data?.resUrl).into(holder?.itemView as ImageView)
        }
    }

    class BannerViewHolder(itemView: ImageView) : RecyclerView.ViewHolder(itemView)
}
