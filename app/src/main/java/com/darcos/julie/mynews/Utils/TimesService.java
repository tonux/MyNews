package com.darcos.julie.mynews.Utils;

import com.darcos.julie.mynews.Models.MostPopular.MostPopular;
import com.darcos.julie.mynews.Models.Search.Search;
import com.darcos.julie.mynews.Models.TopStories.TopStories;


import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TimesService {

    //api Topstories
    @GET("svc/topstories/v2/{section}.json?api-key=JyNdblyOMqqNNknG0iu5INNajfu1wAyj")
    Observable<TopStories> getTopStories(@Path("section") String section);

    //api MostPopular
    @GET("svc/mostpopular/v2/viewed/1.json?api-key=JyNdblyOMqqNNknG0iu5INNajfu1wAyj")
    Observable<MostPopular> getMostPopular();

    //api Search
    @GET("svc/search/v2/articlesearch.json?api-key=JyNdblyOMqqNNknG0iu5INNajfu1wAyj")
    Observable<Search> getSearch(@Query("q") String queryTerm, @Query("fq") String newsDesk, @Query("begin_date") String beginDate, @Query("end_date") String endDate);


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
