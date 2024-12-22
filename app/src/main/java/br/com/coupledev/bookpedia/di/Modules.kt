package br.com.coupledev.bookpedia.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import br.com.coupledev.bookpedia.book.data.database.DatabaseFactory
import br.com.coupledev.bookpedia.book.data.database.FavoriteBookDatabase
import br.com.coupledev.bookpedia.book.data.network.KtorRemoteBookpediaDataSource
import br.com.coupledev.bookpedia.book.data.network.RemoteBookDataSource
import br.com.coupledev.bookpedia.book.data.repository.DefaultBookRepository
import br.com.coupledev.bookpedia.book.domain.BookRepository
import br.com.coupledev.bookpedia.book.presentation.book_detail.BookDetailViewModel
import br.com.coupledev.bookpedia.book.presentation.book_list.BookListViewModel
import br.com.coupledev.bookpedia.book.presentation.SelectedBookViewModel
import br.com.coupledev.bookpedia.core.data.HttpClientFactory
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClientFactory.create(
            OkHttp.create()
        )
    }

    single { DatabaseFactory(androidApplication()) }

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single { get<FavoriteBookDatabase>().favoriteBookDao }

    singleOf(::KtorRemoteBookpediaDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()

    viewModelOf(::BookListViewModel)
    viewModelOf(::SelectedBookViewModel)
    viewModelOf(::BookDetailViewModel)
}