package br.com.coupledev.bookpedia.book.data.network

import br.com.coupledev.bookpedia.book.data.dto.SearchResponseDto
import br.com.coupledev.bookpedia.core.domain.DataError
import br.com.coupledev.bookpedia.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBook(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>
}