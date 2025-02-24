package com.example.booktracker.repository

import com.example.booktracker.model.Book
import com.google.firebase.database.FirebaseDatabase

class BookRepository {

    private val database = FirebaseDatabase.getInstance()
    private val booksRef = database.getReference("books")

    // Function to add a book to Firebase Realtime Database
    fun addBook(book: Book) {
        val bookId = booksRef.push().key // Automatically generate a unique ID
        if (bookId != null) {
            booksRef.child(bookId).setValue(book)
        }
    }

    // Function to fetch all books from Firebase Realtime Database
    fun getAllBooks(callback: (List<Book>) -> Unit) {
        booksRef.get().addOnSuccessListener { snapshot ->
            val booksList = mutableListOf<Book>()
            for (bookSnapshot in snapshot.children) {
                val book = bookSnapshot.getValue(Book::class.java)
                if (book != null) {
                    booksList.add(book)
                }
            }
            callback(booksList)
        }.addOnFailureListener {
            callback(emptyList())
        }
    }
}
