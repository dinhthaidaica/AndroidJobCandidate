package app.storytel.candidate.com.ui

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
import app.storytel.candidate.com.model.ui.PostItem
import app.storytel.candidate.com.ui.adapter.FlexibilityAdapter
import app.storytel.candidate.com.ui.comment.DetailActivity
import app.storytel.candidate.com.ui.widget.empty.EmptyListener
import app.storytel.candidate.com.ui.widget.error.ErrorListener
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity(), FlexibleAdapter.OnItemClickListener {

    private var mAdapter: FlexibilityAdapter<Category>? = null
    private val mainActivityViewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        toolbar.title = "Posts"

        setupAdapter()
        setupViewListeners()

        mainActivityViewModel.getPostsAndPhotos().observe(this, Observer {
            if (it != null) this.handleDataState(it)
        })
        mainActivityViewModel.fetchPostsAndPhotos()
    }

    private fun setupAdapter() {
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view.recycledViewPool.setMaxRecycledViews(R.layout.item_post, 30)
        recycler_view.setHasFixedSize(false)
        recycler_view.itemAnimator = DefaultItemAnimator()

        if (mAdapter == null) {
            mAdapter = FlexibilityAdapter(ArrayList<Category>(), this)
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

    private fun handleDataState(state: Resource<List<Category>?>) {
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

    private fun setupScreenForSuccess(categories: List<Category>?) {
        view_error.visibility = View.GONE
        progress.visibility = View.GONE
        if (categories != null && categories.isNotEmpty()) {
            mAdapter!!.updateDataSet(categories, true)
            recycler_view.visibility = View.VISIBLE
            toolbar_layout!!.title = "Posts of ${categories.size} users"
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
            mainActivityViewModel.fetchPostsAndPhotos()
        }
    }

    private val errorListener = object : ErrorListener {
        override fun onTryAgainClicked() {
            mainActivityViewModel.fetchPostsAndPhotos()
        }
    }

    override fun onItemClick(view: View?, position: Int): Boolean {
        val item: IFlexible<*>? = mAdapter!!.getItem(position)
        if (item is PostItem) {
            val intent = DetailActivity.callingIntent(this, item)
            startActivity(intent)
        }
        return true
    }

}