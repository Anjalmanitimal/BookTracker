package com.example.booktracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.booktracker.R
import com.example.booktracker.model.Book

class BookAdapter : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private var books = listOf<Book>()

    fun submitList(newBooks: List<Book>) {
        books = newBooks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int = books.size

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bookTitle: TextView = itemView.findViewById(R.id.tvBookTitle)
        private val bookPageRange: TextView = itemView.findViewById(R.id.tvPageRange)
        private val bookAuthor: TextView = itemView.findViewById(R.id.tvBookAuthor)

        fun bind(book: Book) {
            bookTitle.text = book.title  // Book Name
            bookPageRange.text = "Pages: ${book.pageRange}"  // Page Range below title
            bookAuthor.text = "Author: ${book.author}"  // Author Name
        }
    }
}

