package com.example.taipeizoo.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BaseModel<T> {
    @SerializedName("result")
    @Expose
    var result: Result<T>? = null

    class Result<T> {
        @SerializedName("limit")
        @Expose
        var limit: Int = 0

        @SerializedName("offset")
        @Expose
        var offset: Int = 0

        @SerializedName("count")
        @Expose
        var count: Int = 0

        @SerializedName("sort")
        @Expose
        var sort: String = ""

        @SerializedName("results")
        @Expose
        var results: T? = null
    }
}