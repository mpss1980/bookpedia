package br.com.coupledev.bookpedia.book.data.network

import br.com.coupledev.bookpedia.book.data.dto.SearchResponseDto
import br.com.coupledev.bookpedia.core.data.safeCall
import br.com.coupledev.bookpedia.core.domain.DataError
import br.com.coupledev.bookpedia.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://openlibrary.org"

class KtorRemoteBookpediaDataSource(
    private val httpClient: HttpClient
) : RemoteBookDataSource {

    override suspend fun searchBook(
        query: String,
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/search.json"
            ) {
                parameter("q", query)
                parameter("title", resultLimit)
                parameter("language", "eng")
                parameter(
                    "fields",
                    "key,title,language,cover_i,author_key,author_name,cover_edition_key,first_publish_year,ratings_average,ratings_count,number_of_pages_median,edition_count"
                )
            }
        }
    }
}