package com.example.innerdex

import com.tradplus.ads.base.bean.TPAdError
import com.tradplus.ads.base.bean.TPAdInfo
import com.tradplus.ads.open.interstitial.InterstitialAdListener

class CdsTpListe (private val listener: CodskTpListenr) : InterstitialAdListener {

    override fun onAdLoaded(p0: TPAdInfo?) {
        listener.loadSuc(p0)
    }

    override fun onAdFailed(p0: TPAdError?) {
        listener.loadFail(p0?.errorMsg)
    }

    override fun onAdImpression(p0: TPAdInfo?) {
        listener.showSuc(p0)
    }

    override fun onAdClicked(p0: TPAdInfo?) {

    }

    override fun onAdClosed(p0: TPAdInfo?) {
        listener.close()
    }

    override fun onAdVideoError(p0: TPAdInfo?, p1: TPAdError?) {
        listener.showFail(p1?.errorMsg)
    }

    override fun onAdVideoStart(p0: TPAdInfo?) {

    }

    override fun onAdVideoEnd(p0: TPAdInfo?) {

    }
}