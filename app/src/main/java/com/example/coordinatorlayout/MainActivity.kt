package com.example.coordinatorlayout

// File: MainActivity.kt
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var overlay: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()
        setupBottomSheet()
        setupViews()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Coordinator Layout Demo"
    }

    private fun setupBottomSheet() {
        val bottomSheet = findViewById<View>(R.id.bottom_sheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        overlay = findViewById(R.id.overlay)

        bottomSheetBehavior.apply {
            isHideable = true

            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            overlay.visibility = View.VISIBLE
                            overlay.alpha = 1f
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            overlay.visibility = View.GONE
                        }
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            overlay.visibility = View.GONE
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    if (slideOffset > 0) {
                        overlay.visibility = View.VISIBLE
                        overlay.alpha = slideOffset
                    } else {
                        overlay.visibility = View.GONE
                    }
                }
            })
        }
    }

    private fun setupViews() {
        // Setup FAB
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        // Setup bottom sheet content
        findViewById<Button>(R.id.btn_bottom_sheet_action).setOnClickListener {
            Toast.makeText(this, "Bottom Sheet Action Clicked!", Toast.LENGTH_SHORT).show()
        }

        // Setup overlay click behavior
        overlay.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}