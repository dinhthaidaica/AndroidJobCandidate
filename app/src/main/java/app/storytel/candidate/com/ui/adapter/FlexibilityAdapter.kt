@file:Suppress("unused", "RedundantOverride")

package app.storytel.candidate.com.ui.adapter

import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem

@Suppress("UNCHECKED_CAST")
class FlexibilityAdapter<T : AbstractFlexibleItem<*>?> : FlexibleAdapter<T> {

    constructor(items: List<*>?) : super(items as MutableList<T>?)

    constructor(items: List<*>?, listeners: Any?) : super(items as MutableList<T>?, listeners)

    constructor(
        items: List<*>?,
        listeners: Any?,
        stableIds: Boolean
    ) : super(items as MutableList<T>?, listeners, stableIds)

    override fun updateDataSet(items: List<T>?, animate: Boolean) {
        //NOTE: To have views/items not changed, set them into "items" before passing the final
        // list to the Adapter.
        //Overwrite the list and fully notify the change, pass false to not animate changes.
        //Watch out! The original list must a copy
        super.updateDataSet(items, animate)
        //Add example view
        //showLayoutInfo(true);
    }
}