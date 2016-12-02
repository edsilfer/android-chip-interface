package br.com.edsilfer.android.chipinterface.model

/**
 * Created by User on 24/11/2016.
 */

abstract class Chip() {

    var range = Pair(0, 0)

    abstract fun getHeader(): String

    abstract fun getSubheader(): String

    abstract fun getThumbnail(): String

    override fun hashCode(): Int {
        return getHeader().hashCode() + getSubheader().hashCode() + getThumbnail().hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return !(other == null || other !is Chip || other.hashCode() != hashCode())
    }
}
