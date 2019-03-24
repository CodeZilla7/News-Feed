/*
 *   Copyright (C) 2018 Eton Otieno Oboch
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.edoubletech.newsfeed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edoubletech.newsfeed.data.usecase.GetNews
import com.edoubletech.newsfeed.ui.state.NewsState
import kotlinx.coroutines.launch

/**
 * This is the MainViewModel that contains the data needed in the app.
 */
class MainViewModel(private val getNews: GetNews) : ViewModel() {

    private val newsLiveData = MutableLiveData<NewsState>()

    fun setCategory(category: String) = getNews.setCategory(category)

    fun fetchNews() {
        viewModelScope.launch {
            newsLiveData.postValue(NewsState.Success(getNews.execute()))
        }
    }

    fun getNews(): LiveData<NewsState> {
        return newsLiveData
    }
}