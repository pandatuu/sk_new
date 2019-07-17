package com.example.sk_android.mvp.api.collection

import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CollectionApi {

    @GET("/api/v1/favorites/")
    fun getFavoritesCompany(
        @Query("type") type: String
    ): Observable<Response<JsonObject>>

    @DELETE("/api/v1/favorites/{id}")
    fun deleteFavoritesCompany(
        @Path("id") id: String
    ): Observable<Response<String>>
}