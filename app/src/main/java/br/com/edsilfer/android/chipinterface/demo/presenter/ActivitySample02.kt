package br.com.edsilfer.android.chipinterface.demo.presenter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import br.com.edsilfer.android.chipinterface.demo.FakeDataProvider
import br.com.edsilfer.android.chipinterface.demo.R
import br.com.edsilfer.android.chipinterface.model.Presets
import br.com.edsilfer.android.chipinterface.model.intf.ChipControl
import kotlinx.android.synthetic.main.activity_sample_02.*


/**
 * Created by User on 24/11/2016.
 */

class ActivitySample02 : AppCompatActivity() {

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sample_02, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_02)

        setSupportActionBar(toolbar)
        toolbar.title = "Compose"
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        supportActionBar!!.setHomeButtonEnabled(true)

        from.setChipStyle(Presets.preset02())
        from.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val parts = from.text.toString().split(" ");
                if (parts.last().length > 3 && p3 != 0) {
                    FakeDataProvider.provideChats()
                            .filter { it.getHeader().toLowerCase().contains(parts.last()) }
                            .forEach {
                                (from as ChipControl).addChip(it, parts.last())
                            }
                }
            }
        })
    }
}



