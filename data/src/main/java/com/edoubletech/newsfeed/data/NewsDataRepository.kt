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

package com.edoubletech.newsfeed.data

import com.edoubletech.newsfeed.data.data.NewsDataStoreFactory
import com.edoubletech.newsfeed.data.model.News
import com.edoubletech.newsfeed.data.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsDataRepository(private val factory: NewsDataStoreFactory) : NewsRepository {

    // TODO: Handle getting news from either cache or remote
    override suspend fun getNews(): List<News> {
        return withContext(Dispatchers.IO) {
            val isCached = factory.getCacheDataStore().isCached()
            val news = factory.getDataStore(isCached).getObservableNews()
            saveListOfNews(news)
            news
        }
    }

    override fun saveListOfNews(news: List<News>) {
        factory.getCacheDataStore().saveNews(news)
    }
}