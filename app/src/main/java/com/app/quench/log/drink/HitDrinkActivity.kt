package com.app.quench.log.drink

import android.view.View
import androidx.core.view.postDelayed
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.app.quench.log.R
import com.app.quench.log.adapter.RulerAdapter
import com.app.quench.log.base.BaseActivity
import com.app.quench.log.base.Drink
import com.app.quench.log.base.entity
import com.app.quench.log.databinding.ActivityHitDrinkBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HitDrinkActivity : BaseActivity<ActivityHitDrinkBinding>(ActivityHitDrinkBinding::inflate) {


    var water = 200
    override fun initView() {

        window.statusBarColor=getColor(R.color.color_bg)
        cup(0)
        binding.ruler.postDelayed(100) {
            binding.rulerText.text = (water).toString()
            extracted(binding.ruler, 50, 76, (water - 50) / 10) {
                binding.rulerText.text = it.toString()
                water = it
            }
        }
    }

    private fun extracted(recyclerView: RecyclerView, start: Int, count: Int, post: Int, callback: (Int) -> Unit) {

        recyclerView.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false))
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        val fl = resources.displayMetrics.density * 24
//        val fl = 0f
        val leftRightPadding = (recyclerView.width - fl) / 2
        recyclerView.setPadding(leftRightPadding.toInt(), 0, leftRightPadding.toInt(), 0)
        recyclerView.setClipToPadding(false)

        val lineAdapter = RulerAdapter(start, count)
//        lineAdapter.index = post
        recyclerView.adapter = lineAdapter
        recyclerView.postDelayed(100) {
//            recyclerView.scrollToPosition(post)
            (recyclerView.layoutManager as  LinearLayoutManager).scrollToPositionWithOffset(post, 0)
        }

        // 滚动监听
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var tempTime = 0L
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                // 停止动作
                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    // 防止快速滚动处理
//                    if (System.currentTimeMillis() - tempTime < 200) {
                    if (System.currentTimeMillis() - tempTime < 40) {
                        return
                    }
                    binding.rulerText.visibility = View.VISIBLE
//                    binding.trans.visibility = View.VISIBLE
//                    binding.line.visibility = View.VISIBLE
                    val centerView = snapHelper.findSnapView(recyclerView.layoutManager)
                    if (centerView != null) {
                        // 获取对应的刻度值
                        val index = recyclerView.layoutManager?.getPosition(centerView) ?: 0

//                        lineAdapter.index = index
//                        lineAdapter.notifyItemChanged(index)
//                        centerView.visibility = View.INVISIBLE
//                        centerView.isSelected=true
                        callback(lineAdapter.value(index))
//                        kotlin.runCatching {
//                            val layoutParams=centerView.layoutParams as RecyclerView.LayoutParams
//                            layoutParams.viewLayoutPosition
//                        }
                    }
                    tempTime = System.currentTimeMillis()
                } else if (RecyclerView.SCROLL_STATE_DRAGGING == newState || RecyclerView.SCROLL_STATE_SETTLING == newState) {
                    binding.rulerText.visibility = View.INVISIBLE
//                    binding.trans.visibility = View.INVISIBLE
//                    binding.line.visibility = View.INVISIBLE
                }
            }
        })
    }

    override fun click() {
        binding.back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.cup1.setOnClickListener { cup(0) }
        binding.cup2.setOnClickListener { cup(1) }
        binding.cup3.setOnClickListener { cup(2) }
        binding.cup4.setOnClickListener { cup(3) }
        binding.cup5.setOnClickListener { cup(4) }
        binding.cup6.setOnClickListener { cup(5) }

        binding.save.setOnClickListener {
            lifecycleScope.launch {
                val drink = Drink(water, System.currentTimeMillis(), cup)
                val s = withContext(Dispatchers.IO) {
                    db.dao().add(drink.entity)
                }
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private var cup = 0
    private fun cup(i: Int) {
        cup = i
        binding.cup1.isSelected = i == 0
        binding.cup2.isSelected = i == 1
        binding.cup3.isSelected = i == 2
        binding.cup4.isSelected = i == 3
        binding.cup5.isSelected = i == 4
        binding.cup6.isSelected = i == 5
    }

}