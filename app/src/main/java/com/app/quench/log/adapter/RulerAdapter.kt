package com.app.quench.log.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.quench.log.databinding.Ruler1Binding
import com.app.quench.log.databinding.Ruler2Binding

class RulerAdapter(var start: Int, var count: Int) : RecyclerView.Adapter<RulerAdapter.AHolder>() {
    class Holder(binding: Ruler2Binding) : AHolder(binding.root, binding.line, binding.value)
    class Holder2(binding: Ruler1Binding) : AHolder(binding.root, binding.line, null)

    open class AHolder(root: View, var line: View, var value: TextView? = null) : RecyclerView.ViewHolder(root) {
        var density = itemView.getResources().getDisplayMetrics().density;
    }


    override fun getItemViewType(position: Int): Int {
        val value = value(position)/10
        if (value % 5 == 0) {
            return 2
        }
//        if (value % 2 == 0) {
//            return 1
//        }
        return 0
    }

    fun value(position: Int) = start + position * 10


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AHolder {
        val from = LayoutInflater.from(parent.context)
        return if (viewType > 0) {
            Holder(Ruler2Binding.inflate(from, parent, false))
        } else {
            Holder2(Ruler1Binding.inflate(from, parent, false))
        }
    }

    override fun getItemCount() = count

    override fun onBindViewHolder(holder: AHolder, position: Int) {
        val i = value(position)
        if (i % 5 == 0) {
            holder.value?.text = "$i"
        } else {
            holder.value?.text = ""
        }

    }
}
