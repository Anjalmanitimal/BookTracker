package com.example.booktracker.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booktracker.R
import com.example.booktracker.adapter.BookAdapter
import com.example.booktracker.databinding.ActivityHomeBinding
import com.example.booktracker.model.Book
import com.example.booktracker.viewmodel.BookViewModel
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val bookViewModel: BookViewModel by viewModels()
    private val bookAdapter = BookAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView
        binding.recyclerViewBooks.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewBooks.adapter = bookAdapter

        // Observe books from ViewModel
        lifecycleScope.launch {
            bookViewModel.books.collect { books ->
                bookAdapter.submitList(books)
            }
        }

        // Fetch books from Firebase when the activity starts


        // Add Book button click listener
        binding.btnAddBook.setOnClickListener {
            showAddBookDialog()
        }

        // Logout button click listener
        binding.btnLogout.setOnClickListener {
            finish() // Simply close the HomeActivity, which will navigate back to login
        }

        binding.btnManageBooks.setOnClickListener {
            val intent = Intent(this, ManageBooksActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showAddBookDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_book, null)
        val bookTitleEditText = dialogView.findViewById<EditText>(R.id.etBookTitle)
        val bookAuthorEditText = dialogView.findViewById<EditText>(R.id.etBookAuthor)
        val pageRangeEditText = dialogView.findViewById<EditText>(R.id.etPageRange)

        android.app.AlertDialog.Builder(this)
            .setTitle("Add New Book")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val title = bookTitleEditText.text.toString()
                val author = bookAuthorEditText.text.toString()
                val pageRange = pageRangeEditText.text.toString()

                if (title.isNotEmpty() && author.isNotEmpty()) {
                    val book = Book(title, author, pageRange)
                    bookViewModel.addBook(book)
                } else {
                    Toast.makeText(this, "Please enter both title and author", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
