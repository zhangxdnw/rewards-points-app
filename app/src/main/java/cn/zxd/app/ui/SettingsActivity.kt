package cn.zxd.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.zxd.app.databinding.ActivitySettingsBinding
import cn.zxd.app.ui.view.recycler.HorizontalDividerItemDecoration

class SettingsActivity : BaseActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvItems.adapter = SimpleItemAdapter(this, listOf("声音", "高级", "退出程序"))
        binding.rvItems.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvItems.addItemDecoration(HorizontalDividerItemDecoration.Builder(this).build())
        (binding.rvItems.adapter as SimpleItemAdapter).listener =
            object : SimpleItemAdapter.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    when (position) {
                        0 -> {
                            startActivity(Intent(view?.context, VolumeActivity::class.java))
                        }
                        1 -> {
                            startActivity(Intent(view?.context, SeniorSettingsActivity::class.java))
                        }
                        2 -> {

                        }
                    }
                }
            }
    }

    class SimpleItemAdapter(context: Context, data: List<String>) :
        RecyclerView.Adapter<SimpleItemViewHolder>() {

        interface OnItemClickListener {
            fun onItemClick(view: View?, position: Int)
        }

        var list: List<String>? = null
        var context: Context? = null
        var inflater: LayoutInflater? = null
        var listener: OnItemClickListener? = null

        init {
            this.list = data
            this.context = context
            this.inflater = LayoutInflater.from(context)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleItemViewHolder {
            return SimpleItemViewHolder(
                inflater!!.inflate(
                    android.R.layout.simple_list_item_1,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: SimpleItemViewHolder, position: Int) {
            holder.itemView.isClickable = true
            holder.itemView.setOnClickListener {
                listener?.onItemClick(it, position)
            }
            holder.text?.text = list?.get(position)
        }

        override fun getItemCount(): Int {
            return list!!.size
        }

    }

    class SimpleItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text = itemView.findViewById<TextView>(android.R.id.text1)
    }
}