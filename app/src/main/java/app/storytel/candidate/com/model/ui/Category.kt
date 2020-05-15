package app.storytel.candidate.com.model.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.storytel.candidate.com.R
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IExpandable
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.flexibleadapter.items.IHeader
import eu.davidea.viewholders.ExpandableViewHolder
import java.io.Serializable


class Category : Serializable, AbstractModelItem<Category.HeaderViewHolder>,
    IExpandable<Category.HeaderViewHolder, PostItem>, IHeader<Category.HeaderViewHolder> {

    private var mExpanded = false
    var items: MutableList<PostItem> = mutableListOf()
    var categoryName: String? = null

    constructor(id: Long) : super(id) {
        isDraggable = false
        isHidden = false
        isExpanded = true
        isSelectable = false
        isEnabled = false
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: HeaderViewHolder?,
        position: Int,
        payloads: MutableList<Any>?
    ) {
        holder?.let {
            it.heading.text = categoryName
        }
    }

    override fun getItemViewType(): Int {
        return R.layout.item_header
    }

    override fun createViewHolder(
        view: View?,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?
    ): HeaderViewHolder? {
        return HeaderViewHolder(view!!, adapter!!)
    }

    override fun getLayoutRes() = R.layout.item_header

    override fun getExpansionLevel() = 0

    override fun isExpanded(): Boolean {
        return mExpanded
    }

    override fun setExpanded(expanded: Boolean) {
        mExpanded = expanded
    }

    override fun getSubItems(): MutableList<PostItem> {
        return items
    }

    override fun toString(): String {
        return "Header [" + categoryName + "//SubItems" + items.size + "]"
    }

    class HeaderViewHolder(view: View, adapter: FlexibleAdapter<*>) :
        ExpandableViewHolder(view, adapter) {
        val heading: TextView = view.findViewById(R.id.headerTitle)

        override fun isViewExpandableOnClick(): Boolean {
            return true //true by default
        }
    }
}