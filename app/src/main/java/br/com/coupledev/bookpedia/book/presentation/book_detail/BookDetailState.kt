package br.com.coupledev.bookpedia.book.presentation.book_detail

import br.com.coupledev.bookpedia.book.domain.Book

data class BookDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val book: Book? = null
)
