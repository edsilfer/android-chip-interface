package br.com.edsilfer.android.chipinterface.demo.model.enum

/**
 * Created by efernandes on 10/24/16.
 */

enum class UserStatus(value: String) {
    ONLINE("online"), OFFLINE("offline"), UNKNOWN("unknown");

    private var mValue: String = value

    override fun toString(): String {
        return mValue
    }

    fun fromString(value: String): UserStatus {
        return values().firstOrNull { it.equals(value) } ?: UNKNOWN
    }
}
