package br.com.edsilfer.android.chipinterface.demo.presenter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import br.com.edsilfer.android.chipinterface.demo.FakeDataProvider
import br.com.edsilfer.android.chipinterface.demo.R
import br.com.edsilfer.android.chipinterface.model.ChipEvents
import br.com.edsilfer.android.chipinterface.model.intf.ChipControl
import br.com.edsilfer.kotlin_support.extensions.notifySubscribers
import br.com.edsilfer.kotlin_support.extensions.paintStatusBar
import br.com.edsilfer.kotlin_support.service.keyboard.EnhancedTextWatcher
import kotlinx.android.synthetic.main.activity_sample_01.*

/**
 * Created by User on 24/11/2016.
 */

class ActivitySample01 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_01)
        paintStatusBar(R.color.sample01colorPrimaryDark)

        notifySubscribers(ChipEvents.ADD_STYLE, "template_default_android_chip.xml")



        chipEditText.addTextChangedListener(object : EnhancedTextWatcher(chipEditText) {
            override fun onTextChanged(cursor: Int, isBackspace: Boolean, deletedChar: Char) {
                val parts = chipEditText.text.toString().split(" ");
                if (parts.last().length > 3 && !isBackspace) {
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



