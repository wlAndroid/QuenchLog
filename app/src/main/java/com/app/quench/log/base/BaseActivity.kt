package com.app.quench.log.base

import android.content.Intent
import android.graphics.Canvas
import android.icu.util.Calendar
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.app.quench.log.HitApp
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

abstract class BaseActivity<T : ViewBinding>(private val function: (LayoutInflater) -> T) : AppCompatActivity() {

    lateinit var binding: T
        private set


    open val lightStatusBar: Boolean = true

    val db by lazy {
        val hitApp = application as HitApp
        hitApp.db
    }

    fun drinkHistory(collector: FlowCollector<List<Drink>>) {
        lifecycleScope.launch {
            val c = db.dao().list()
            c.map { it.map { it.bean } }.collect(collector)
        }
    }


    fun setItemTouchHelper(recyclerView: RecyclerView, width: Int = (70f * resources.displayMetrics.density).toInt()) {
        val adapter = recyclerView.adapter ?: return
        ItemTouchHelper(object : ItemTouchHelper.Callback() {

            private val limitScrollX = width
            private var currentScrollX = 0
            private var currentScrollXWhenInActive = 0
            private var initXWhenInActive = 0f
            private var firstInActive = false
            var leftSwipeChecker = false
            private var handler = Handler(Looper.getMainLooper())

            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                val dragFlags = 0
                val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                return makeMovementFlags(dragFlags, swipeFlags)
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

            override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
                return Integer.MAX_VALUE.toFloat()
            }

            override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
                return Integer.MAX_VALUE.toFloat()
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    if (viewHolder.itemView.scrollX == 0) {
                        leftSwipeChecker = true
                    }

                    leftSwipeChecker = leftSwipeChecker && dX < 0

                    if (leftSwipeChecker) {
                        recoverSwipedItem(viewHolder, recyclerView)
                        if (viewHolder.itemView.scrollX != 0) {
                            leftSwipeChecker = false
                        }
                    }

                    if (dX == 0f) {
                        currentScrollX = viewHolder.itemView.scrollX
                        firstInActive = true
                    }

                    if (isCurrentlyActive) {
                        var scrollOffset = currentScrollX + (-dX).toInt()
                        if (scrollOffset > limitScrollX) {
                            scrollOffset = limitScrollX
                        } else if (scrollOffset < 0) {
                            scrollOffset = 0
                        }
                        viewHolder.itemView.scrollTo(scrollOffset, 0)
                    } else {
                        if (firstInActive) {
                            firstInActive = false
                            currentScrollXWhenInActive = viewHolder.itemView.scrollX
                            initXWhenInActive = dX
                        }

                        if (viewHolder.itemView.scrollX < limitScrollX) {
                            viewHolder.itemView.scrollTo((currentScrollXWhenInActive * dX / initXWhenInActive).toInt(), 0)
                        }
                    }
                }
            }

            private fun recoverSwipedItem(viewHolder: RecyclerView.ViewHolder, recyclerView: RecyclerView) {

                for (i in adapter.itemCount downTo 0) {
                    val itemView = recyclerView.findViewHolderForAdapterPosition(i)?.itemView

                    if (i != viewHolder.adapterPosition) {

                        itemView?.let {
                            if (it.scrollX > 0) {
                                recoverItemAnim(itemView)
                            }
                        }
                    }

                    itemView?.setOnClickListener {
                        recoverItemAnim(itemView)
                    }
                }
            }

            private fun recoverItemAnim(itemView: View?) {
                itemView?.scrollTo(0, 0)

//            handler.postDelayed({
//                itemView?.scrollTo(20, 0)
//            }, 50)
//
//            handler.postDelayed({
//                itemView?.scrollTo(0, 0)
//            }, 100)
//
//            handler.postDelayed({
//                itemView?.scrollTo(10, 0)
//            }, 200)
//
                handler.postDelayed({
                    itemView?.scrollTo(0, 0)
                }, 300)
            }

            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)

                if (viewHolder.itemView.scrollX > limitScrollX) {
                    viewHolder.itemView.scrollTo(limitScrollX, 0)
                } else if (viewHolder.itemView.scrollX < 0) {
                    viewHolder.itemView.scrollTo(0, 0)
                }
            }

        }).apply {
            attachToRecyclerView(recyclerView)
        }
    }

    fun today(long: Long = System.currentTimeMillis()): Pair<Long, Long> {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = long
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val start = calendar.timeInMillis
        calendar.add(Calendar.DATE, 1)
        val end = calendar.timeInMillis - 1
        return Pair(start, end)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = function.invoke(layoutInflater)
        val root = binding.root
        setContentView(root)
        lightStatusBars(lightStatusBar)

        initView()
        click()
    }

    abstract fun initView()

    abstract fun click()


    fun startAAA(clazz: Class<*>, extras: (() -> Bundle)? = null) {
        startActivity(Intent(this, clazz).apply {
            extras?.let {
                putExtras(extras())
            }
        })
    }

    fun lightStatusBars(isLight: Boolean) {
        //fun2
        val systemUiFlag = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        val decorView = window.decorView
        if (isLight) {
            decorView.systemUiVisibility = (decorView.systemUiVisibility or systemUiFlag)
        } else {
            decorView.systemUiVisibility = (decorView.systemUiVisibility and systemUiFlag.inv())
        }
    }
}