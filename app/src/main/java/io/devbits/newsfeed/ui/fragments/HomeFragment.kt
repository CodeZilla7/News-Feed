/*
 *  Copyright (C) 2019 Eton Otieno
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.devbits.newsfeed.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import io.devbits.newsfeed.R
import io.devbits.newsfeed.data.News
import io.devbits.newsfeed.ui.MainViewModel
import io.devbits.newsfeed.ui.adapters.NewsAdapter
import io.devbits.newsfeed.ui.state.Result
import kotlinx.android.synthetic.main.fragment_home.homeEmptyView
import kotlinx.android.synthetic.main.fragment_home.homeLoadingIndicator
import kotlinx.android.synthetic.main.fragment_home.homeNewsRV
import kotlinx.android.synthetic.main.fragment_home.view.homeNewsRV
import org.koin.androidx.viewmodel.ext.android.getViewModel

class HomeFragment : Fragment() {

    private val newsAdapter = NewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.homeNewsRV.adapter = newsAdapter
        val viewModel = getViewModel<MainViewModel>()
//        viewModel.newsLiveData.observe(this, Observer {
//            handleState(it)
//        })
        viewModel.triggerDataFetch()
        viewModel.data.observe(this, Observer {
            setUpScreenForSuccess(it)
        })
    }

    private fun handleState(result: Result<List<News>>) {
        when (result) {
            is Result.Loading -> setUpScreenForLoadingState()
            is Result.Success -> setUpScreenForSuccess(result.data)
            is Result.Error -> setUpScreenForError(result.exception)
        }
    }

    private fun setUpScreenForLoadingState() {
        homeLoadingIndicator.visibility = View.VISIBLE
        homeNewsRV.visibility = View.GONE
        homeEmptyView.visibility = View.GONE
    }

    private fun setUpScreenForSuccess(data: List<News>) {
        homeEmptyView.visibility = View.GONE
        homeLoadingIndicator.visibility = View.GONE

        if (data.isNullOrEmpty()) {
            homeEmptyView.visibility = View.VISIBLE
            homeEmptyView.text = "No Data was found 😑😑"
        } else {
            homeNewsRV.visibility = View.VISIBLE
            newsAdapter.submitList(data)
        }
    }

    private fun setUpScreenForError(exception: Exception) {
        homeLoadingIndicator.visibility = View.GONE
        homeNewsRV.visibility = View.GONE
        homeEmptyView.visibility = View.VISIBLE
        homeEmptyView.text = exception.localizedMessage
    }
}