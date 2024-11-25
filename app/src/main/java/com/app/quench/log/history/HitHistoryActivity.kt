package com.app.quench.log.history

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.app.quench.log.R
import com.app.quench.log.adapter.DrinkAdapter
import com.app.quench.log.base.AskDialog
import com.app.quench.log.base.BaseActivity
import com.app.quench.log.base.Drink
import com.app.quench.log.base.entity
import com.app.quench.log.databinding.ActivityHitHistoryBinding
import kotlinx.coroutines.launch

class HitHistoryActivity : BaseActivity<ActivityHitHistoryBinding>(ActivityHitHistoryBinding::inflate) {

    val adapter by lazy {
        DrinkAdapter()
    }

    override fun initView() {
        window.statusBarColor = getColor(R.color.color_bg)

        binding.drinkList.adapter = adapter
        setItemTouchHelper(binding.drinkList)

        drinkHistory {
            changeUI(it)
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
        binding.back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun changeUI(todayList: List<Drink>) {
        adapter.submitList(todayList)
        if (todayList.isEmpty()) {
            binding.noText.visibility = View.VISIBLE
        } else {
            binding.noText.visibility = View.GONE
        }
    }

}