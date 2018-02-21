package com.phenomaly.hifamily.libraries.testapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.phenomaly.hifamily.libraries.hifamilydailiesview.view.HiFamilyDailiesView

class MainActivity : AppCompatActivity() {

    lateinit var dailies: HiFamilyDailiesView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dailies = findViewById(R.id.dailies)

        setup()
    }

    private fun setup() {
        val availableDailies = (1..50)
                .map { "date$it" to "Mommy! Don’t forget the folic acid! It’s very important for my development. You can buy it at any pharmacy, and make sure you consult your doctor about proper dosage.$it" }
                .toMap()

        dailies.init(
                currentIndex = 0,
                availableDailies = availableDailies,
                allDailiesCount = 366
        )

        // Test listeners
        dailies.addOnDailySelectedListener {
            Log.i("HiFamilyDailiesView", "Selected $it")
            if (it == 5) {
                Log.i("HiFamilyDailiesView", "Current ${dailies.getCurrentDailyIndex()}")
                dailies.clearDailySelectedListeners()
                dailies.clearOnTouchListeners()
            }
        }
        dailies.addOnTouchListener { Log.i("HiFamilyDailiesView", "Touched $it") }
    }
}
