package com.trust.walletclone.dialog

import android.R.attr.bitmap
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.content.DialogInterface
import android.graphics.Point
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.zxing.WriterException
import com.trust.walletclone.R
import com.trust.walletclone.activity.MainActivity
import com.trust.walletclone.adapter.ReceiveTokenAdapter
import com.trust.walletclone.databinding.DialogReceiveTokenSelectBinding
import com.trust.walletclone.util.Coin
import com.trust.walletclone.util.DBHelper
import wallet.core.jni.CoinType


class ReceiveTokenSelectDialog() : BottomSheetDialogFragment() {

    private lateinit var binding: DialogReceiveTokenSelectBinding
    var coinList :ArrayList<Coin> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogBg)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogReceiveTokenSelectBinding.inflate(inflater)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.toolbar.btnBack.setOnClickListener {
            dismiss()
        }
        binding.toolbar.edtSearch.hint = (getString(R.string.lbl_search_receive))
        binding.toolbar.edtSearch.addTextChangedListener {
            binding.toolbar.btnClear.visibility = View.VISIBLE
        }
        binding.toolbar.btnClear.setOnClickListener {
            binding.toolbar.edtSearch.text.clear()
        }
        coinList = DBHelper(requireContext()).readAllCoins()

        binding.listTokens.layoutManager = LinearLayoutManager(context)
        binding.listTokens.adapter = ReceiveTokenAdapter(coinList, context)


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