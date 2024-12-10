package br.com.coupledev.bookpedia.book.domain

import br.com.coupledev.bookpedia.core.domain.DataError
import br.com.coupledev.bookpedia.core.domain.Result

interface BookRepository {
    suspend fun searchBook(query: String): Result<List<Book>, DataError.Remote>
}