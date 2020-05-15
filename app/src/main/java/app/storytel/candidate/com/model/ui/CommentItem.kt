package app.storytel.candidate.com.model.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.storytel.candidate.com.R
import app.storytel.candidate.com.data.model.Comment
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.ExpandableViewHolder
import java.io.Serializable


class CommentItem : Serializable, AbstractModelItem<CommentItem.CommentViewHolder> {

    private var name: String? = ""
    private var body: String? = ""
    private var email: String? = ""

    constructor(id: Long) : super(id)

    constructor(comment: Comment) : super(id = comment.id.toLong()) {
        name = comment.name
        body = comment.body
        email = comment.email
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: CommentViewHolder?,
        position: Int,
        payload: MutableList<Any>?
    ) {
        holder?.let {
            it.commentName.text = name
            it.commentEmail.text = email
            it.commentBody.text = body
        }
    }

    override fun createViewHolder(
        view: View?,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?
    ): CommentViewHolder? {
        return CommentViewHolder(view!!, adapter)
    }

    override fun getItemViewType(): Int {
        return R.layout.item_post
    }

    override fun getLayoutRes() = R.layout.item_comment

    class CommentViewHolder internal constructor(view: View, adapter: FlexibleAdapter<*>?) :
        ExpandableViewHolder(view, adapter) {

        var commentName: TextView = itemView.findViewById(R.id.commentName)
        var commentEmail: TextView = itemView.findViewById(R.id.commentEmail)
        var commentBody: TextView = itemView.findViewById(R.id.commentBody)
    }
}