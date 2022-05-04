package com.trust.walletclone.dialog

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.trust.walletclone.R
import com.trust.walletclone.activity.BrowserActivity
import com.trust.walletclone.activity.PasswordActivity
import com.trust.walletclone.activity.WalletCreateActivity
import com.trust.walletclone.databinding.DialogLegalBinding

class LegalDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogLegalBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogLegalBinding.inflate(inflater)
        initView()
        return binding.root
    }

    private fun initView() {
        //do something
        binding.accept.setOnCheckedChangeListener { compoundButton, b ->
            if(b)
            binding.btnContinue.setBackgroundColor(ResourcesCompat.getColor(getResources(),  R.color.primary , null))
            else
                binding.btnContinue.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.gray100, null))
        }
        binding.btnContinue.setOnClickListener {
            if(binding.accept.isChecked){
                val intent = Intent(context, PasswordActivity::class.java)
                startActivity(intent)
                dismiss()
            }
        }
        binding.btnPolicy.setOnClickListener{
            val intent = Intent(context, BrowserActivity::class.java)
            intent.putExtra("URL","https://trustwallet.com/privacy-policy")
            startActivity(intent)
        }
        binding.btnTerms.setOnClickListener{
            val intent = Intent(context, BrowserActivity::class.java)
            intent.putExtra("URL","https://trustwallet.com/terms-of-services")
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        //拿到系统的 bottom_sheet
        val view: FrameLayout = dialog?.findViewById(R.id.design_bottom_sheet)!!
        //设置view高度
        view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        //获取behavior
        val behavior = BottomSheetBehavior.from(view)
        //设置弹出高度
        behavior.peekHeight = 3000
        //设置展开状态
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