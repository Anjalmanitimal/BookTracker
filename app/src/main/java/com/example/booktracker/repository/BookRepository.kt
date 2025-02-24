package com.example.booktracker.repository

import com.example.booktracker.model.Book
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BookRepository {

    private val database = FirebaseDatabase.getInstance()
    private val booksRef = database.getReference("books")

    // Function to add a book to Firebase Realtime Database
    fun addBook(book: Book) {
        val bookId = booksRef.push().key
        if (bookId != null) {
            book.id = bookId
            println("Saving book to Firebase: $book")  // Log book object before saving
            booksRef.child(bookId).setValue(book)
        }
    }



    fun updateBook(book: Book) {
        if (book.id.isNotEmpty()) {
            booksRef.child(book.id).setValue(book)
        }
    }

    fun deleteBook(book: Book) {
        if (book.id.isNotEmpty()) {
            booksRef.child(book.id).removeValue()
        }
    }

    // Function to fetch all books from Firebase Realtime Database
    fun observeBooks(callback: (List<Book>) -> Unit) {
        booksRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val booksList = mutableListOf<Book>()
                for (bookSnapshot in snapshot.children) {
                    val book = bookSnapshot.getValue(Book::class.java)
                    if (book != null) {
                        book.id = bookSnapshot.key ?: ""  // Ensure ID is set
                        booksList.add(book)
                    }
                }
                callback(booksList) // Send updated list to ViewModel
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList()) // Handle failure
            }
        })
    }


}
