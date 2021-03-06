package br.com.edsilfer.android.chipinterface.demo.model

import br.com.edsilfer.android.chipinterface.demo.model.enum.ChatType
import br.com.edsilfer.android.chipinterface.model.Chip
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by efernandes on 04/11/16.
 */

class Chat(
        var id: Double = -1.toDouble(),
        var mHeader: String = "empty mHeader",
        val currentUser: User,
        var participants: MutableList<User> = mutableListOf<User>(),
        var mThumbnail: String = "",
        var lastMessage: Message
) : Serializable, Chip() {

    private val chatType: ChatType

    init {
        chatType = if (participants.size > 2) ChatType.GROUP else ChatType.INDIVIDUAL
        if (chatType == ChatType.INDIVIDUAL) {
            removeCurrentUser()
            mHeader = participants[0].name
            mThumbnail = participants[0].thumbnail
        }

    }

    override fun getHeader(): String {
        return mHeader
    }

    override fun getSubheader(): String {
        return lastMessage.content
    }

    override fun getThumbnail(): String {
        return mThumbnail
    }

    private fun removeCurrentUser() {
        val iterator = participants.iterator()
        while (iterator.hasNext()) {
            val user = iterator.next()
            if (user.id == currentUser.id) {
                iterator.remove()
            }
        }
    }

    override fun toString(): String {
        return "name: ${getHeader()}, hash: ${hashCode()} | "
    }
}
