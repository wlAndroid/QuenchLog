package com.app.quench.log.base

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.app.quench.log.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SettingDialog(var int: Int, var call: (Int) -> Unit) : BottomSheetDialogFragment(R.layout.dialog_edit) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val save = view.findViewById<View>(R.id.save)
        val edit = view.findViewById<EditText>(R.id.edit)
        edit.setText(int.toString())

        save.setOnClickListener {
            val trim = edit.text.trim()
            if (trim.isEmpty()) {
                Toast.makeText(context, "Please input goals", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val s = kotlin.runCatching {
                trim.toString().toInt()
            }.getOrDefault(0)

            if (s !in 1000..10000) {
                Toast.makeText(context, "Please input goals (1000-10000)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            dismiss()
            call(s)
        }
    }

//    var dismiss:()->Unit={}
//    override fun onDismiss(dialog: DialogInterface) {
//        super.onDismiss(dialog)
//        dismiss.invoke()
//    }

//    override fun onStart() {
//        super.onStart()
//
//        val window = dialog?.window ?: return
//        window.setBackgroundDrawable(null)
//
//    }
//
//    @SuppressLint("RestrictedApi")
//    override fun setupDialog(dialog: Dialog, style: Int) {
//        super.setupDialog(dialog, style)
//        (view?.getParent() as? View)?.setBackgroundColor(Color.TRANSPARENT)
//    }

}