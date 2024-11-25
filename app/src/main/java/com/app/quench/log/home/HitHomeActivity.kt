package com.app.quench.log.home

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.app.quench.log.R
import com.app.quench.log.adapter.CupAdapter
import com.app.quench.log.adapter.DrinkAdapter
import com.app.quench.log.base.AskDialog
import com.app.quench.log.base.BaseActivity
import com.app.quench.log.base.Drink
import com.app.quench.log.base.entity
import com.app.quench.log.databinding.ActivityHitHomeBinding
import com.app.quench.log.drink.HitDrinkActivity
import com.app.quench.log.history.HitHistoryActivity
import com.app.quench.log.setting.HitSettingActivity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HitHomeActivity : BaseActivity<ActivityHitHomeBinding>(ActivityHitHomeBinding::inflate),
    SharedPreferences.OnSharedPreferenceChangeListener {

    override fun initView() {
        window.statusBarColor = getColor(R.color.color_accent)
        binding.cupList.adapter = cupAdapter
        binding.drinkList.adapter = adapter

        setItemTouchHelper(binding.drinkList)

        drinkHistory {
            val (start, end) = today()
            val s = it.indexOfFirst {
                it.date <= end
            }
            val e = it.indexOfLast {
                it.date >= start
            }
            val todayList = kotlin.runCatching {
                it.subList(s, e + 1)
            }.getOrDefault(emptyList())
            changeUI(todayList)
        }

        val sp = getSharedPreferences("goal_sp", 0)
        sp.registerOnSharedPreferenceChangeListener(this)
        val int = sp.getInt("goal", 2000)

        binding.goalText.text = "${int}ml"
    }


    val format by lazy {
        SimpleDateFormat("yyyy.MM.dd", Locale.US)
    }

    val cupAdapter by lazy {
        CupAdapter()
    }
    val adapter by lazy {
        DrinkAdapter()
    }

    private var sum = 0
    private fun changeUI(todayList: List<Drink>) {
        binding.toastTodayDate.text = format.format(Date())
        binding.toastCount.text = "Drank ${todayList.size} glasses of water in today."

        sum = todayList.sumOf {
            it.water
        }
        val sp = getSharedPreferences("goal_sp", 0)
        val int = sp.getInt("goal", 2000)
        val fl = (sum * 1f / int).coerceIn(0f, 1f)

        binding.waterText.text = "${sum}ml"
        binding.goalProgress.progress = (fl * 100).toInt()
        binding.thumb.updateLayoutParams<ConstraintLayout.LayoutParams> {
            horizontalBias = fl
        }
        val cup = todayList.toMutableList().apply {
            add(0, Drink())
        }
        cupAdapter.submitList(cup)
        adapter.submitList(todayList)

        if (todayList.isEmpty()) {
            binding.noText.visibility = View.VISIBLE
        } else {
            binding.noText.visibility = View.GONE
        }
    }

    override fun click() {
        adapter.click = {
            AskDialog {
                lifecycleScope.launch {
                    db.dao().delete(it.entity)
                }
            }.show(supportFragmentManager, "ask_dialog")
        }
        cupAdapter.add = {
            startAAA(HitDrinkActivity::class.java)
        }

        binding.viewAll.setOnClickListener {
            startAAA(HitHistoryActivity::class.java)
        }
        binding.toastMenu.setOnClickListener {
            startAAA(HitSettingActivity::class.java)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        val sp = getSharedPreferences("goal_sp", 0)
        sp.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        val sp = getSharedPreferences("goal_sp", 0)
        val int = sp.getInt("goal", 2000)
        val fl = (sum * 1f / int).coerceIn(0f, 1f)

        binding.waterText.text = "${sum}ml"
        binding.goalText.text = "${int}ml"
        binding.goalProgress.progress = (fl * 100).toInt()
        binding.thumb.updateLayoutParams<ConstraintLayout.LayoutParams> {
            horizontalBias = fl
        }
    }

}