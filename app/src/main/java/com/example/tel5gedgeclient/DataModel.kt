package com.example.tel5gedgeclient

data class DataModel(
    var id: Int,
    var name: String,
    var isExternalSyncpoint: Boolean,
    var observing_units: List<String>,
    var observing_units_holding: ArrayList<Boolean>,
    var all_present: Boolean,
    var is_triggered: Boolean
):java.io.Serializable

    /*
    // Model class for our Jokes
    data class DataModel(
        var categoories:ArrayList<String>,
        var created_at: String,
        var icon_url:String,
        var id:String,
        var updated_at:String,
        var url:String,
        var value:String
    ):java.io.Serializable
     */