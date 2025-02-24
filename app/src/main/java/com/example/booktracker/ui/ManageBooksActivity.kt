package com.example.booktracker.ui
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booktracker.adapter.ManageBooksAdapter
import com.example.booktracker.databinding.ActivityManageBooksBinding
import com.example.booktracker.model.Book
import com.example.booktracker.ui.BookReviewsActivity
import com.example.booktracker.viewmodel.BookViewModel
import kotlinx.coroutines.launch

class ManageBooksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManageBooksBinding
    private val bookViewModel = BookViewModel()
    private val bookAdapter = ManageBooksAdapter(::showEditDialog, ::deleteBook)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityManageBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewManageBooks.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewManageBooks.adapter = bookAdapter

        binding.btnBackToHome.setOnClickListener {
            finish() // Closes this activity and returns to HomeActivity
        }

        // Add a listener for the new button to go to BookReviewsActivity
        binding.btnViewReviews.setOnClickListener {
            val intent = Intent(this, BookReviewsActivity::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch {
            bookViewModel.books.collect { books ->
                bookAdapter.submitList(books)
            }
        }
    }

    // Function to edit book details
    private fun showEditDialog(book: Book) {
        val dialogView = LayoutInflater.from(this).inflate(com.example.booktracker.R.layout.dialog_edit_book, null)
        val etTitle = dialogView.findViewById<EditText>(com.example.booktracker.R.id.etEditBookTitle)
        val etAuthor = dialogView.findViewById<EditText>(com.example.booktracker.R.id.etEditBookAuthor)
        val etPageRange = dialogView.findViewById<EditText>(com.example.booktracker.R.id.etEditPageRange)

        etTitle.setText(book.title)
        etAuthor.setText(book.author)
        etPageRange.setText(book.pageRange)

        AlertDialog.Builder(this)
            .setTitle("Edit Book")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                val updatedBook = book.copy(
                    title = etTitle.text.toString(),
                    author = etAuthor.text.toString(),
                    pageRange = etPageRange.text.toString()
                )
                bookViewModel.updateBook(updatedBook)
                Toast.makeText(this, "Book updated!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Function to delete a book
    private fun deleteBook(book: Book) {
        bookViewModel.deleteBook(book)
        Toast.makeText(this, "Book deleted!", Toast.LENGTH_SHORT).show()
    }
}
