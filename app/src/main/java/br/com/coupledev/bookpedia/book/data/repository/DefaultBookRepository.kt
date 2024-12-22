package br.com.coupledev.bookpedia.book.data.repository

import androidx.sqlite.SQLiteException
import br.com.coupledev.bookpedia.book.data.database.FavoriteBookDao
import br.com.coupledev.bookpedia.book.data.mappers.toBook
import br.com.coupledev.bookpedia.book.data.mappers.toBookEntity
import br.com.coupledev.bookpedia.book.data.network.RemoteBookDataSource
import br.com.coupledev.bookpedia.book.domain.Book
import br.com.coupledev.bookpedia.book.domain.BookRepository
import br.com.coupledev.bookpedia.core.domain.DataError
import br.com.coupledev.bookpedia.core.domain.EmptyResult
import br.com.coupledev.bookpedia.core.domain.Result
import br.com.coupledev.bookpedia.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao
) : BookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBook(query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }
    }

    override suspend fun getBookDescription(bookId: String): Result<String?, DataError> {
        val localResult = favoriteBookDao.getFavoriteBook(bookId)

        return if (localResult == null) {
            remoteBookDataSource
                .getBookDetails(bookId)
                .map { it.description }
        } else {
            Result.Success(localResult.description)
        }
    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { bookEntities ->
                bookEntities.map { it.toBook() }
            }
    }

    override fun isBookFavorite(id: String): Flow<Boolean> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { bookEntities ->
                bookEntities.any { it.id == id }
            }
    }

    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> {
        return try {
            favoriteBookDao.upsert(book.toBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavorites(id: String) {
        favoriteBookDao.deleteFavoriteBook(id)
    }
}