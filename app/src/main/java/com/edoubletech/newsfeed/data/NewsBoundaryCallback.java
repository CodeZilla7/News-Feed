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

package com.edoubletech.newsfeed.data;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.edoubletech.newsfeed.BuildConfig;
import com.edoubletech.newsfeed.NewsFeed;
import com.edoubletech.newsfeed.data.api.GuardianMain;
import com.edoubletech.newsfeed.data.api.GuardianResponse;
import com.edoubletech.newsfeed.data.api.GuardianResult;
import com.edoubletech.newsfeed.data.dao.NewsDao;
import com.edoubletech.newsfeed.data.model.News;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

public class NewsBoundaryCallback extends PagedList.BoundaryCallback<News> {

    private String mCategory;
    private NewsDao mDao = NewsFeed.getNewsComponent().exposeDao();

    NewsBoundaryCallback(String category){
        mCategory = category;
    }

    @Override
    public void onZeroItemsLoaded() {
        requestAndSaveNews(mCategory);
    }

    @Override
    public void onItemAtEndLoaded(@NonNull News itemAtEnd) {
        requestAndSaveNews(mCategory);
    }

    private void requestAndSaveNews(String category) {
        List<News> mNewsList = new ArrayList<>();
        Retrofit retrofit = NewsFeed.getNewsComponent().exposeRetrofit();
        NewsService newsService = retrofit.create(NewsService.class);
        Call<GuardianMain> call = newsService.getNews("50", BuildConfig.GUARDIAN_API_KEY,
                category, "all", "json");

        // Make the actual call. This is an asynchronous call.
        call.enqueue(new Callback<GuardianMain>() {
            @Override
            public void onResponse(Call<GuardianMain> call, Response<GuardianMain> response) {
                if (response.isSuccessful()) {
                    Timber.d(" NewsResponse is successful");
                    GuardianResponse res = response.body().getResponse();
                    List<GuardianResult> apiResults = res.getResults();
                    for (GuardianResult apiResult : apiResults) {
                        mNewsList.add(new News(
                                apiResult.getFields().getThumbnail(), /* Thumbnail for the news */
                                apiResult.getWebUrl(), /* Website url*/
                                apiResult.getSectionName(), /* Section name*/
                                apiResult.getWebTitle(), /* Web Title of Article*/
                                apiResult.getFields().getTrailText(), /* Trail Text*/
                                apiResult.getFields().getBodyText(), /* Description */
                                apiResult.getWebPublicationDate())); /* Publication Date*/
                    }
                } else {
                    int statusCode = response.code();
                    ResponseBody errorBody = response.errorBody();
                    Timber.i("Network Error: " + errorBody.toString()
                            + "\nStatus Code: " + statusCode);

                }
            }

            @Override
            public void onFailure(Call<GuardianMain> call1, Throwable throwable) {
                Timber.e(throwable);
            }
        });
        mDao.addNews(mNewsList);
    }
}
