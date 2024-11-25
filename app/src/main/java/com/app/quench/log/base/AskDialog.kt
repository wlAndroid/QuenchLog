package com.app.quench.log.base

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.app.quench.log.databinding.DialogDeleteBinding

class AskDialog(
    var callback: () -> Unit
) : DialogFragment() {

    private lateinit var binding: DialogDeleteBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogDeleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.delete.setOnClickListener {
            callback()
            dismiss()
        }
        binding.cancel.setOnClickListener {
            dismiss()
        }
    }


    override fun onStart() {
        super.onStart()

        val window = dialog?.window ?: return
        window.setBackgroundDrawable(null)
//            val attributes = window.attributes

//        val displayMetrics = requireContext().resources.displayMetrics
//        val margin = displayMetrics.density * 28f
//        attributes.width = (displayMetrics.widthPixels - margin * 2).toInt()
//            attributes.width = (displayMetrics.widthPixels * 0.9f).toInt()
//            window.attributes = attributes
        window.setGravity(Gravity.CENTER)
    }
}