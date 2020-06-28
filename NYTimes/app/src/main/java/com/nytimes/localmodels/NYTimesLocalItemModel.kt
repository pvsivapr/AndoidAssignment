package com.nytimes.localmodels

data class NYTimesLocalItemModel(
    val section: String,
    val subSection: String,
    val title: String,
    val byline: String,
    val itemType: String,
    val url: String,

    val createdDate: String,
    val publishedDate: String,
    val updatedDate: String,//diplaying

    val imageUrl: String,
    val imageFormat: String,
    val imageType: String
)