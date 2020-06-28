package com.nytimes.localdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nylocaldbmodel")
data class NYLocalDBModel(
    @PrimaryKey
    val nid: Int,

    @ColumnInfo(name = "name")
    val section: String?,
    @ColumnInfo(name = "sub_section")
    val subSection: String?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "by_line")
    val byline: String?,
    @ColumnInfo(name = "item_type")
    val itemType: String?,
    @ColumnInfo(name = "url")
    val url: String?,

    @ColumnInfo(name = "created_date")
    val createdDate: String?,
    @ColumnInfo(name = "published_date")
    val publishedDate: String?,
    @ColumnInfo(name = "updated_date")
    val updatedDate: String?,//diplaying

    @ColumnInfo(name = "image_url")
    val imageUrl: String?,
    @ColumnInfo(name = "image_format")
    val imageFormat: String?,
    @ColumnInfo(name = "image_type")
    val imageType: String?
)