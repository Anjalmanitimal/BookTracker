package com.example.booktracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.booktracker.R
import com.example.booktracker.model.Book

class BookReviewsAdapter(
    private var books: List<Book>,
    private val onSaveReview: (Book, String) -> Unit
) : RecyclerView.Adapter<BookReviewsAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book_review, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int = books.size

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bookTitle: TextView = itemView.findViewById(R.id.tvBookTitle)
        private val reviewEditText: EditText = itemView.findViewById(R.id.etReview)
        private val saveButton: Button = itemView.findViewById(R.id.btnSaveReview)

        fun bind(book: Book) {
            bookTitle.text = book.title
            reviewEditText.setText(book.review)

            saveButton.setOnClickListener {
                val review = reviewEditText.text.toString()
                onSaveReview(book, review)
            }
        }
    }

    fun submitList(newBooks: List<Book>) {
        books = newBooks
        notifyDataSetChanged()
    }
}
