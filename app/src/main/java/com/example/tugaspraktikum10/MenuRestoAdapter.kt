package com.example.tugaspraktikum10
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tugaspraktikum10.resto.MenuResto
import kotlinx.android.synthetic.main.menuresto_item.view.*

class MenuRestoAdapter : ListAdapter<MenuResto, MenuRestoAdapter.MenuRestoHolder> (DIFF_CALLBACK){
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MenuResto>() {
            override fun areItemsTheSame(oldItem: MenuResto, newItem: MenuResto): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: MenuResto, newItem: MenuResto): Boolean {
                return oldItem.nama == newItem.nama && oldItem.deskripsi == newItem.deskripsi
                        && oldItem.rate == newItem.rate
            }
        }
    }
    private var listener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuRestoHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.menuresto_item, parent, false)
        return MenuRestoHolder(itemView)
    }
    override fun onBindViewHolder(holder: MenuRestoHolder, position: Int) {
        val currentMenuResto: MenuResto = getItem(position)
        holder.textViewTitle.text = currentMenuResto.nama
        holder.textViewPriority.text = currentMenuResto.rate.toString()
        holder.textViewDescription.text = currentMenuResto.deskripsi
    }
    fun getMenuRestoAt(position: Int): MenuResto {
        return getItem(position)
    }
    inner class MenuRestoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }
        var textViewTitle: TextView = itemView.text_view_nama
        var textViewPriority: TextView = itemView.text_view_rate
        var textViewDescription: TextView = itemView.text_view_deskripsi
    }
    interface OnItemClickListener {
        fun onItemClick(note: MenuResto)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}