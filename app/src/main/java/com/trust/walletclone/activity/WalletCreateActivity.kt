package com.trust.walletclone.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.trust.walletclone.R
import com.trust.walletclone.databinding.ActivityWalletCreateBinding
import com.trust.walletclone.databinding.ActivityWalletImportBinding
import com.trust.walletclone.dialog.LegalDialog
import com.trust.walletclone.fragment.*


private const val NUM_PAGES = 4
private const val MIN_SCALE = 0.75f


class WalletCreateActivity : AppCompatActivity() {
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private lateinit var viewPager: ViewPager2
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    lateinit var btnCreateWallet: Button
    private lateinit var binding: ActivityWalletCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.slider.setPageTransformer(DepthPageTransformer())
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        binding.slider.adapter = pagerAdapter
        binding.btnCreate.setOnClickListener {
            LegalDialog().show(supportFragmentManager, "MyFullDialog")
        }
        binding.btnImport.setOnClickListener {
            var intent = Intent(this, WalletImportActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    /**
     * A simple pager adapter that represents 4 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES
        override fun createFragment(position: Int) :Fragment {
            return when (position) {
                0 -> Slider1()
                1 -> Slider2()
                2 -> Slider3()
                else -> Slider4()
            }
        }
    }

    @RequiresApi(21)
    private inner class DepthPageTransformer : ViewPager2.PageTransformer {
        override fun transformPage(view: View, position: Float) {
            view.apply {
                val pageWidth = width
                when {
                    position < -1 -> { // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        alpha = 0f
                    }
                    position <= 0 -> { // [-1,0]
                        // Use the default slide transition when moving to the left page
                        alpha = 1f
                        translationX = 0f
                        translationZ = 0f
                        scaleX = 1f
                        scaleY = 1f
                    }
                    position <= 1 -> { // (0,1]
                        // Fade the page out.
                        alpha = 1 - position

                        // Counteract the default slide transition
                        translationX = pageWidth * -position
                        // Move it behind the left page
                        translationZ = -1f

                        // Scale the page down (between MIN_SCALE and 1)
                        val scaleFactor = (MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position)))
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                    }
                    else -> { // (1,+Infinity]
                        // This page is way off-screen to the right.
                        alpha = 0f
                    }
                }
            }
        }
    }
}