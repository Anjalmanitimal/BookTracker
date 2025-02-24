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

    // Add a book to the database
    fun addBook(book: Book) {
        viewModelScope.launch {
            bookRepository.addBook(book)
            fetchBooks() // Refresh the book list after adding a new one
        }
    }

    // Fetch all books from the database
    fun fetchBooks() {
        viewModelScope.launch {
            bookRepository.getAllBooks { books ->
                _books.value = books
            }
        }
    }
}
