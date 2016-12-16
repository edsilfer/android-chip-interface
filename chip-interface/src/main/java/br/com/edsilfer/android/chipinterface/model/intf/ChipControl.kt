package br.com.edsilfer.android.chipinterface.model.intf

import br.com.edsilfer.android.chipinterface.model.Chip
import br.com.edsilfer.android.chipinterface.model.ChipPalette

/**
 * Created by User on 24/11/2016.
 */

interface ChipControl {

    fun addChip(chip: Chip, replaceable : String)

    fun removeChip(chip: Chip)

    fun getTextWithNoSpans () : String

}



