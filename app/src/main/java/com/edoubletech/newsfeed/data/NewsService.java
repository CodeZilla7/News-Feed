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

package com.edoubletech.newsfeed.data;

import com.edoubletech.newsfeed.data.api.GuardianMain;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by EtonOtieno on 3/2/2018
 */

public interface NewsService {
    
    @GET("search")
    Call<GuardianMain> getNews(
            @Query("page-size") String pageSize,
            @Query("api-key") String apiKey,
            @Query("section") String section,
            @Query("show-fields") String fields,
            @Query("format") String format
    );
}