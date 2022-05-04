package com.trust.walletclone.dialog

import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.trust.walletclone.R
import com.trust.walletclone.activity.PhraseActivity
import com.trust.walletclone.databinding.DialogSendTokenBinding
import com.trust.walletclone.databinding.DialogSendTokenSelectBinding
import com.trust.walletclone.fragment.TokenFragment
import com.trust.walletclone.util.Coin
import org.web3j.protocol.Web3j

class SendTokenDialog(var coin: Coin, var max: String) : BottomSheetDialogFragment() {

    private lateinit var binding: DialogSendTokenBinding
    var onSend: ((String, Float, String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogBg)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogSendTokenBinding.inflate(inflater)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.toolbar.title.text = "Send ${coin.symbol}"
        binding.amountEditor.hint = "Amount ${coin.symbol}"
        binding.address.addTextChangedListener{watcher ->
            if(watcher!!.isNotEmpty())
                binding.btnPaste.visibility = View.GONE
            else{
                binding.btnPaste.visibility = View.VISIBLE
            }
        }
        binding.btnPaste.setOnClickListener {
            val clipAddress = (requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).primaryClip?.getItemAt(0)?.text.toString()
            binding.address.setText(clipAddress)
        }
        binding.toolbar.btnBack.setOnClickListener {
            dismiss()
        }
        binding.btnMaxvalue.setOnClickListener {
            binding.amount.setText(max)
        }
        binding.btnSend.setOnClickListener {
            if(binding.address.text?.length == 42 && binding.amount.text.toString().toFloat() > 0)
            onSend?.invoke(binding.address.text.toString(), binding.amount.text.toString().toFloat(), binding.memo.toString())
            else{
                Toast.makeText(requireContext(),"Please input Valid address and amount",Toast.LENGTH_SHORT).show()
            }
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