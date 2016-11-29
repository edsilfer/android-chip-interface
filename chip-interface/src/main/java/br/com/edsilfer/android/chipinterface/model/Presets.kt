package br.com.edsilfer.android.chipinterface.model

import br.com.edsilfer.android.chipinterface.R
import br.com.edsilfer.android.chipinterface.model.enum.ChipStyle

/**
 * Created by User on 28/11/2016.
 */

object Presets {

    fun preset01(): ChipPalette {
        return ChipPalette(
                R.style.default_collapsed_header,
                R.color.clr_default_collapsed_background,
                R.style.default_expanded_header,
                R.style.default_expanded_subheader,
                R.color.clr_default_expanded_upper_background,
                R.color.clr_default_expanded_bottom_background,
                ChipStyle.CIRCULAR
        )
    }
}
