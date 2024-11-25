package com.app.quench.log.setting

import androidx.core.content.edit
import com.app.quench.log.R
import com.app.quench.log.base.BaseActivity
import com.app.quench.log.base.SettingDialog
import com.app.quench.log.databinding.ActivityHitSettingBinding

class HitSettingActivity : BaseActivity<ActivityHitSettingBinding>(ActivityHitSettingBinding::inflate) {

    private var goal = 2000
    private var last = 2000
    override fun initView() {
        window.statusBarColor = getColor(R.color.color_bg)
        val goal = getSharedPreferences("goal_sp", 0).getInt("goal", 2000)
        last = goal
        xhi(goal)
    }

    override fun click() {
        binding.goalLayout.setOnClickListener {
            SettingDialog(goal) {
                goal = it
                xhi(goal)
            }.show(supportFragmentManager, "setting")
        }
        binding.save.setOnClickListener {
            if (last != goal) {
                getSharedPreferences("goal_sp", 0).edit {
                    putInt("goal", goal)
                }
            }
            onBackPressedDispatcher.onBackPressed()
        }
        binding.back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun xhi(goal: Int) {
        this.goal = goal
        binding.goal.text = "${goal}ml"
        val i = goal / 200
        binding.cup.text = "≈${i}cups "
    }
}