package com.example.booktracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.model.Book
import com.example.booktracker.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {

    private val bookRepository = BookRepository()

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> get() = _books

    init {
        observeBooks() // Automatically starts observing books on ViewModel creation
    }

    private fun observeBooks() {
        bookRepository.observeBooks { books ->
            _books.value = books
        }
    }

    // Add a book to the database
    fun addBook(book: Book) {
        viewModelScope.launch {
            bookRepository.addBook(book)
            observeBooks() // Refresh the book list after adding a new one
        }
    }

    fun updateBook(book: Book) {
        viewModelScope.launch {
            bookRepository.updateBook(book)
            observeBooks() // Refresh list after update
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch {
            bookRepository.deleteBook(book)
            observeBooks() // Refresh list after deletion
        }
    }

    // Fetch all books from the database

}
