package app.storytel.candidate.com.model.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.storytel.candidate.com.R
import app.storytel.candidate.com.data.model.MyPost
import com.squareup.picasso.Picasso
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFilterable
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.flexibleadapter.items.IHeader
import eu.davidea.flexibleadapter.items.ISectionable
import eu.davidea.viewholders.ExpandableViewHolder
import java.io.Serializable
import java.util.*


class PostItem : Serializable, AbstractModelItem<PostItem.ServiceViewHolder>,
    ISectionable<PostItem.ServiceViewHolder, IHeader<*>>, IFilterable<String> {

    private var thumbnail: String? = ""
    var avatar: String? = ""
    var title: String? = ""
    var body: String? = ""
    private var header: IHeader<*>? = null

    constructor(id: Long) : super(id)

    constructor(post: MyPost) : super(id = post.id.toLong()) {
        title = post.title
        body = post.body
        thumbnail = post.photo?.thumbnailUrl
        avatar = post.photo?.url
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: ServiceViewHolder?,
        position: Int,
        payload: MutableList<Any>?
    ) {
        holder?.let {
            Picasso.get()
                .load(thumbnail)
                .resize(150, 150)
                .centerCrop()
                .into(it.postImage)

            it.postTitle.text = title
            it.postTitle.isSelected = true

            it.postBody.text = body
        }
    }

    override fun createViewHolder(
        view: View?,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?
    ): ServiceViewHolder? {
        return ServiceViewHolder(view!!, adapter)
    }

    override fun getItemViewType(): Int {
        return R.layout.item_post
    }

    override fun getLayoutRes() = R.layout.item_post

    override fun getHeader(): IHeader<*> {
        return this.header!!
    }

    override fun setHeader(header: IHeader<*>?) {
        this.header = header
    }

    override fun filter(constraint: String?): Boolean {
        return this.title!!.toLowerCase(Locale.getDefault()).contains(
            constraint!!.toLowerCase(
                Locale.getDefault()
            )
        )
    }

    class ServiceViewHolder internal constructor(view: View, adapter: FlexibleAdapter<*>?) :
        ExpandableViewHolder(view, adapter) {

        var postImage: ImageView = itemView.findViewById(R.id.postImage)
        var postTitle: TextView = itemView.findViewById(R.id.postTitle)
        var postBody: TextView = itemView.findViewById(R.id.postBody)

        override fun isViewExpandableOnClick(): Boolean {
            return true //true by default
        }
    }
}