package com.example.booktracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.booktracker.R
import com.example.booktracker.model.Book

class ManageBookAdapter(
    private val onEditClick: (Book) -> Unit,
    private val onDeleteClick: (Book) -> Unit
) : RecyclerView.Adapter<ManageBookAdapter.BookViewHolder>() {

    private var books = listOf<Book>()

    fun submitList(newBooks: List<Book>) {
        books = newBooks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_manage_book, parent, false)
        return BookViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int = books.size

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bookTitle: TextView = itemView.findViewById(R.id.tvBookTitle)
        private val bookPageRange: TextView = itemView.findViewById(R.id.tvPageRange)
        private val bookAuthor: TextView = itemView.findViewById(R.id.tvBookAuthor)
        private val btnEdit: Button = itemView.findViewById(R.id.btnEdit)
        private val btnDelete: Button = itemView.findViewById(R.id.btnDelete)

        fun bind(book: Book) {
            bookTitle.text = book.title
            bookPageRange.text = "Pages: ${book.pageRange}"
            bookAuthor.text = "Author: ${book.author}"

            btnEdit.setOnClickListener { onEditClick(book) }
            btnDelete.setOnClickListener { onDeleteClick(book) }
        }

        fun submitList(newBooks: List<Book>) {
            books = newBooks
            notifyDataSetChanged() // Ensure full refresh
        }
    }
}
