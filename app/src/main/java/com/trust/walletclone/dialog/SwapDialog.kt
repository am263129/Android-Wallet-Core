package com.trust.walletclone.dialog

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.trust.walletclone.R
import com.trust.walletclone.databinding.DialogSendTokenSelectBinding
import com.trust.walletclone.databinding.DialogSwapBinding

class SwapDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogSwapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogBg)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogSwapBinding.inflate(inflater)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.btnSwap.setOnClickListener {
            dismiss()
        }
        binding.btnPayCoin.setOnClickListener{
            ReceiveTokenSelectDialog().show(childFragmentManager, "Select pay coin")
        }
        binding.btnGetCoin.setOnClickListener {
            ReceiveTokenSelectDialog().show(childFragmentManager, "Select get coin")
        }
    }

    override fun onStart() {
        super.onStart()
        val view: FrameLayout = dialog?.findViewById(R.id.design_bottom_sheet)!!
        view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        val behavior = BottomSheetBehavior.from(view)
        behavior.peekHeight = 3000
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

        })
    }

    override fun onCancel(dialog: DialogInterface) {
        dialog.dismiss()
        super.onCancel(dialog)
    }

    override fun dismiss() {
        hideKeyBoard()
        super.dismiss()
    }

    private fun hideKeyBoard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = dialog?.window?.currentFocus
        view?.let {
            imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}