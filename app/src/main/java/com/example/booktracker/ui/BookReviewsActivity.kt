package com.example.booktracker.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booktracker.adapter.BookReviewsAdapter
import com.example.booktracker.databinding.ActivityBookReviewsBinding
import com.example.booktracker.viewmodel.BookViewModel
import kotlinx.coroutines.launch

class BookReviewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookReviewsBinding
    private val bookViewModel = BookViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        binding.recyclerViewBooks.layoutManager = LinearLayoutManager(this)

        // Initialize BookReviewsAdapter with books and save review callback
        val bookReviewsAdapter = BookReviewsAdapter(emptyList()) { book, review ->
            // Handle saving the review for the book
            bookViewModel.saveReview(book, review)  // You need to implement saveReview in ViewModel
        }
        binding.recyclerViewBooks.adapter = bookReviewsAdapter

        lifecycleScope.launch {
            // Collect books from ViewModel and pass to adapter
            bookViewModel.books.collect { books ->
                bookReviewsAdapter.submitList(books)  // Pass books to the adapter
            }
        }

        // Set up the Back to Home button click listener
        binding.btnBackToHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)  // Make sure HomeActivity is declared in your manifest
            startActivity(intent)
            finish()  // Optionally call finish to prevent users from going back to this activity
        }
    }
}
