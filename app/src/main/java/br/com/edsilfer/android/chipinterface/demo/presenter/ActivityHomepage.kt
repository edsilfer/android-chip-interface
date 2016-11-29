package br.com.edsilfer.android.chipinterface.demo.presenter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import br.com.edsilfer.android.chipinterface.demo.FakeDataProvider
import br.com.edsilfer.android.chipinterface.demo.R
import br.com.edsilfer.android.chipinterface.model.intf.ChipControl
import br.com.edsilfer.android.chipinterface.model.Presets
import br.com.edsilfer.kotlin_support.extensions.paintStatusBar
import kotlinx.android.synthetic.main.activity_homepage.*

/**
 * Created by User on 24/11/2016.
 */

class ActivityHomepage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        chipEditText.setChipStyle(Presets.preset01())
        chipEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val parts = chipEditText.text.toString().split(" ");
                if (parts.last().length > 3 && p3 != 0) {
                    FakeDataProvider.provideChats()
                            .filter { it.getHeader().toLowerCase().contains(parts.last()) }
                            .forEach {
                                (chipEditText as ChipControl).addChip(it, parts.last())
                            }
                }
            }
        })
    }
}



