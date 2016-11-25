package br.com.edsilfer.android.chipinterface.demo.model.enum

/**
 * Created by efernandes on 10/24/16.
 */

enum class MessageDirection(value: String) {
    INCOME("income"), OUTGOING("outgoing");

    private var mValue: String = value

    override fun toString(): String {
        return mValue
    }

    fun fromString(value: String): MessageDirection {
        return values().firstOrNull { it.equals(value) } ?: INCOME
    }
}

