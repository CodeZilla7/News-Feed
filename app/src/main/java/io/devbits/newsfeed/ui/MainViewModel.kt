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

package io.devbits.newsfeed.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.devbits.newsfeed.data.usecase.GetNews
import io.devbits.newsfeed.ui.state.NewsState
import kotlinx.coroutines.launch

/**
 * This is the MainViewModel that contains the data needed in the app.
 */
class MainViewModel(private val getNews: GetNews) : ViewModel() {

    private val _newsLiveData = MutableLiveData<NewsState>()

    val newsLiveData: LiveData<NewsState>
        get() = _newsLiveData

    fun setCategory(category: String) = getNews.setCategory(category)

    fun fetchNews() {
        viewModelScope.launch {
            _newsLiveData.postValue(NewsState.Success(getNews.execute()))
        }
    }
}