package com.example.booktracker.model

data class Book(
    val title: String = "",   // Default empty value
    val author: String = ""   // Default empty value
) {
    // Default constructor for Firebase deserialization
    constructor() : this("", "")
}
