package com.app.quench.log.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.quench.log.R
import com.app.quench.log.base.Drink
import com.app.quench.log.databinding.ItemCupBinding
import com.app.quench.log.databinding.ItemDrinkBinding

import java.text.SimpleDateFormat
import java.util.*

class CupAdapter : ListAdapter<Drink, CupAdapter.Holder>(object : DiffUtil.ItemCallback<Drink>() {
    override fun areItemsTheSame(p0: Drink, p1: Drink): Boolean {
        return p0.id == p1.id
    }

    override fun areContentsTheSame(p0: Drink, p1: Drink): Boolean {
        return p0.water == p1.water
                && p0.cup == p1.cup
//               &&p0.date==p1.date
//                && p0.date == p1.date
    }

}) {
    class Holder(var binding: ItemCupBinding) : RecyclerView.ViewHolder(binding.root)


    var add: () -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val from = LayoutInflater.from(parent.context)
        val view = ItemCupBinding.inflate(from, parent, false)
//        view.remove.visibility=View.VISIBLE
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val drink = getItem(position)

        if (drink.id == 0 || drink.water == 0) {
            holder.binding.cupName.visibility = View.GONE
            holder.binding.cupImage.setImageResource(R.drawable.ic_hit_cup_add)
            holder.binding.cupText.text = "Add"
            holder.binding.root.setOnClickListener {
                add()
            }
        } else {
            holder.binding.cupName.visibility = View.VISIBLE
            val (cup, name) = com.app.quench.log.base.list.get(drink.cup)
            holder.binding.cupName.setText(name)
            holder.binding.cupImage.setImageResource(cup)
            holder.binding.cupText.text = "${drink.water}ml"
            holder.binding.root.setOnClickListener(null)
        }
    }
}