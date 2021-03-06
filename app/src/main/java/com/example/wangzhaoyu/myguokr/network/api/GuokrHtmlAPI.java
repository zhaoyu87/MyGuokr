package com.example.wangzhaoyu.myguokr.network.api;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * @author wangzhaoyu
 */
public interface GuokrHtmlAPI {

    //article content
    @GET(ApiConfig.HtmlAPI.ARTICLE_CONTENT)
    public void getArticleContent(
            @Path(ApiConfig.Path.ID) int articleId,
            Callback<Response> callback);


    @GET(ApiConfig.HtmlAPI.ARTICLE_CONTENT)
    public Observable<Response> getArticleContent(
            @Path(ApiConfig.Path.ID) int articleId);
}
