package com.app.quench.log

import android.animation.ValueAnimator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.app.quench.log.base.BaseActivity
import com.app.quench.log.databinding.ActivityMainBinding
import com.app.quench.log.home.HitHomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class HitSplashActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    lateinit var animator: ValueAnimator
    override fun onResume() {
        super.onResume()
        if (animator.isStarted) {
            animator.resume()
        } else {
            animator.start()
        }
    }

    override fun onPause() {
        super.onPause()
        animator.pause()
    }

    override fun initView() {
        val i = window.decorView.systemUiVisibility
        window.decorView.systemUiVisibility = i or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        animator = ValueAnimator.ofInt(0, 360).apply {
            duration = 1200
            repeatCount = -1
            addUpdateListener {
                changeProgress(it.animatedValue as Int)
            }
        }

        lifecycleScope.launch {
            delay(Random.nextLong(1000, 2987))
            toNext()
        }
    }

    private fun toNext() {
        startAAA(HitHomeActivity::class.java)
        finish()
    }

    private fun changeProgress(i: Int) {
        binding.loadingProgress.rotation = i.toFloat()
    }

    override fun click() {

    }
}