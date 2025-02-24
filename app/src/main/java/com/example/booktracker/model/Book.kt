package com.example.booktracker.model

data class Book(
    val title: String = "",
    val author: String = "",
    val pageRange: String = "" // New field for page range
) {
    // Default constructor for Firebase deserialization
    constructor() : this("", "", "")
}
