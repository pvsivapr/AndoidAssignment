package com.nytimes.networking
import com.google.gson.annotations.SerializedName

data class NYMultimediaModel(
    @SerializedName("url")
    val url: String,
    @SerializedName("format")
    val format: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("subtype")
    val subtype: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("width")
    val width: String,
    @SerializedName("caption")
    val caption: String,
    @SerializedName("copyright")
    val copyRight: String
)

data class NYItemsModel(
    @SerializedName("section")
    val section: String,
    @SerializedName("subsection")
    val subSection: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("abstract")
    val abstract: String,
    @SerializedName("byline")
    val byline: String,
    @SerializedName("item_type")
    val itemType: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("uri")//world
    val uri: String,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("published_date")
    val publishedDate: String,
    @SerializedName("updated_date")
    val updatedDate: String,
    @SerializedName("multimedia")
    val multimedia: List<NYMultimediaModel>
)

data class NYItemsListModel(
    @SerializedName("status")
    val status: String,
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("section")
    val section: String,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("num_results")
    val numResults: Int,
    @SerializedName("results")
    val results: List<NYItemsModel>
)