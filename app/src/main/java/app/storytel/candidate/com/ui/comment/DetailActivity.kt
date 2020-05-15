package app.storytel.candidate.com.ui.comment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import app.storytel.candidate.com.R
import app.storytel.candidate.com.model.Resource
import app.storytel.candidate.com.model.ResourceState
import app.storytel.candidate.com.model.ui.Category
import app.storytel.candidate.com.model.ui.CommentItem
import app.storytel.candidate.com.model.ui.PostItem
import app.storytel.candidate.com.ui.adapter.FlexibilityAdapter
import app.storytel.candidate.com.ui.widget.empty.EmptyListener
import app.storytel.candidate.com.ui.widget.error.ErrorListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_scrolling.progress
import kotlinx.android.synthetic.main.activity_scrolling.toolbar
import kotlinx.android.synthetic.main.content_scrolling.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class DetailActivity : AppCompatActivity() {

    private var mAdapter: FlexibilityAdapter<CommentItem>? = null
    private val detailActivityViewModel: DetailActivityViewModel by viewModel()
    private lateinit var currentPost: PostItem

    companion object {
        const val POST = "post"

        fun callingIntent(context: Context, post: PostItem): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(POST, post)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)
        currentPost = intent.getSerializableExtra(POST) as PostItem

        supportActionBar!!.title = currentPost.title
        details.text = currentPost.body
        Picasso.get().load(currentPost.avatar).resize(600, 600).centerInside().into(backdrop)

        setupAdapter()
        setupViewListeners()

        detailActivityViewModel.getComments().observe(this, Observer {
            if (it != null) this.handleDataState(it)
        })
        detailActivityViewModel.fetchComments(currentPost.getId().toInt())
    }

    private fun setupAdapter() {
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view.recycledViewPool.setMaxRecycledViews(R.layout.item_comment, 150)
        recycler_view.setHasFixedSize(false)
        recycler_view.itemAnimator = DefaultItemAnimator()

        if (mAdapter == null) {
            mAdapter = FlexibilityAdapter(ArrayList<Category>())
            mAdapter!!.expandItemsAtStartUp()
                .setAutoCollapseOnExpand(false)
                .setAutoScrollOnExpand(true)
                .setAnimateToLimit(Int.MAX_VALUE)
                .setNotifyMoveOfFilteredItems(false)
                .setNotifyChangeOfUnfilteredItems(false)
                .setAnimationOnReverseScrolling(false)
        }
        recycler_view.adapter = mAdapter
    }

    private fun handleDataState(state: Resource<List<CommentItem>?>) {
        when (state.status) {
            ResourceState.LOADING -> setupScreenForLoadingState()
            ResourceState.SUCCESS -> setupScreenForSuccess(state.data)
            ResourceState.ERROR -> setupScreenForError(state.message)
        }
    }

    private fun setupScreenForLoadingState() {
        progress.visibility = View.VISIBLE
        recycler_view.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.GONE
    }

    private fun setupScreenForSuccess(comments: List<CommentItem>?) {
        view_error.visibility = View.GONE
        progress.visibility = View.GONE
        if (comments != null && comments.isNotEmpty()) {
            mAdapter!!.updateDataSet(comments, true)
            recycler_view.visibility = View.VISIBLE
        } else {
            view_empty.visibility = View.VISIBLE
        }
    }

    private fun setupScreenForError(message: String?) {
        Timber.e(message)
        progress.visibility = View.GONE
        recycler_view.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.VISIBLE
    }

    private fun setupViewListeners() {
        view_empty.emptyListener = emptyListener
        view_error.errorListener = errorListener
    }

    private val emptyListener = object : EmptyListener {
        override fun onCheckAgainClicked() {
            detailActivityViewModel.fetchComments(currentPost.getId().toInt())
        }
    }

    private val errorListener = object : ErrorListener {
        override fun onTryAgainClicked() {
            detailActivityViewModel.fetchComments(currentPost.getId().toInt())
        }
    }

}