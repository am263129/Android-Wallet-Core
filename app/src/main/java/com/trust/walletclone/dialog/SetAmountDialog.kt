package com.trust.walletclone.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.trust.walletclone.R

class SetAmountDialog {
    var onConfirm : ((Float) -> Unit)? = null
    var dialog: Dialog? = null //obj
    fun displayLoadingWithText(context: Context?, text: String?, cancelable: Boolean) { // function -- context(parent (reference))
        dialog = Dialog(context!!)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.dialog_set_amount)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.window!!.attributes.width = (context.resources.displayMetrics.widthPixels*0.90).toInt()
        dialog!!.setCancelable(cancelable)

        val textView = dialog!!.findViewById<TextView>(R.id.label)
        textView.text = text
        try {
            dialog!!.show()
        } catch (e: Exception) {
        }

        dialog!!.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            dialog!!.dismiss()
        }

        dialog!!.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            onConfirm?.invoke(dialog!!.findViewById<EditText>(R.id.amount).text.toString().toFloat())
        }
    }

    fun hideLoading() {
        try {
            if (dialog != null) {
                dialog!!.dismiss()
            }
        } catch (e: Exception) {
        }
    }
}