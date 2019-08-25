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

package io.devbits.newsfeed.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.devbits.newsfeed.R
import io.devbits.newsfeed.data.News
import kotlinx.android.synthetic.main.news_item.view.dateTextView
import kotlinx.android.synthetic.main.news_item.view.newsSourceTextView
import kotlinx.android.synthetic.main.news_item.view.summaryTextView
import kotlinx.android.synthetic.main.news_item.view.thumbnailImageView
import kotlinx.android.synthetic.main.news_item.view.titleTextView
import org.joda.time.DateTimeZone
import org.joda.time.format.ISODateTimeFormat
import java.text.SimpleDateFormat
import java.util.*

class NewsViewHolder private constructor(
    newsItemView: View
) : RecyclerView.ViewHolder(newsItemView) {

    fun bind(news: News) {
        Glide.with(itemView)
            .load(news.imageUrl)
            .centerCrop()
            .into(itemView.thumbnailImageView)

        itemView.newsSourceTextView.text = news.source
        itemView.titleTextView.text = news.title

        val dateTime = ISODateTimeFormat.dateTimeParser().parseDateTime(news.publicationDate)
        dateTime.withZone(DateTimeZone.forTimeZone(TimeZone.getDefault()))
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val time = Calendar.getInstance().apply { timeInMillis = dateTime.millis }.time
        val date = simpleDateFormat.format(time)
        itemView.dateTextView.text = date

        itemView.summaryTextView.text = news.summary
        itemView.setOnClickListener {
            val navController = it.findNavController()
            val directions = HomeFragmentDirections.actionHomeToDetail()
            navController.navigate(directions)
        }
    }

    companion object {
        fun create(parent: ViewGroup): NewsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.news_item, parent, false)
            return NewsViewHolder(view)
        }
    }
}