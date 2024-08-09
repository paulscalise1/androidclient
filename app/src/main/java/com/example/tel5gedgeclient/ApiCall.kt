package com.example.tel5gedgeclient

import android.content.Context
import android.widget.Toast
import retrofit.*

class ApiCall {
    // This function takes a Context and callback function
    // as a parameter, which will be called
    // when the API response is received.
    fun getjokes(context: Context, callback: (List<DataModel>) -> Unit) {

        // Create a Retrofit instance with the base URL and
        // a GsonConverterFactory for parsing the response.
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(currentServerIP)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create an ApiService instance from the Retrofit instance.
        val service: ApiService = retrofit.create(ApiService::class.java)

        // Call the getjokes() method of the ApiService
        // to make an API request.
        val call: Call<List<DataModel>> = service.getjokes()

        // Use the enqueue() method of the Call object to
        // make an asynchronous API request.
        call.enqueue(object : Callback<List<DataModel>> {
            // This is an anonymous inner class that implements the Callback interface.
            override fun onResponse(response: Response<List<DataModel>>, retrofit: Retrofit) {
                // This method is called when the API response is received successfully.
                if (response.isSuccess) {
                    // If the response is successful, parse the
                    // response body to a List<DataModel> object.
                    val jokes: List<DataModel> = response.body() ?: emptyList()

                    // Call the callback function with the List<DataModel>
                    // object as a parameter.
                    callback(jokes)
                }
            }

            override fun onFailure(t: Throwable) {
                // This method is called when the API request fails.
                Toast.makeText(context, "Request Fail", Toast.LENGTH_SHORT).show()
            }
        })
    }
}



/*
package com.example.tel5gedgeclient

import android.content.Context
import android.provider.ContactsContract.Data
import android.widget.Toast
import retrofit.*

class ApiCall {
    // This function takes a Context and callback function
    // as a parameter, which will be called
    // when the API response is received.
    fun getjokes(context: Context, callback: (DataModel) -> Unit) {

        // Create a Retrofit instance with the base URL and
        // a GsonConverterFactory for parsing the response.
        val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://192.168.1.141:3000/").addConverterFactory(
            GsonConverterFactory.create()).build()

        // Create an ApiService instance from the Retrofit instance.
        val service: ApiService = retrofit.create<ApiService>(ApiService::class.java)

        // Call the getjokes() method of the ApiService
        // to make an API request.
        val call: Call<DataModel> = service.getjokes()

        // Use the enqueue() method of the Call object to
        // make an asynchronous API request.
        call.enqueue(object : Callback<DataModel> {
            // This is an anonymous inner class that implements the Callback interface.
            override fun onResponse(response: Response<DataModel>?, retrofit: Retrofit?) {
                // This method is called when the API response is received successfully.

                if(response!!.isSuccess){
                    // If the response is successful, parse the
                    // response body to a DataModel object.
                    val jokes: DataModel = response.body() as DataModel

                    // Call the callback function with the DataModel
                    // object as a parameter.
                    callback(jokes)
                }
            }

            override fun onFailure(t: Throwable?) {
                // This method is called when the API request fails.
                Toast.makeText(context, "Request Fail", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

 */