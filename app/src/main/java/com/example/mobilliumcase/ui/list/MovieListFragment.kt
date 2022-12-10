package com.example.mobilliumcase.ui.list

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilliumcase.R
import com.example.mobilliumcase.base.BaseFragment
import com.example.mobilliumcase.base.BaseLoadStateAdapter
import com.example.mobilliumcase.base.SchedulerProvider
import com.example.mobilliumcase.model.MoviesDataModel
import com.example.mobilliumcase.ui.list.paging.MovieListPagerAdapter
import com.example.mobilliumcase.ui.list.paging.slider.MovieSliderListAdapter
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class MovieListFragment : BaseFragment<MovieViewModel>(){
    override fun getLayoutRes(): Int = R.layout.fragment_movie_list
    override fun setViewModel(): MovieViewModel = MovieViewModel()
    @Inject
    lateinit var schedulerProvider: SchedulerProvider
    /** init view */
    private val verticalRecyclerView by lazy { vertical_recycler }
   // private val horizontalRecyclerView by lazy { view?.findViewById<RecyclerView>(R.id.horizontal_recycler) }
    private val moviesSliderView by lazy { imageSlider }
    private val swipeRefreshLayout by lazy { swipe_layout }
    private val movieListProgress by lazy { movie_progress }

    /** init pager adapter */
    private lateinit var verticalPagerAdapter: MovieListPagerAdapter

    /** init movies slider adapter */
    private lateinit var sliderAdapter: MovieSliderListAdapter

    override fun init(context: Context) {
        initPagingAdapter()
        initRefreshEvents()
        //initSlider()
        observeLiveDataObjects()
    }

    private fun initSlider(list: ArrayList<MoviesDataModel>) {
        sliderAdapter = MovieSliderListAdapter(
            list,
            onMovieClicked = { selectedItem ->
                lifecycleScope.launch {
                    val action =
                        MovieListFragmentDirections.actionMainToDetail(
                            selectedItem.toInt()
                        )
                    findNavController().navigate(action)
                }
            }
        )
        // on below line we are setting auto cycle direction
        // for our slider view from left to right.
        moviesSliderView?.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR

        // on below line we are setting adapter for our slider.
        moviesSliderView?.setSliderAdapter(sliderAdapter)

        // on below line we are setting scroll time
        // in seconds for our slider view.
        moviesSliderView?.scrollTimeInSec = 3

        // on below line we are setting auto cycle
        // to true to auto slide our items.
        moviesSliderView?.isAutoCycle = true

        // on below line we are calling start
        // auto cycle to start our cycle.
        moviesSliderView?.startAutoCycle()
    }

    private fun initRefreshEvents() {
        swipeRefreshLayout?.setOnRefreshListener {
            viewModel.apply {
                lifecycleScope.launch {
                    moviesPagingFlowData.collectLatest {
                        verticalPagerAdapter.submitData(it)
                    }
                    initNowPlayingMovies()
                }
            }
            swipeRefreshLayout!!.isRefreshing = false
        }
    }

    private fun observeLiveDataObjects() {
        viewModel.apply {
            // Observes progress changes
            viewModel.progress.observe(viewLifecycleOwner) { inProgress ->
                movieListProgress?.visibility = if(inProgress) View.VISIBLE else View.GONE
            }
            // Observes paging data list
            lifecycleScope.launch {
                moviesPagingFlowData.collectLatest {
                    verticalPagerAdapter.submitData(it)
                }
                verticalRecyclerView?.visibility = View.VISIBLE
                //refreshLayout.isRefreshing = false
            }
            // Observes alert messages
            alertMessage.observe(viewLifecycleOwner) {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
            nowPlayingMovies.observe(viewLifecycleOwner) {
                moviesSliderView.visibility = View.VISIBLE
                //sliderAdapter.setNewItems(it)
                initSlider(it as ArrayList<MoviesDataModel>)
            }
        }

    }

    private fun initPagingAdapter() {
        verticalPagerAdapter = MovieListPagerAdapter(
            onMovieClicked = { selectedItem ->
                lifecycleScope.launch {
                    val action =
                        MovieListFragmentDirections.actionMainToDetail(
                            selectedItem.id.toInt()
                        )
                    findNavController().navigate(action)
                }
            }
        )
        verticalRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            //addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = verticalPagerAdapter.withLoadStateHeaderAndFooter(
                header = BaseLoadStateAdapter { verticalPagerAdapter.retry() },
                footer = BaseLoadStateAdapter { verticalPagerAdapter.retry() },
            )
        }
        verticalPagerAdapter.addLoadStateListener { loadState ->
            val errorState = when {
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            errorState?.let {
                Toast.makeText(context, it.error.toString(), Toast.LENGTH_LONG).show()
            }
        }

    }

}