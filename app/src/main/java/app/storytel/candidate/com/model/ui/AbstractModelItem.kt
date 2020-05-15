package app.storytel.candidate.com.model.ui

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.viewholders.FlexibleViewHolder
import java.io.Serializable


abstract class AbstractModelItem<VH : FlexibleViewHolder?>(id: Long) : AbstractFlexibleItem<VH?>(),
    Serializable {
    private var id: Long

    override fun equals(other: Any?): Boolean {
        return false
    }

    fun getId(): Long {
        return id
    }

    fun setId(id: Long) {
        this.id = id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    companion object {
        private const val serialVersionUID = -6882745111884490060L
    }

    init {
        this.id = id
    }
}