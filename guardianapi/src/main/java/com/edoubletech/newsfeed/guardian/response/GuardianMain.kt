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

package com.edoubletech.newsfeed.guardian.response

import com.edoubletech.newsfeed.data.model.News
import com.google.gson.annotations.SerializedName

class GuardianMain(@field:SerializedName("response") val response: GuardianResponse)

fun GuardianMain.mapToNews(): List<News> {
    val results = this.response.results
    val articles = mutableListOf<News>()
    results.forEach {
        articles.add(
            News(
                id = it.id,
                imageUrl = it.fields.thumbnail,
                webUrl = it.webUrl,
                sectionName = it.sectionName,
                title = it.webTitle,
                bodyText = it.fields.bodyText,
                publicationDate = it.webPublicationDate
            )
        )
    }
    return articles
}