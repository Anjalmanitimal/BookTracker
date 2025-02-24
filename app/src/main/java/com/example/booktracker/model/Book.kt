package com.example.booktracker.model

data class Book(
    var title: String = "",
    var author: String = "",
    var pageRange: String = "",
    var id: String = ""  // Keep 'id' here for Firebase
) {
    constructor() : this("", "", "", "")  // No-argument constructor for Firebase
}
