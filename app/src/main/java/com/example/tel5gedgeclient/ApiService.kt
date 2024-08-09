package com.example.tel5gedgeclient

import retrofit.Call
import retrofit.http.GET

// This interface defines an API
// service for getting random jokes.
interface ApiService {
    // This annotation specifies that the HTTP method
    // is GET and the endpoint URL is "jokes/random".
    @GET("syncs")
    // This method returns a Call object with a generic
    // type of DataModel, which represents
    // the data model for the response.
    //fun getjokes(): Call<DataModel>
    fun getjokes(): Call<List<DataModel>>

}