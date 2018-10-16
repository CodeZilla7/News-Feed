/*
 *  Copyright (C) 2018 Eton Otieno Oboch
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
 *
 */

package com.edoubletech.newsfeed.data.guardian

import com.edoubletech.newsfeed.data.model.News
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GuardianMain(@SerializedName("response") @Expose val response: GuardianResponse)

fun GuardianMain.mapToNews(): List<News> {
    val results = this.response.results
    val articles = ArrayList<News>()
    results.forEach {
        articles.add(News(
                it.id,
                it.fields.thumbnail, /* Thumbnail for the news */
                it.webUrl, /* Website url*/
                it.sectionName, /* Section name*/
                it.webTitle, /* Web Title of Article*/
                it.fields.trailText, /* Trail Text*/
                it.fields.bodyText, /* Description */
                it.webPublicationDate)) /* Publication Date*/
    }
    return articles
}