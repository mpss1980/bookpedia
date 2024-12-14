package br.com.coupledev.bookpedia.book.data.repository

import br.com.coupledev.bookpedia.book.data.mappers.toBook
import br.com.coupledev.bookpedia.book.data.network.RemoteBookDataSource
import br.com.coupledev.bookpedia.book.domain.Book
import br.com.coupledev.bookpedia.book.domain.BookRepository
import br.com.coupledev.bookpedia.core.domain.DataError
import br.com.coupledev.bookpedia.core.domain.Result
import br.com.coupledev.bookpedia.core.domain.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource
): BookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBook(query)
            .map {dto ->
                dto.results.map { it.toBook() }
            }
    }
}