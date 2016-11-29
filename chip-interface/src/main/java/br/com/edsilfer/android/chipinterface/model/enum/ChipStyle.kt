package br.com.edsilfer.android.chipinterface.model.enum

import br.com.edsilfer.android.chipinterface.R

/**
 * Created by User on 28/11/2016.
 */
enum class ChipStyle(private val mValue: Int) {
    SQUARE(R.layout.rsc_chip_details_squared_thumbnail), CIRCULAR(R.layout.rsc_chip_details_circular_thumbnail);

    fun getLayout(): Int {
        return mValue
    }
}