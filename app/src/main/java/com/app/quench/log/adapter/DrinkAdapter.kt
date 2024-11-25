package com.app.quench.log.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.quench.log.base.Drink
import com.app.quench.log.databinding.ItemDrinkBinding

import java.text.SimpleDateFormat
import java.util.*

class DrinkAdapter : ListAdapter<Drink, DrinkAdapter.Holder>(object : DiffUtil.ItemCallback<Drink>() {
    override fun areItemsTheSame(p0: Drink, p1: Drink): Boolean {
        return p0.id == p1.id
    }

    override fun areContentsTheSame(p0: Drink, p1: Drink): Boolean {
        return p0.water == p1.water
                && p0.cup == p1.cup
//               &&p0.date==p1.date
                && p0.date == p1.date
    }

}) {
    class Holder(var binding: ItemDrinkBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val from = LayoutInflater.from(parent.context)
        val view = ItemDrinkBinding.inflate(from, parent, false)
//        view.remove.visibility=View.VISIBLE
        return Holder(view)
    }

    var click: (Drink) -> Unit = {}
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val drink = getItem(position)

        holder.itemView.post {
            holder.itemView.scrollTo(0, 0)
        }

        kotlin.runCatching {
            holder.binding.drinkTime.text = format.format(drink.date)
        }

        val (cup, name) = com.app.quench.log.base.list.get(drink.cup)
        holder.binding.drinkText.setText(name)
        holder.binding.drinkIcon.setImageResource(cup)
        holder.binding.drink.text = "${drink.water}ml"

        holder.binding.drinkDelete.setOnClickListener {
            click(drink)
        }

    }

    private val format by lazy {
        SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
    }
}