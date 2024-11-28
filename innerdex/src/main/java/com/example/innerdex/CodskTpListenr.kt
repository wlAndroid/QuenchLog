package com.example.innerdex

import com.tradplus.ads.base.bean.TPAdInfo

interface CodskTpListenr {

    fun showSuc(info: TPAdInfo?)

    fun loadSuc(info: TPAdInfo?)

    fun loadFail(e: String?)

    fun showFail(e: String?)

    fun close()
}