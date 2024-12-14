package br.com.coupledev.bookpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.coupledev.bookpedia.book.data.network.KtorRemoteBookpediaDataSource
import br.com.coupledev.bookpedia.book.data.repository.DefaultBookRepository
import br.com.coupledev.bookpedia.book.presentation.book_list.BookListScreenRoot
import br.com.coupledev.bookpedia.book.presentation.book_list.BookListViewModel
import br.com.coupledev.bookpedia.core.data.HttpClientFactory
import br.com.coupledev.bookpedia.ui.theme.BookpediaTheme
import io.ktor.client.engine.okhttp.OkHttp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val engine = remember { OkHttp.create() }

            BookpediaTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    BookListScreenRoot(
                        viewModel = remember {
                            BookListViewModel(
                                bookRepository = DefaultBookRepository(
                                    remoteBookDataSource = KtorRemoteBookpediaDataSource(
                                        httpClient = HttpClientFactory.create(
                                            engine = engine
                                        )
                                    )
                                )
                            )
                        },
                        onBookClick = {

                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}