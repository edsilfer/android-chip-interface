package br.com.edsilfer.android.chipinterface.model

import br.com.edsilfer.android.chipinterface.model.enum.ChipStyle

/**
 * Created by User on 28/11/2016.
 */

data class ChipPalette(
        val collapsedHeader: Int,
        val collapsedBackground :Int,
        val expandedHeader: Int,
        val expandedSubheader: Int,
        val upperBackground: Int,
        val bottomBackground: Int,
        val style: ChipStyle
)

