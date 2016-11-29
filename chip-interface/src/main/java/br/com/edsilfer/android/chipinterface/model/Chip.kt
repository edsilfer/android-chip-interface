package br.com.edsilfer.android.chipinterface.model

/**
 * Created by User on 24/11/2016.
 */

abstract class Chip() {

    companion object {
        private var objCount = -1.toDouble()
    }

    var chipId = 0.toDouble()
        private set

    init {
        chipId = objCount++
    }

    abstract fun getHeader(): String

    abstract fun getSubheader(): String

    abstract fun getThumbnail(): String
}
