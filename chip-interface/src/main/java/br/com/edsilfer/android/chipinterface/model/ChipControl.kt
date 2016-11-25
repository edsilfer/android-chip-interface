package br.com.edsilfer.android.chipinterface.model

import android.content.Context

/**
 * Created by User on 24/11/2016.
 */

interface ChipControl {

    fun addChip(context : Context, chip: Chip, replaceable : String)

    fun removeChip(chip: Chip)

}



